package com.hwacom.ngtms.hcce.fme.manager;

import com.hwacom.ngtms.hcce.core.exception.InvalidFmeDefinitionException;
import com.hwacom.ngtms.hcce.fme.manager.model.FmeDefinition;
import com.hwacom.ngtms.hcce.shared.FmeDefTable;
import java.util.List;

public abstract interface FmeDataService
{
  public abstract void verifyFmeDefinitions(List<FmeDefinition> paramList)
    throws InvalidFmeDefinitionException;
  
  public abstract void updateFmeDefList(String paramString, List<FmeDefinition> paramList);
  
  public abstract FmeDefTable loadFmeDefTable(String paramString);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\manager\FmeDataService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */