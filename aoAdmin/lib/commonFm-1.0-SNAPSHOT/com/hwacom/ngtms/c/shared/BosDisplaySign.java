/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;

/**
 * 警告標誌 (BOS) 顯示的符號
 *
 * @author JtsayLin
 */
public enum BosDisplaySign {
  /** 0.不顯示符號 */
  NONE(null, ""),

  /** 1.箭頭符號 (不閃爍) */
  ARROW('↑', "ii"),

  /** 2.箭頭符號 (閃爍) */
  FLASH_ARROW('↑', "II"),

  /** 3.圓形符號 (不閃爍) */
  ROUND('●', "oo"),

  /** 4.圓形符號 (閃爍) */
  FLASH_ROUND('●', "OO"),

  /** 5.叉叉符號 (不閃爍) */
  CROSS('×', "xx"),

  /** 6.叉叉符號 (閃爍) */
  FLASH_CROSS('×', "XX");

  private static final Map<String, BosDisplaySign> WRAPPER_WORD_MAPPING;

  static {
    Builder<String, BosDisplaySign> builder = new Builder<>();
    for (BosDisplaySign sign : BosDisplaySign.values())
      builder.put(sign.getCommandWrapperWord(), sign);

    WRAPPER_WORD_MAPPING = builder.build();
  }

  public static BosDisplaySign valueOfCommandWrapperWord(String commandWrapperWord) {
    BosDisplaySign sign = WRAPPER_WORD_MAPPING.get(commandWrapperWord);
    checkArgument(sign != null, "Undefined BOS display sign [%s]", commandWrapperWord);

    return sign;
  }

  private Character sign;
  private String commandWrapperWord;

  private BosDisplaySign(Character sign, String commandWrapperWord) {
    this.sign = sign;
    this.commandWrapperWord = commandWrapperWord;
  }

  public Optional<Character> getSign() {
    return Optional.fromNullable(sign);
  }

  public String getCommandWrapperWord() {
    return commandWrapperWord;
  }
}
