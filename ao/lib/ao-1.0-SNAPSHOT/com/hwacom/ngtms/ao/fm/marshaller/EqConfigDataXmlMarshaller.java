/*     */ package com.hwacom.ngtms.ao.fm.marshaller;
/*     */ 
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Am;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.AmEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Avi;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.AviEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Bt;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.BtEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Cctv;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.CctvEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Cms;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.CmsEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Csls;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.CslsEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.EqConfigData;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Et;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.EtEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.EtagDataRow;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.EtagEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Fla;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.FlaEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Fs;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.FsEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Fwl;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.FwlEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Iid;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.IidEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Lcs;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.LcsEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.OnedayEqConfigData;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Pd;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.PdEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Qld;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.QldEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Rd;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.RdEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Rgs;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.RgsEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Rms;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.RmsEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Rvd;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.RvdEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Scs;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.ScsEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Sdh;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.SdhEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Tts;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.TtsEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Tvb;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.TvbEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.UrlInter;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.UrlInterPic;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.UrlIntra;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.UrlIntraOne;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.UrlIntraPic;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Vd;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.VdEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Vi;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.ViEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Wd;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.WdEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Wis;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.WisEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Wss;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.WssEqConfig;
/*     */ import com.thoughtworks.xstream.XStream;
/*     */ import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
/*     */ import com.thoughtworks.xstream.io.naming.NameCoder;
/*     */ import com.thoughtworks.xstream.io.xml.DomDriver;
/*     */ import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.StringReader;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class EqConfigDataXmlMarshaller
/*     */ {
/*  86 */   private static final Logger logger = LoggerFactory.getLogger(EqConfigDataXmlMarshaller.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EqConfigData forXml(String xml) {
/*  96 */     StringReader stringReader = new StringReader(xml);
/*     */     
/*  98 */     return (EqConfigData)getXstream().fromXML(stringReader);
/*     */   }
/*     */   
/*     */   public String convertToXmlString(EqConfigData eqConfigData) {
/* 102 */     XStream xstream = getXstream();
/* 103 */     return xstream.toXML(eqConfigData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshalToXml(EqConfigData eqConfigData, String fileName) {
/* 111 */     try (Writer someWriter = new OutputStreamWriter(new FileOutputStream(fileName), 
/* 112 */           Charset.forName("UTF-8"))) {
/* 113 */       getXstream().toXML(eqConfigData, someWriter);
/* 114 */     } catch (IOException e) {
/* 115 */       logger.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public EqConfigData unMarshal(String fileName) {
/* 120 */     logger.debug("In unMarshal, fileName:'{}'", fileName);
/* 121 */     EqConfigData eqConfigData = null;
/*     */     
/* 123 */     Path resourcePath = Paths.get(fileName, new String[0]);
/*     */     
/* 125 */     try (InputStream is = Files.newInputStream(resourcePath, new java.nio.file.OpenOption[0])) {
/* 126 */       eqConfigData = (EqConfigData)getXstream().fromXML(is);
/* 127 */     } catch (Exception e) {
/* 128 */       logger.error(e.getMessage());
/*     */     } 
/* 130 */     return eqConfigData;
/*     */   }
/*     */ 
/*     */   
/*     */   public EqConfigData unMarshal(Path resourcePath) {
/* 135 */     EqConfigData eqConfigData = null;
/*     */     
/* 137 */     try (InputStream is = Files.newInputStream(resourcePath, new java.nio.file.OpenOption[0])) {
/* 138 */       eqConfigData = (EqConfigData)getXstream().fromXML(is);
/* 139 */     } catch (Exception e) {
/* 140 */       logger.error(e.getMessage());
/*     */     } 
/* 142 */     return eqConfigData;
/*     */   }
/*     */ 
/*     */   
/*     */   public XStream getXstream() {
/* 147 */     XStream xstream = new XStream((HierarchicalStreamDriver)new DomDriver("UTF-8", (NameCoder)new XmlFriendlyNameCoder("_-", "_")));
/*     */     
/* 149 */     xstream.alias("file_attribute", EqConfigData.class);
/*     */     
/* 151 */     xstream.useAttributeFor(EqConfigData.class, "fileName");
/* 152 */     xstream.useAttributeFor(EqConfigData.class, "controlCenterId");
/* 153 */     xstream.useAttributeFor(EqConfigData.class, "time");
/*     */     
/* 155 */     xstream.aliasAttribute("file_name", "fileName");
/* 156 */     xstream.aliasAttribute("control_center_id", "controlCenterId");
/*     */     
/* 158 */     xstream.aliasField("oneday_eq_config_data", EqConfigData.class, "onedayEqConfigData");
/*     */ 
/*     */     
/* 161 */     xstream.aliasField("avi_data", OnedayEqConfigData.class, "aviEqConfig");
/* 162 */     xstream.addImplicitCollection(AviEqConfig.class, "avi", "avi", Avi.class);
/*     */     
/* 164 */     xstream.aliasField("cms_data", OnedayEqConfigData.class, "cmsEqConfig");
/*     */     
/* 166 */     xstream.addImplicitCollection(CmsEqConfig.class, "cms", "cms", Cms.class);
/*     */     
/* 168 */     xstream.useAttributeFor(Cms.class, "eqId");
/* 169 */     xstream.useAttributeFor(Cms.class, "freewayId");
/* 170 */     xstream.useAttributeFor(Cms.class, "expresswayId");
/* 171 */     xstream.useAttributeFor(Cms.class, "directionId");
/* 172 */     xstream.useAttributeFor(Cms.class, "milepost");
/* 173 */     xstream.useAttributeFor(Cms.class, "interchange");
/* 174 */     xstream.useAttributeFor(Cms.class, "eqLocation");
/* 175 */     xstream.useAttributeFor(Cms.class, "cmsType");
/* 176 */     xstream.useAttributeFor(Cms.class, "latitude");
/* 177 */     xstream.useAttributeFor(Cms.class, "longitude");
/* 178 */     xstream.useAttributeFor(Cms.class, "uniqueId");
/*     */     
/* 180 */     xstream.aliasAttribute(Cms.class, "cmsType", "cms_type");
/* 181 */     xstream.aliasAttribute(Cms.class, "eqLocation", "eq_location");
/*     */ 
/*     */     
/* 184 */     xstream.aliasField("lcs_data", OnedayEqConfigData.class, "lcsEqConfig");
/* 185 */     xstream.addImplicitCollection(LcsEqConfig.class, "lcs", "lcs", Lcs.class);
/*     */     
/* 187 */     xstream.useAttributeFor(Lcs.class, "eqId");
/* 188 */     xstream.useAttributeFor(Lcs.class, "freewayId");
/* 189 */     xstream.useAttributeFor(Lcs.class, "expresswayId");
/* 190 */     xstream.useAttributeFor(Lcs.class, "directionId");
/* 191 */     xstream.useAttributeFor(Lcs.class, "milepost");
/* 192 */     xstream.useAttributeFor(Lcs.class, "latitude");
/* 193 */     xstream.useAttributeFor(Lcs.class, "longitude");
/* 194 */     xstream.useAttributeFor(Lcs.class, "uniqueId");
/*     */ 
/*     */     
/* 197 */     xstream.aliasField("csls_data", OnedayEqConfigData.class, "cslsEqConfig");
/* 198 */     xstream.addImplicitCollection(CslsEqConfig.class, "csls", "csls", Csls.class);
/*     */     
/* 200 */     xstream.useAttributeFor(Csls.class, "eqId");
/* 201 */     xstream.useAttributeFor(Csls.class, "freewayId");
/* 202 */     xstream.useAttributeFor(Csls.class, "expresswayId");
/* 203 */     xstream.useAttributeFor(Csls.class, "directionId");
/* 204 */     xstream.useAttributeFor(Csls.class, "milepost");
/* 205 */     xstream.useAttributeFor(Csls.class, "latitude");
/* 206 */     xstream.useAttributeFor(Csls.class, "longitude");
/* 207 */     xstream.useAttributeFor(Csls.class, "uniqueId");
/*     */ 
/*     */     
/* 210 */     xstream.aliasField("fs_data", OnedayEqConfigData.class, "fsEqConfig");
/* 211 */     xstream.addImplicitCollection(FsEqConfig.class, "fs", "fs", Fs.class);
/*     */ 
/*     */     
/* 214 */     xstream.aliasField("rd_data", OnedayEqConfigData.class, "rdEqConfig");
/* 215 */     xstream.addImplicitCollection(RdEqConfig.class, "rd", "rd", Rd.class);
/*     */ 
/*     */     
/* 218 */     xstream.aliasField("rgs_data", OnedayEqConfigData.class, "rgsEqConfig");
/* 219 */     xstream.addImplicitCollection(RgsEqConfig.class, "rgs", "rgs", Rgs.class);
/*     */     
/* 221 */     xstream.useAttributeFor(Rgs.class, "eqId");
/* 222 */     xstream.useAttributeFor(Rgs.class, "freewayId");
/* 223 */     xstream.useAttributeFor(Rgs.class, "expresswayId");
/* 224 */     xstream.useAttributeFor(Rgs.class, "directionId");
/* 225 */     xstream.useAttributeFor(Rgs.class, "milepost");
/* 226 */     xstream.useAttributeFor(Rgs.class, "latitude");
/* 227 */     xstream.useAttributeFor(Rgs.class, "longitude");
/* 228 */     xstream.useAttributeFor(Rgs.class, "uniqueId");
/*     */ 
/*     */     
/* 231 */     xstream.aliasField("rms_data", OnedayEqConfigData.class, "rmsEqConfig");
/* 232 */     xstream.addImplicitCollection(RmsEqConfig.class, "rms", "rms", Rms.class);
/*     */     
/* 234 */     xstream.useAttributeFor(Rms.class, "eqId");
/* 235 */     xstream.useAttributeFor(Rms.class, "freewayId");
/* 236 */     xstream.useAttributeFor(Rms.class, "expresswayId");
/* 237 */     xstream.useAttributeFor(Rms.class, "directionId");
/* 238 */     xstream.useAttributeFor(Rms.class, "milepost");
/* 239 */     xstream.useAttributeFor(Rms.class, "latitude");
/* 240 */     xstream.useAttributeFor(Rms.class, "longitude");
/* 241 */     xstream.useAttributeFor(Rms.class, "uniqueId");
/*     */ 
/*     */     
/* 244 */     xstream.aliasField("tts_data", OnedayEqConfigData.class, "ttsEqConfig");
/* 245 */     xstream.addImplicitCollection(TtsEqConfig.class, "tts", "tts", Tts.class);
/*     */ 
/*     */     
/* 248 */     xstream.aliasField("vd_data", OnedayEqConfigData.class, "vdEqConfig");
/*     */     
/* 250 */     xstream.addImplicitCollection(VdEqConfig.class, "vd", "vd", Vd.class);
/*     */     
/* 252 */     xstream.useAttributeFor(Vd.class, "eqId");
/* 253 */     xstream.useAttributeFor(Vd.class, "freewayId");
/* 254 */     xstream.useAttributeFor(Vd.class, "expresswayId");
/* 255 */     xstream.useAttributeFor(Vd.class, "directionId");
/* 256 */     xstream.useAttributeFor(Vd.class, "longitude");
/* 257 */     xstream.useAttributeFor(Vd.class, "latitude");
/* 258 */     xstream.useAttributeFor(Vd.class, "milepost");
/* 259 */     xstream.useAttributeFor(Vd.class, "uniqueId");
/* 260 */     xstream.useAttributeFor(Vd.class, "eqLocation");
/* 261 */     xstream.useAttributeFor(Vd.class, "interchange");
/* 262 */     xstream.useAttributeFor(Vd.class, "lanes");
/* 263 */     xstream.useAttributeFor(Vd.class, "vdCategory");
/*     */     
/* 265 */     xstream.aliasAttribute(Vd.class, "eqLocation", "eq_location");
/* 266 */     xstream.aliasAttribute(Vd.class, "vdCategory", "vd_category");
/*     */ 
/*     */     
/* 269 */     xstream.aliasField("vi_data", OnedayEqConfigData.class, "viEqConfig");
/* 270 */     xstream.addImplicitCollection(ViEqConfig.class, "vi", "vi", Vi.class);
/*     */     
/* 272 */     xstream.useAttributeFor(Vi.class, "eqId");
/* 273 */     xstream.useAttributeFor(Vi.class, "freewayId");
/* 274 */     xstream.useAttributeFor(Vi.class, "expresswayId");
/* 275 */     xstream.useAttributeFor(Vi.class, "directionId");
/* 276 */     xstream.useAttributeFor(Vi.class, "milepost");
/* 277 */     xstream.useAttributeFor(Vi.class, "latitude");
/* 278 */     xstream.useAttributeFor(Vi.class, "longitude");
/* 279 */     xstream.useAttributeFor(Vi.class, "uniqueId");
/*     */     
/* 281 */     xstream.aliasField("wd_data", OnedayEqConfigData.class, "wdEqConfig");
/* 282 */     xstream.addImplicitCollection(WdEqConfig.class, "wd", "wd", Wd.class);
/*     */     
/* 284 */     xstream.useAttributeFor(Wd.class, "eqId");
/* 285 */     xstream.useAttributeFor(Wd.class, "freewayId");
/* 286 */     xstream.useAttributeFor(Wd.class, "expresswayId");
/* 287 */     xstream.useAttributeFor(Wd.class, "directionId");
/* 288 */     xstream.useAttributeFor(Wd.class, "milepost");
/* 289 */     xstream.useAttributeFor(Wd.class, "latitude");
/* 290 */     xstream.useAttributeFor(Wd.class, "longitude");
/* 291 */     xstream.useAttributeFor(Wd.class, "uniqueId");
/*     */ 
/*     */     
/* 294 */     xstream.aliasField("wis_data", OnedayEqConfigData.class, "wisEqConfig");
/* 295 */     xstream.addImplicitCollection(WisEqConfig.class, "wis", "wis", Wis.class);
/*     */     
/* 297 */     xstream.useAttributeFor(Wis.class, "eqId");
/* 298 */     xstream.useAttributeFor(Wis.class, "freewayId");
/* 299 */     xstream.useAttributeFor(Wis.class, "expresswayId");
/* 300 */     xstream.useAttributeFor(Wis.class, "directionId");
/* 301 */     xstream.useAttributeFor(Wis.class, "milepost");
/* 302 */     xstream.useAttributeFor(Wis.class, "latitude");
/* 303 */     xstream.useAttributeFor(Wis.class, "longitude");
/* 304 */     xstream.useAttributeFor(Wis.class, "uniqueId");
/*     */ 
/*     */     
/* 307 */     xstream.aliasField("cctv_data", OnedayEqConfigData.class, "cctvEqConfig");
/* 308 */     xstream.addImplicitCollection(CctvEqConfig.class, "cctv", "cctv", Cctv.class);
/*     */     
/* 310 */     xstream.useAttributeFor(Cctv.class, "eqId");
/* 311 */     xstream.useAttributeFor(Cctv.class, "freewayId");
/* 312 */     xstream.useAttributeFor(Cctv.class, "expresswayId");
/* 313 */     xstream.useAttributeFor(Cctv.class, "directionId");
/* 314 */     xstream.useAttributeFor(Cctv.class, "longitude");
/* 315 */     xstream.useAttributeFor(Cctv.class, "latitude");
/* 316 */     xstream.useAttributeFor(Cctv.class, "milepost");
/* 317 */     xstream.useAttributeFor(Cctv.class, "uniqueId");
/*     */     
/* 319 */     xstream.aliasField("url_inter", Cctv.class, "urlInter");
/* 320 */     xstream.alias("url_inter", UrlInter.class);
/* 321 */     xstream.aliasField("url_inter", UrlInter.class, "linkurl");
/* 322 */     xstream.useAttributeFor(UrlInter.class, "linkurl");
/*     */     
/* 324 */     xstream.aliasField("url_inter_pic", Cctv.class, "urlInterPic");
/* 325 */     xstream.alias("url_inter_pic", UrlInterPic.class);
/* 326 */     xstream.aliasField("url_intra_pic", UrlInterPic.class, "linkurl");
/* 327 */     xstream.useAttributeFor(UrlInterPic.class, "linkurl");
/*     */     
/* 329 */     xstream.aliasField("url_intra", Cctv.class, "urlIntra");
/* 330 */     xstream.alias("url_intra", UrlIntra.class);
/* 331 */     xstream.aliasField("url_intra", UrlIntra.class, "linkurl");
/* 332 */     xstream.useAttributeFor(UrlIntra.class, "linkurl");
/*     */     
/* 334 */     xstream.aliasField("url_intra_one", Cctv.class, "urlIntraOne");
/* 335 */     xstream.alias("url_intra_one", UrlIntraOne.class);
/* 336 */     xstream.aliasField("url_intra_one", UrlIntraOne.class, "linkurl");
/* 337 */     xstream.useAttributeFor(UrlIntraOne.class, "linkurl");
/*     */     
/* 339 */     xstream.aliasField("url_intra_pic", Cctv.class, "urlIntraPic");
/* 340 */     xstream.alias("url_intra_pic", UrlIntraPic.class);
/* 341 */     xstream.aliasField("url_intra_pic", UrlIntraPic.class, "linkurl");
/* 342 */     xstream.useAttributeFor(UrlIntraPic.class, "linkurl");
/*     */ 
/*     */     
/* 345 */     xstream.aliasField("iid_data", OnedayEqConfigData.class, "iidEqConfig");
/*     */     
/* 347 */     xstream.addImplicitCollection(IidEqConfig.class, "iid", "iid", Iid.class);
/*     */     
/* 349 */     xstream.useAttributeFor(Iid.class, "eqId");
/* 350 */     xstream.useAttributeFor(Iid.class, "freewayId");
/* 351 */     xstream.useAttributeFor(Iid.class, "expresswayId");
/* 352 */     xstream.useAttributeFor(Iid.class, "directionId");
/* 353 */     xstream.useAttributeFor(Iid.class, "longitude");
/* 354 */     xstream.useAttributeFor(Iid.class, "latitude");
/* 355 */     xstream.useAttributeFor(Iid.class, "milepost");
/* 356 */     xstream.useAttributeFor(Iid.class, "uniqueId");
/*     */ 
/*     */     
/* 359 */     xstream.aliasField("scs_data", OnedayEqConfigData.class, "scsEqConfig");
/* 360 */     xstream.addImplicitCollection(ScsEqConfig.class, "scs", "scs", Scs.class);
/*     */     
/* 362 */     xstream.useAttributeFor(Scs.class, "eqId");
/* 363 */     xstream.useAttributeFor(Scs.class, "freewayId");
/* 364 */     xstream.useAttributeFor(Scs.class, "expresswayId");
/* 365 */     xstream.useAttributeFor(Scs.class, "directionId");
/* 366 */     xstream.useAttributeFor(Scs.class, "longitude");
/* 367 */     xstream.useAttributeFor(Scs.class, "latitude");
/* 368 */     xstream.useAttributeFor(Scs.class, "milepost");
/* 369 */     xstream.useAttributeFor(Scs.class, "uniqueId");
/*     */ 
/*     */     
/* 372 */     xstream.aliasField("eTag_data", OnedayEqConfigData.class, "etagEqConfig");
/* 373 */     xstream.addImplicitCollection(EtagEqConfig.class, "etagDataList", "eTag", EtagDataRow.class);
/*     */     
/* 375 */     xstream.useAttributeFor(EtagDataRow.class, "eqId");
/* 376 */     xstream.useAttributeFor(EtagDataRow.class, "freewayId");
/* 377 */     xstream.useAttributeFor(EtagDataRow.class, "expresswayId");
/* 378 */     xstream.useAttributeFor(EtagDataRow.class, "directionId");
/* 379 */     xstream.useAttributeFor(EtagDataRow.class, "longitude");
/* 380 */     xstream.useAttributeFor(EtagDataRow.class, "latitude");
/* 381 */     xstream.useAttributeFor(EtagDataRow.class, "milepost");
/*     */     
/* 383 */     xstream.aliasField("etag_data", OnedayEqConfigData.class, "etagEqConfig");
/* 384 */     xstream.addImplicitCollection(EtagEqConfig.class, "etagDataList", "etag", EtagDataRow.class);
/*     */     
/* 386 */     xstream.useAttributeFor(EtagDataRow.class, "eqId");
/* 387 */     xstream.useAttributeFor(EtagDataRow.class, "freewayId");
/* 388 */     xstream.useAttributeFor(EtagDataRow.class, "expresswayId");
/* 389 */     xstream.useAttributeFor(EtagDataRow.class, "directionId");
/* 390 */     xstream.useAttributeFor(EtagDataRow.class, "longitude");
/* 391 */     xstream.useAttributeFor(EtagDataRow.class, "latitude");
/* 392 */     xstream.useAttributeFor(EtagDataRow.class, "milepost");
/*     */ 
/*     */     
/* 395 */     xstream.aliasField("et_data", OnedayEqConfigData.class, "etEqConfig");
/* 396 */     xstream.addImplicitCollection(EtEqConfig.class, "et", "et", Et.class);
/*     */     
/* 398 */     xstream.useAttributeFor(Et.class, "eqId");
/* 399 */     xstream.useAttributeFor(Et.class, "freewayId");
/* 400 */     xstream.useAttributeFor(Et.class, "expresswayId");
/* 401 */     xstream.useAttributeFor(Et.class, "directionId");
/* 402 */     xstream.useAttributeFor(Et.class, "longitude");
/* 403 */     xstream.useAttributeFor(Et.class, "latitude");
/* 404 */     xstream.useAttributeFor(Et.class, "milepost");
/* 405 */     xstream.useAttributeFor(Et.class, "uniqueId");
/*     */ 
/*     */     
/* 408 */     xstream.aliasField("qld_data", OnedayEqConfigData.class, "qldEqConfig");
/* 409 */     xstream.addImplicitCollection(QldEqConfig.class, "qld", "qld", Qld.class);
/*     */     
/* 411 */     xstream.useAttributeFor(Qld.class, "eqId");
/* 412 */     xstream.useAttributeFor(Qld.class, "freewayId");
/* 413 */     xstream.useAttributeFor(Qld.class, "expresswayId");
/* 414 */     xstream.useAttributeFor(Qld.class, "directionId");
/* 415 */     xstream.useAttributeFor(Qld.class, "longitude");
/* 416 */     xstream.useAttributeFor(Qld.class, "latitude");
/* 417 */     xstream.useAttributeFor(Qld.class, "milepost");
/* 418 */     xstream.useAttributeFor(Qld.class, "uniqueId");
/*     */ 
/*     */     
/* 421 */     xstream.aliasField("fla_data", OnedayEqConfigData.class, "flaEqConfig");
/* 422 */     xstream.addImplicitCollection(FlaEqConfig.class, "fla", "fla", Fla.class);
/*     */ 
/*     */     
/* 425 */     xstream.aliasField("fwl_data", OnedayEqConfigData.class, "fwlEqConfig");
/* 426 */     xstream.addImplicitCollection(FwlEqConfig.class, "fwl", "fwl", Fwl.class);
/*     */ 
/*     */     
/* 429 */     xstream.aliasField("wss_data", OnedayEqConfigData.class, "wssEqConfig");
/* 430 */     xstream.addImplicitCollection(WssEqConfig.class, "wss", "wss", Wss.class);
/*     */ 
/*     */     
/* 433 */     xstream.aliasField("am_data", OnedayEqConfigData.class, "amEqConfig");
/* 434 */     xstream.addImplicitCollection(AmEqConfig.class, "am", "am", Am.class);
/*     */ 
/*     */     
/* 437 */     xstream.aliasField("rvd_data", OnedayEqConfigData.class, "rvdEqConfig");
/* 438 */     xstream.addImplicitCollection(RvdEqConfig.class, "rvd", "rvd", Rvd.class);
/*     */ 
/*     */     
/* 441 */     xstream.aliasField("tvb_data", OnedayEqConfigData.class, "tvbEqConfig");
/* 442 */     xstream.addImplicitCollection(TvbEqConfig.class, "tvb", "tvb", Tvb.class);
/*     */ 
/*     */     
/* 445 */     xstream.aliasField("sdh_data", OnedayEqConfigData.class, "sdhEqConfig");
/* 446 */     xstream.addImplicitCollection(SdhEqConfig.class, "sdh", "sdh", Sdh.class);
/*     */ 
/*     */     
/* 449 */     xstream.aliasField("pd_data", OnedayEqConfigData.class, "pdEqConfig");
/* 450 */     xstream.addImplicitCollection(PdEqConfig.class, "pd", "pd", Pd.class);
/*     */ 
/*     */     
/* 453 */     xstream.aliasField("bt_data", OnedayEqConfigData.class, "btEqConfig");
/* 454 */     xstream.addImplicitCollection(BtEqConfig.class, "bt", "bt", Bt.class);
/*     */     
/* 456 */     return xstream;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\marshaller\EqConfigDataXmlMarshaller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */