@GwtWebSockets({
	@GwtWebSocket(name = "CommonCt3ResetDeviceWebSocket",
	  			  entryPoint = "com.hwacom.ngtms.common.am.AmEntryPoint",
	  			  uri = "websocket/ct3/common/ct3ResetDevice"),
	
	@GwtWebSocket(name = "CommonCt3TcConfigSettingWebSocket",
		  		  entryPoint = "com.hwacom.ngtms.common.am.AmEntryPoint",
		  		  uri = "websocket/ct3/common/tcConfigSetting"),

	@GwtWebSocket(name = "CommonCt3TcConfigQueryWebSocket",
				  entryPoint = "com.hwacom.ngtms.common.am.AmEntryPoint",
				  uri = "websocket/ct3/common/tcConfigQuery"),
	@GwtWebSocket(name = "OperationLogWebSocket",
	  entryPoint = "com.hwacom.ngtms.common.am.AmEntryPoint",
	  uri = "websocket/opLog")})

package com.hwacom.ngtms.cam.websocket;

import com.hwacom.ngtms.toolbox.gwt.annotation.GwtWebSocket;
import com.hwacom.ngtms.toolbox.gwt.annotation.GwtWebSockets;