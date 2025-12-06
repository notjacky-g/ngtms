/*
 * © HwaCom Systems Inc. 2018
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.hwacom.ngtms.base.shared.Pair;
import com.hwacom.ngtms.hcce.am.restygwt.FmeAddressRestService;
import com.hwacom.ngtms.hcce.am.restygwt.HcceRestService;
import com.hwacom.ngtms.hcce.am.view.Messages;
import com.hwacom.ngtms.hcce.am.view.SkipClusterModeVerification;
import com.hwacom.ngtms.hcce.shared.dto.ClusterModeDTO;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.RestServiceProxy;

public class GwtEntryPoint implements EntryPoint {

  static { // 因各模組可能直接使用 amService, 需直接初始 URL 設定, 否則會變為 restygwt 預設路徑
    Defaults.setServiceRoot(GWT.getHostPageBaseURL());
    Defaults.setDateFormat(null);
  }

  private static final Messages messages = GWT.create(Messages.class);

  public static final String HCCE_DR_SWITCH_EP_NAME = "系統監視軟體";

  public static final String LOCAL_IP = "127.0.0.1";

  public static final int LOCAL_PORT = 80;

  public static final FmeAddressRestService fmeService = GWT.create(FmeAddressRestService.class);

  public static final HcceRestService hcceService = GWT.create(HcceRestService.class);

  public static final HcceRestService backupSystemHcceService = GWT.create(HcceRestService.class);

  public static String webSocketIp;

  public static int webSocketPort;

  protected Composite mainWidget;

  protected boolean nginxActivated;

  private AtomicInteger fmeAddressRequests = new AtomicInteger();

  private Viewport viewport;

  private String epName = null;

  private Consumer<List<SimpleEntry<String, RestService>>> dedicatedServicesConsumer;

  private Consumer<List<RestService>> servicesConsumer;

  private List<SimpleEntry<String, RestService>> dedicatedServices;

  private List<RestService> services;

  private boolean startTimeoutChecker;

  private boolean alive = true;

  public GwtEntryPoint(String windowTitle, RestService... services) {
    this(windowTitle, null, services);
  }

  public GwtEntryPoint(
      String windowTitle, List<SimpleEntry<String, RestService>> dedicatedServices) {
    this(windowTitle, dedicatedServices, new RestService[0]);
  }

  public GwtEntryPoint(
      String windowTitle,
      List<SimpleEntry<String, RestService>> dedicatedServices,
      RestService... services) {
    epName = windowTitle;
    initHostPage(windowTitle);
    initService(dedicatedServices, services);
    this.dedicatedServices = dedicatedServices;
    this.services = new ArrayList<>();
    for (RestService service : services) {
      this.services.add(service);
    }
  }

  protected void initHostPage(String windowTitle) {
    setFavicon();
    Window.setTitle(windowTitle);
    viewport = new Viewport();
    RootPanel.get().add(viewport);
  }

  protected void initService(
      List<SimpleEntry<String, RestService>> dedicatedServices, RestService... services) {
    Consumer<Boolean> callback =
        nginxActivated -> {
          initConsumers(nginxActivated);
          getFmeAddress(dedicatedServices, services);
        };
    fmeService.checkNginxActivated(
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean nginxActivated) {
            GwtEntryPoint.this.nginxActivated = nginxActivated;
            callback.accept(nginxActivated);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("Check nginx activated failed!", exception);
            callback.accept(false);
          }
        });
  }

  private void initConsumers(boolean nginxActivated) {
    if (nginxActivated) {
      dedicatedServicesConsumer =
          dedicatedServices ->
              skipReplaceIpAndPort(
                  dedicatedServices
                      .stream()
                      .map(entry -> entry.getValue())
                      .collect(Collectors.toList()));
      servicesConsumer = serviceList -> skipReplaceIpAndPort(serviceList);
    } else {
      dedicatedServicesConsumer = dedicatedServices -> getFmeAddress(dedicatedServices);
      servicesConsumer = serviceList -> getFmeAddress(serviceList.toArray(new RestService[0]));
    }
  }

  private void skipReplaceIpAndPort(List<RestService> services) {
    String ip = Location.getHostName();
    int port;
    try {
      port = Integer.parseInt(Location.getPort());
    } catch (NumberFormatException e) {
      port = "http:".equals(Location.getProtocol()) ? 80 : 443;
    }
    for (RestService service : services) {
      serviceReady(service, ip, port);
    }
  }

  private void getFmeAddress(
      List<SimpleEntry<String, RestService>> dedicatedServices, RestService... services) {
    List<RestService> serviceList = new ArrayList<>(Arrays.asList(services));
    serviceList.addAll(getAdditionalRestServices());
    fmeAddressRequests.addAndGet(serviceList.size());
    if (dedicatedServices != null) {
      fmeAddressRequests.addAndGet(dedicatedServices.size());
      dedicatedServicesConsumer.accept(dedicatedServices);
    }
    servicesConsumer.accept(serviceList);
    if (serviceList.contains(hcceService)) {
      getPrimarySystemFmeAddress();
    }
    if (serviceList.contains(backupSystemHcceService)) {
      getBackupSystemFmeAddress();
    }
  }

  protected List<RestService> getAdditionalRestServices() {
    List<RestService> list = new ArrayList<>();
    list.add(hcceService);
    return list;
  }

  private void getFmeAddress(final List<SimpleEntry<String, RestService>> services) {
    for (SimpleEntry<String, RestService> each : services) {
      String fmeName = each.getKey();
      RestService service = each.getValue();
      fmeService.getFmeAddress(
          fmeName,
          new MethodCallback<Pair<String, Integer>>() {
            @Override
            public void onSuccess(Method method, Pair<String, Integer> response) {
              bindServiceIpAndPort(service, response.getFirst(), response.getSecond());
            }

            @Override
            public void onFailure(Method method, Throwable exception) {
              GWT.log("Get fme address failed, fme=" + fmeName, exception);
            }
          });
    }
  }

  private void bindServiceIpAndPort(RestService service, String ip, int port) {
    replaceServiceResource(service, ip, port);
    serviceReady(service, ip, port);
  }

  protected void replaceServiceResource(RestService service, String ip, Integer port) {
    RestServiceProxy proxy = (RestServiceProxy) service;
    if (Location.getPort().isEmpty()) {
      proxy.setResource(
          new Resource(
              proxy.getResource().getUri().replace(Location.getHostName(), ip + ":" + port)));
    } else {
      proxy.setResource(
          new Resource(
              proxy
                  .getResource()
                  .getUri()
                  .replace(Location.getHostName(), ip)
                  .replace(Location.getPort(), Integer.toString(port))));
    }
  }

  protected void serviceReady(RestService service, String fmeAddress, int port) {
    GWT.log("fmeAddressRequests: " + fmeAddressRequests);
    if (fmeAddressRequests.decrementAndGet() == 0) {
      allServicesReady();
    }
  }

  protected void allServicesReady() {}

  private void getFmeAddress(RestService... services) {
    // hcce, bkHcce ip&port另外取得, 這邊只bind其他模組
    // retrieveSpecificNodeIpAndPort會取得該系統的ip&port, 主取主、備取備
    fmeService.retrieveSpecificIpAndPort(
        new MethodCallback<Pair<String, Integer>>() {
          @Override
          public void onSuccess(Method method, Pair<String, Integer> response) {
            for (RestService service : services) {
              if (service == backupSystemHcceService || service == hcceService) {
                continue;
              }
              bindServiceIpAndPort(service, response.getFirst(), response.getSecond());
              webSocketIp = response.getFirst();
              webSocketPort = response.getSecond();
            }
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("Get fme address failed!", exception);
          }
        });
  }

  private void getPrimarySystemFmeAddress() {
    fmeService.retrieveArbitraryNodeIpAndPort(
        new MethodCallback<Pair<String, Integer>>() {
          @Override
          public void onSuccess(Method method, Pair<String, Integer> response) {
            bindServiceIpAndPort(hcceService, response.getFirst(), response.getSecond());
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("Get primary system fme address failed!", exception);
            skipReplaceIpAndPort(Arrays.asList(hcceService));
          }
        });
  }

  private void getBackupSystemFmeAddress() {
    fmeService.retrieveArbitraryBackupNodeIpAndPort(
        new MethodCallback<Pair<String, Integer>>() {
          @Override
          public void onSuccess(Method method, Pair<String, Integer> response) {
            bindServiceIpAndPort(
                backupSystemHcceService, response.getFirst(), response.getSecond());
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("Get backup system fme address failed!", exception);
            skipReplaceIpAndPort(Arrays.asList(backupSystemHcceService));
          }
        });
  }

  protected void init(Composite mainWidget) {
    this.init(mainWidget, true);
  }

  protected void init(Composite mainWidget, boolean startTimeoutChecker) {
    this.startTimeoutChecker = startTimeoutChecker;
    if (mainWidget instanceof SkipClusterModeVerification) {
      initWidget(mainWidget);
    } else {
      verifyClusterMode(mainWidget);
    }
  }

  private void verifyClusterMode(Composite mainWidget) {
    fmeService.getClusterMode(
        new MethodCallback<ClusterModeDTO>() {
          @Override
          public void onSuccess(Method method, ClusterModeDTO clusterMode) {
            // 僅有hcce會在clusterMode為standby的時候放行
            if (clusterMode == ClusterModeDTO.Active
                || (epName != null
                    && epName.equals(HCCE_DR_SWITCH_EP_NAME)
                    && clusterMode == ClusterModeDTO.Standby)) {
              initWidget(mainWidget);
            } else {
              inactivePage();
            }
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            Info.display("", exception.getMessage());
            inactivePage();
          }
        });
  }

  private void initWidget(Composite mainWidget) {
    viewport.add(mainWidget);
    viewport.forceLayout();
    this.mainWidget = mainWidget;
    startSystemEnvTimer();
    if (startTimeoutChecker) {
      startTimeoutChecker();
    }
  }

  private void inactivePage() {
    viewport.add(new Label(messages.cluster_mode_is_not_active()));
  }

  private void startSystemEnvTimer() {
    (new Timer() {
          @Override
          public void run() {
            fmeService.getClusterMode(
                new MethodCallback<ClusterModeDTO>() {
                  @Override
                  public void onSuccess(Method method, ClusterModeDTO result) {
                    checkSystemEnv(result);
                  }

                  @Override
                  public void onFailure(Method method, Throwable caught) {
                    GWT.log("TitleView getSystemEnv failed.", caught);
                    checkSystemEnv(ClusterModeDTO.DisConnected);
                  }
                });
          }
        })
        .scheduleRepeating(60000);
  }

  private void checkSystemEnv(ClusterModeDTO clusterMode) {
    switch (clusterMode) {
      case Active:
        mainWidget.unmask();
        break;
      case DisConnected:
        mainWidget.mask("系統已斷線!請重新整理瀏覽器!");
        unbindServices();
        break;
      case Standby:
        mainWidget.unmask();
        if (!epName.equals(HCCE_DR_SWITCH_EP_NAME)) {
          unbindServices();
        }
        break;
      default:
        mainWidget.mask("系統已斷線!請重新整理瀏覽器!");
    }
  }

  private void unbindServices() {
    for (SimpleEntry<String, RestService> each : dedicatedServices) {
      RestService service = each.getValue();
      GWT.log("unbind da service:" + service.getClass().getName());
      unbindServiceResource(service);
    }
    for (RestService service : services) {
      GWT.log("unbind service:" + service.getClass().getName());
      unbindServiceResource(service);
    }
    webSocketIp = LOCAL_IP;
    webSocketPort = LOCAL_PORT;
  }

  protected void unbindServiceResource(RestService service) {
    RestServiceProxy proxy = (RestServiceProxy) service;
    if (Location.getPort().isEmpty()) {
      proxy.setResource(
          new Resource(
              proxy.getResource().getUri().replace(webSocketIp, LOCAL_IP + ":" + LOCAL_PORT)));
    } else {
      proxy.setResource(
          new Resource(
              proxy
                  .getResource()
                  .getUri()
                  .replace(webSocketIp, LOCAL_IP)
                  .replace(Integer.toString(webSocketPort), Integer.toString(LOCAL_PORT))));
    }
  }

  private void startTimeoutChecker() {
    Event.addNativePreviewHandler(
        new NativePreviewHandler() {
          @Override
          public void onPreviewNativeEvent(NativePreviewEvent event) {
            switch (event.getTypeInt()) {
              case Event.ONCLICK:
              case Event.ONKEYUP:
              case Event.ONMOUSEWHEEL:
                if (!alive) {
                  keepSessionAlive();
                }
                alive = true;
                break;
              default:
                break;
            }
          }
        });
    startKeepAliveTimer();
    startSessionTimeoutTimer();
  }

  private void startKeepAliveTimer() {
    new Timer() {
      @Override
      public void run() {
        schedule(30000);
        if (alive || isPageAlive()) {
          alive = false;
          keepSessionAlive();
        }
      }
    }.run();
  }

  protected boolean isPageAlive() {
    return false;
  }

  private void keepSessionAlive() {
    fmeService.keepHttpSessionAlive(
        new MethodCallback<Void>() {
          @Override
          public void onSuccess(Method method, Void response) {}

          @Override
          public void onFailure(Method method, Throwable exception) {}
        });
  }

  private void startSessionTimeoutTimer() {
    new Timer() {
      @Override
      public void run() {
        schedule(60000);
        if (!alive) {
          checkSessionTimeout();
        }
      }
    }.schedule(getDelaySeconds() * 1000);
  }

  private void checkSessionTimeout() {
    fmeService.isHttpSessionTimeout(
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean timeout) {
            if (timeout) {
              Window.Location.assign("/logout");
            }
          }

          @Override
          public void onFailure(Method method, Throwable exception) {}
        });
  }

  private native int getDelaySeconds() /*-{
    var seconds = new Date().getSeconds();
    var result = 0;
    if (seconds !== 0) {
      result = 60 - seconds;
    }
    console.log('Delay seconds ' + result);
    return result;
  }-*/;

  @Override
  public void onModuleLoad() {}

  /** 用正規的 DOM 方式重新指定一次 favicon。 */
  private native void setFavicon() /*-{
	    var link = $doc.createElement('link');
	    link.type = 'image/x-icon';
	    link.rel = 'shortcut icon';
	    link.href = 'favicon.ico';
	    $doc.getElementsByTagName('head')[0].appendChild(link);
	}-*/;

  public Viewport getViewport() {
    return viewport;
  }
}
