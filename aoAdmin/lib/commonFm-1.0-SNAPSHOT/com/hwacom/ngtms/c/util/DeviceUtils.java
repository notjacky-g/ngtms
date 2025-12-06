/*    */ package com.hwacom.ngtms.c.util;
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hazelcast.query.Predicate;
/*    */ import com.hazelcast.query.PredicateBuilder;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*    */ import com.hwacom.ngtms.c.fm.model.DeviceHostLocation;
/*    */ import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
/*    */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*    */ import com.hwacom.ngtms.c.shared.TcProtocolType;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class DeviceUtils {
/*    */   public static TcProtocolType getTcProtocolType(String deviceName) {
/* 16 */     DeviceTcConfig tcConfig = (DeviceTcConfig)HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig).get(deviceName);
/* 17 */     return (tcConfig == null) ? null : tcConfig.getProtocolType();
/*    */   }
/*    */   
/*    */   public static DeviceHostLocation getDeviceHostLocation(String deviceName) {
/* 21 */     DeviceHostLocation result = null;
/* 22 */     if (deviceName == null || deviceName.isEmpty()) {
/* 23 */       return result;
/*    */     }
/*    */     
/* 26 */     IMap<String, DeviceLocationMappingConfig> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/* 27 */     IMap<String, DeviceHostLocation> locationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceHostLocation);
/*    */     
/* 29 */     PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceName").equal(deviceName);
/*    */     
/* 31 */     Iterator<DeviceLocationMappingConfig> iterator = map.values((Predicate)pb).iterator(); if (iterator.hasNext()) { DeviceLocationMappingConfig mapping = iterator.next();
/*    */       
/* 33 */       PredicateBuilder locationPb = (new PredicateBuilder()).getEntryObject().get("locName").equal(mapping.getLocationName());
/* 34 */       Iterator<DeviceHostLocation> iterator1 = locationMap.values((Predicate)locationPb).iterator(); if (iterator1.hasNext()) { DeviceHostLocation location = iterator1.next();
/* 35 */         result = location; }
/*    */        }
/*    */ 
/*    */ 
/*    */     
/* 40 */     return result;
/*    */   }
/*    */   
/*    */   public static String getLocationName(String deviceName) {
/* 44 */     String locationName = "";
/* 45 */     if (deviceName == null || deviceName.isEmpty()) {
/* 46 */       return locationName;
/*    */     }
/*    */     
/* 49 */     IMap<String, DeviceLocationMappingConfig> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/*    */     
/* 51 */     PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceName").equal(deviceName);
/*    */     
/* 53 */     Iterator<DeviceLocationMappingConfig> iterator = map.values((Predicate)pb).iterator(); if (iterator.hasNext()) { DeviceLocationMappingConfig mapping = iterator.next();
/* 54 */       locationName = String.format("%s", new Object[] { mapping.getLocationName() }); }
/*    */ 
/*    */     
/* 57 */     return locationName;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\\\util\DeviceUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */