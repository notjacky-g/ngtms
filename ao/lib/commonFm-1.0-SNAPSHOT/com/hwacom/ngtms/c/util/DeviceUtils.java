/*    */ package com.hwacom.ngtms.c.util;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hazelcast.query.EntryObject;
/*    */ import com.hazelcast.query.PredicateBuilder;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*    */ import com.hwacom.ngtms.c.fm.model.DeviceHostLocation;
/*    */ import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class DeviceUtils
/*    */ {
/*    */   public static com.hwacom.ngtms.c.shared.TcProtocolType getTcProtocolType(String deviceName)
/*    */   {
/* 16 */     com.hwacom.ngtms.c.fm.model.DeviceTcConfig tcConfig = (com.hwacom.ngtms.c.fm.model.DeviceTcConfig)HzUtils.getMap(CommonFmHzMap.DeviceTcConfig).get(deviceName);
/* 17 */     return tcConfig == null ? null : tcConfig.getProtocolType();
/*    */   }
/*    */   
/*    */   public static DeviceHostLocation getDeviceHostLocation(String deviceName) {
/* 21 */     DeviceHostLocation result = null;
/* 22 */     if ((deviceName == null) || (deviceName.isEmpty())) {
/* 23 */       return result;
/*    */     }
/*    */     
/* 26 */     IMap<String, DeviceLocationMappingConfig> map = HzUtils.getMap(CommonFmHzMap.DeviceLocationMappingConfig);
/* 27 */     IMap<String, DeviceHostLocation> locationMap = HzUtils.getMap(CommonFmHzMap.DeviceHostLocation);
/*    */     
/* 29 */     PredicateBuilder pb = new PredicateBuilder().getEntryObject().get("deviceName").equal(deviceName);
/*    */     
/* 31 */     Iterator localIterator1 = map.values(pb).iterator(); if (localIterator1.hasNext()) { DeviceLocationMappingConfig mapping = (DeviceLocationMappingConfig)localIterator1.next();
/*    */       
/* 33 */       PredicateBuilder locationPb = new PredicateBuilder().getEntryObject().get("locName").equal(mapping.getLocationName());
/* 34 */       Iterator localIterator2 = locationMap.values(locationPb).iterator(); if (localIterator2.hasNext()) { DeviceHostLocation location = (DeviceHostLocation)localIterator2.next();
/* 35 */         result = location;
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 40 */     return result;
/*    */   }
/*    */   
/*    */   public static String getLocationName(String deviceName) {
/* 44 */     String locationName = "";
/* 45 */     if ((deviceName == null) || (deviceName.isEmpty())) {
/* 46 */       return locationName;
/*    */     }
/*    */     
/* 49 */     IMap<String, DeviceLocationMappingConfig> map = HzUtils.getMap(CommonFmHzMap.DeviceLocationMappingConfig);
/*    */     
/* 51 */     PredicateBuilder pb = new PredicateBuilder().getEntryObject().get("deviceName").equal(deviceName);
/*    */     
/* 53 */     Iterator localIterator = map.values(pb).iterator(); if (localIterator.hasNext()) { DeviceLocationMappingConfig mapping = (DeviceLocationMappingConfig)localIterator.next();
/* 54 */       locationName = String.format("%s", new Object[] { mapping.getLocationName() });
/*    */     }
/*    */     
/* 57 */     return locationName;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\util\DeviceUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */