/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.collect.ImmutableMap.Builder;
import com.hwacom.ngtms.base.i18n.shared.MessageType;
import java.util.Map;

/**
 * BOS (警告標誌) ID
 *
 * @author JtsayLin
 */
public enum RmsBosId implements MessageType {
  BOS1_1("BOS1", 0x8),
  BOS1_2("BOS1", 0x9),
  BOS1_3("BOS1", 0xA),
  BOS1_4("BOS1", 0xB),
  BOS2_1("BOS2", 0xC),
  BOS2_2("BOS2", 0xD),
  BOS2_3("BOS2", 0xE),
  BOS2_4("BOS2", 0xF),
  OTHER_56("ERROR", 56),
  ;

  public static final int BOS1_MIN_ID = 8;
  public static final int BOS2_MIN_ID = 12;
  private static final Map<Integer, RmsBosId> ID_MAPPING;

  static {
    Builder<Integer, com.hwacom.ngtms.c.shared.RmsBosId> builder = new Builder<>();
    for (com.hwacom.ngtms.c.shared.RmsBosId bosId : RmsBosId.values())
      builder.put(bosId.getId(), bosId);

    ID_MAPPING = builder.build();
  }

  private String bosGroup;
  private Integer id;

  private RmsBosId(String bosGroup, Integer id) {
    this.bosGroup = bosGroup;
    this.id = id;
  }

  public String getBosGroup() {
    return bosGroup;
  }

  public Integer getId() {
    return id;
  }

  public static com.hwacom.ngtms.c.shared.RmsBosId valueOf(Integer id) {
    com.hwacom.ngtms.c.shared.RmsBosId bosId = ID_MAPPING.get(id);
    checkArgument(bosId != null, "Undefined BOS ID [%s]", id);

    return bosId;
  }

  @Override
  public String getMessageKeyPrefix() {
    return "rmsFm.BosId";
  }
}
