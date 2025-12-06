package com.hwacom.ngtms.hcce.fme.manager;

import com.hwacom.ngtms.hcce.core.exception.InvalidFmeDefinitionException;
import com.hwacom.ngtms.hcce.fme.manager.model.FmeDefinition;
import com.hwacom.ngtms.hcce.shared.FmeDefTable;
import java.util.List;

public interface FmeDataService {
  void verifyFmeDefinitions(List<FmeDefinition> paramList) throws InvalidFmeDefinitionException;
  
  void updateFmeDefList(String paramString, List<FmeDefinition> paramList);
  
  FmeDefTable loadFmeDefTable(String paramString);
}


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\manager\FmeDataService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */