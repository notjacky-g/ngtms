package com.hwacom.ngtms.room.am.presenter;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.room.am.RoomEP;
import com.hwacom.ngtms.room.am.view.RoomCardGroupViewer;
import com.hwacom.ngtms.room.shared.dto.RoomCardGroupConfigDTO;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class RoomCardGroupPresenter {

  private RoomCardGroupViewer viewer;

  public RoomCardGroupPresenter(RoomCardGroupViewer viewer) {
    this.viewer = viewer;
    viewer.setPresenter(this);
    getRoomCardGroupConfig();
  }

  public void getRoomCardGroupConfig() {
    RoomEP.commonService.getRoomCardGroupConfig(
        new MethodCallback<List<RoomCardGroupConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<RoomCardGroupConfigDTO> result) {
            viewer.fillData(result);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomCardGroupPresenter.getRoomCardGroupConfig failed.", exception);
          }
        });
  }

  public void createRoomCardGroupConfig(RoomCardGroupConfigDTO dto) {
    RoomEP.commonService.createRoomCardGroupConfig(
        dto,
        new MethodCallback<Boolean>() {

          @Override
          public void onSuccess(Method method, Boolean result) {
            if (result) {
              Info.display("新增定期卡群組", "新增成功");
              getRoomCardGroupConfig();
            } else {
              Info.display("新增定期卡群組", "新增失敗");
            }
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomCardGroupPresenter.createRoomCardGroupConfig failed.", exception);
            Info.display("新增定期卡群組", "新增失敗");
          }
        });
  }

  public void updateRoomCardGroupConfig(RoomCardGroupConfigDTO dto) {
    RoomEP.commonService.updateRoomCardGroupConfig(
        dto,
        new MethodCallback<Boolean>() {

          @Override
          public void onSuccess(Method method, Boolean result) {
            if (result) {
              Info.display("修改定期卡群組", "修改成功");
              getRoomCardGroupConfig();
            } else {
              Info.display("修改定期卡群組", "修改失敗");
            }
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomCardGroupPresenter.updateRoomCardGroupConfig failed.", exception);
            Info.display("修改定期卡群組", "修改失敗");
          }
        });
  }

  public void deleteRoomCardGroupConfig(Long id) {
    RoomEP.commonService.deleteRoomCardGroupConfig(
        id,
        new MethodCallback<Boolean>() {

          @Override
          public void onSuccess(Method method, Boolean result) {
            if (result) {
              Info.display("刪除定期卡群組", "刪除成功");
              getRoomCardGroupConfig();
            } else {
              Info.display("刪除定期卡群組", "刪除失敗");
            }
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomCardGroupPresenter.deleteRoomCardGroupConfig failed.", exception);
            Info.display("刪除定期卡群組", "刪除失敗");
          }
        });
  }
}
