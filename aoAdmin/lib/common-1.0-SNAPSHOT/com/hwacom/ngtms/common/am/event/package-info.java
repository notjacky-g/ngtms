@GwtEvents({ @GwtEvent(name = "MaskEvent", actions = { "MASK" }),
    @GwtEvent(name = "UnmaskEvent", actions = { "UNMASK" }),
    @GwtEvent(name = "AccountDataEvent", actions = { "ROLE_INIT_DATA" }),
    @GwtEvent(name = "RoleEvent", actions = { "SAVE", "ADD", "DELETE" }),
    @GwtEvent(name = "RoleFunctionPermissionEvent", actions = { "SAVE" }),
    @GwtEvent(name = "UserEvent", actions = { "SAVE", "ADD", "DELETE" }),
    @GwtEvent(name = "UserViewerEvent", actions = { "GRID_READY" }),
    @GwtEvent(name = "ReportViewerEvent", actions = { "SELECT_REPORT", "EXPORT_CSV", "EXPORT_PDF", "EXPORT_XLS", "EXPORT_PNG", "EXPORT_JPEG"}),
    @GwtEvent(name = "UnitViewerEvent", actions = { "UNIT_ADDED", "UNIT_REMOVED", "UNIT_UPDATED" }),
    @GwtEvent(name = "PwdRecoveryEvent", actions = { "COMPLETE" }),
    @GwtEvent(name = "MfaCodeRecoveryEvent", actions = { "COMPLETE" })
    })
package com.hwacom.ngtms.common.am.event;

import com.hwacom.ngtms.toolbox.gwt.annotation.GwtEvent;
import com.hwacom.ngtms.toolbox.gwt.annotation.GwtEvents;
