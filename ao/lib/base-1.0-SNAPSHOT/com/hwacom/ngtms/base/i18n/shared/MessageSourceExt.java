/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.base.i18n.shared;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.context.HierarchicalMessageSource;

public interface MessageSourceExt extends HierarchicalMessageSource {
  public String getMessage(String code);

  public String getMessage(String code, Locale locale);

  public String getMessage(String code, Object... args);

  public String getMessage(String code, Locale locale, Object... args);

  public List<String> getKeysOfSamePrefix(String prefix);

  public List<String> getKeysOfSamePrefix(String prefix, Locale locale);

  public List<String> getValuesOfSamePrefix(String prefix);

  public List<String> getValuesOfSamePrefix(String prefix, Locale locale);

  public <E extends Enum<E> & MessageType> String getEnumAbbrMessage(E messageType);

  public <E extends Enum<E> & MessageType> String getEnumFullMessage(E messageType);

  public <E extends Enum<E> & MessageType> String getEnumMessage(E messageType, String middle);

  public <E extends Enum<E> & MessageType> Map<E, String> getEnumAbbrMessages(Class<E> messageType);

  public <E extends Enum<E> & MessageType> Map<E, String> getEnumFullMessages(Class<E> messageType);

  public <E extends Enum<E> & MessageType> Map<E, String> getEnumMessages(
      Class<E> messageType, String middle);
}
