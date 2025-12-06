package com.hwacom.ngtms.room.am.presenter;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.room.am.RoomEP;
import com.hwacom.ngtms.room.am.view.RoomCardIssueViewer;
import com.hwacom.ngtms.room.shared.dto.RoomCardConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCardGroupConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCardIssueParam;
import com.hwacom.ngtms.room.shared.dto.RoomCardPermissionTreeDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCardReaderMappingConfigDTO;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class RoomCardIssuePresenter {

  private RoomCardIssueViewer viewer;

  public RoomCardIssuePresenter(RoomCardIssueViewer viewer) {
    this.viewer = viewer;
    viewer.setPresenter(this);
    getRoomCardConfig();
    getCardGroupConfig();
  }

  public void getRoomCardConfig() {
    GWT.log("getRooCardConfig.");
    RoomEP.commonService.getRoomCardConfig(
        new MethodCallback<List<RoomCardConfigDTO>>() {

          @Override
          public void onSuccess(Method method, List<RoomCardConfigDTO> result) {
            viewer.fillRoomCardConfig(result);
          }

          @Override
          public void onFailure(Method method, Throwable e) {
            GWT.log("RoomCardIssuePresenter getRooCardConfig failed.", e);
            Info.display("查詢卡片資料", "查詢失敗");
          }
        });
  }

  public void createRoomCardConfig(RoomCardConfigDTO dto) {
    RoomEP.commonService.createRoomCardConfig(
        dto,
        new MethodCallback<Boolean>() {

          @Override
          public void onSuccess(Method method, Boolean result) {
            if (result) {
              Info.display("新增卡片", "新增成功");
              getRoomCardConfig();
              viewer.hideCreateDialog();
            } else {
              Info.display("新增卡片", "新增失敗");
            }
          }

          @Override
          public void onFailure(Method method, Throwable e) {
            GWT.log("RoomCardIssuePresenter.createRoomCardConfig failed.", e);
            Info.display("新增卡片", "新增失敗");
          }
        });
  }

  public void updateRoomCardConfig(RoomCardIssueParam param) {
    RoomEP.commonService.updateRoomCardConfig(
        param,
        new MethodCallback<String>() {

          @Override
          public void onSuccess(Method method, String result) {
            Info.display("卡片修改", "修改完成");
            getRoomCardConfig();
            if (result != null && !result.isEmpty()) {
              viewer.showAddCardFailDialog(result);
            }
            getRoomCardReaderMappingConfig(param.getRoomCardConfigDto().getAba());
          }

          @Override
          public void onFailure(Method method, Throwable e) {
            GWT.log("RoomCardIssuePresenter.updateRoomCardConfig failed.", e);
            Info.display("卡片修改", "修改失敗");
          }
        });
  }

  public void issueRoomCard(RoomCardIssueParam param) {
    RoomEP.commonService.issueRoomCard(
        param,
        new MethodCallback<Boolean>() {

          @Override
          public void onSuccess(Method method, Boolean result) {
            if (result) {
              Info.display("卡片發卡", "發卡成功");
              getRoomCardConfig();
            } else {
              Info.display("卡片發卡", "發卡失敗");
            }
          }

          @Override
          public void onFailure(Method method, Throwable e) {
            GWT.log("RoomCardIssuePresenter.issueRoomCard failed.", e);
            Info.display("卡片發卡", "發卡失敗");
          }
        });
  }

  public void issueRoomCardImmediately(RoomCardIssueParam param) {
    RoomEP.commonService.issueRoomCardImmediately(
        param,
        new MethodCallback<String>() {

          @Override
          public void onSuccess(Method method, String result) {
            getRoomCardConfig();
            GWT.log("issueRoomCardImmediately result: " + result);
            if (result != null && !result.isEmpty()) {
              Info.display("卡片發卡", "部分卡機發卡失敗");
              viewer.showAddCardFailDialog(result);
            } else {
              Info.display("卡片發卡", "發卡成功");
            }
            viewer.hideForceIssuingDialog();
            getRoomCardReaderMappingConfig(param.getRoomCardConfigDto().getAba());
          }

          @Override
          public void onFailure(Method method, Throwable e) {
            GWT.log("RoomCardIssuePresenter.issueRoomCardImmediately failed.", e);
            Info.display("卡片發卡", "發卡失敗");
          }
        });
  }

  public void getCardReaderLocation() {
    RoomEP.commonService.getCardReaderLocation(
        new MethodCallback<List<RoomCardPermissionTreeDTO>>() {

          @Override
          public void onSuccess(Method method, List<RoomCardPermissionTreeDTO> response) {
            if (response.size() > 0) {
              viewer.fillTreeData(response);
            }
          }

          @Override
          public void onFailure(Method method, Throwable e) {
            GWT.log("RoomCardIssuePresenter.getCardReaderLocation failed.", e);
          }
        });
  }

  public void getCardGroupConfig() {
    RoomEP.commonService.getRoomCardGroupConfig(
        new MethodCallback<List<RoomCardGroupConfigDTO>>() {

          @Override
          public void onSuccess(Method method, List<RoomCardGroupConfigDTO> result) {
            viewer.fillRoomCardGroup(result);
          }

          @Override
          public void onFailure(Method method, Throwable e) {
            GWT.log("RoomCardIssuePresenter.getCardGroupConfig failed.", e);
          }
        });
  }

  public void getRoomCardReaderMappingConfig(String aba) {
    GWT.log("presenter getRoomCardReaderMappingConfig.");
    RoomEP.commonService.getRoomCardReaderMappingConfig(
        aba,
        new MethodCallback<List<RoomCardReaderMappingConfigDTO>>() {

          @Override
          public void onSuccess(Method method, List<RoomCardReaderMappingConfigDTO> result) {
            viewer.fillTreeCheckValue(result);
          }

          @Override
          public void onFailure(Method method, Throwable e) {
            GWT.log("RoomCardIssuePresenter.getRoomCardReaderMappingConfig failed.", e);
          }
        });
  }
}
