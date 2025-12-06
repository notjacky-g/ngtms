/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui.operation;

import com.google.gwt.event.shared.GwtEvent;

/**
 * ConfigConfig 的專屬 base event class。
 *
 * <p>只是為了要統一 / 限制介面開放度，所以裡頭毫無反應，就只是個空殼。
 *
 * @author monty.pan
 * @param <T> 對應的 handler class
 */
public abstract class OpSubmitEvent<T extends OpSubmitHandler> extends GwtEvent<T> {}
