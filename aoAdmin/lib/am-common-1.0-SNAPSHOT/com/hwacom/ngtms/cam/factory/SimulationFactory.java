/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.factory;

import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import java.util.List;

/**
 * 子系統各自提供實做 class，並在 <code>gwt.xml</code> 用
 *
 * <pre>{@code
 * <replace-with class="PKG_NAME.實做class">
 * 	<when-type-is class="com.hwacom.ngtms.common.am.factory.SimulationFactory"/>
 * </replace-with>
 * }</pre>
 *
 * 來替換程各自的實做內容（deferred binding）
 *
 * @param <T>
 * @author monty.pan
 */
public interface SimulationFactory<T> {
  ModelKeyProvider<T> getModelKeyProvider();

  List<ColumnConfig<T, ?>> getColumnConfigList();
}
