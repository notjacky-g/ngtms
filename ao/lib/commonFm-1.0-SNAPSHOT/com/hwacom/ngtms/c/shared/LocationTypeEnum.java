package com.hwacom.ngtms.c.shared;

import java.util.List;

public interface LocationTypeEnum {

  String toLabel(Enum<?> e);

  List<Enum<?>> values();

  Enum<?> valueOf(String s);
}
