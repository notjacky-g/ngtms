/*     */ package com.hwacom.ngtms.hcce.web;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.hcce.hz.HcHzMap;
/*     */ import com.hwacom.ngtms.hcce.web.vo.ValidationData;
/*     */ import com.ibm.icu.util.Calendar;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Base64;
/*     */ import java.util.Base64.Encoder;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import javax.imageio.ImageIO;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.web.bind.annotation.CrossOrigin;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CrossOrigin
/*     */ @RestController
/*     */ @RequestMapping({"/api/validation"})
/*     */ public class ValidationCodeRestServiceImpl
/*     */ {
/*  43 */   private static final Logger logger = LoggerFactory.getLogger(ValidationCodeRestServiceImpl.class);
/*     */   @Autowired
/*     */   protected Environment environment;
/*  46 */   private static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
/*     */   
/*     */ 
/*     */   @RequestMapping(value={"/genValidateCode"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String genValidateCode()
/*     */   {
/*     */     try
/*     */     {
/*  54 */       IMap<String, ValidationData> userMap = HzUtils.getMap(HcHzMap.UserValidateCode);
/*  55 */       Calendar ca = Calendar.getInstance();
/*  56 */       ca.add(12, -1);
/*     */       
/*  58 */       PredicateBuilder pb = new PredicateBuilder().getEntryObject().get("date").lessThan(ca.getTime());
/*  59 */       for (ValidationData d : userMap.values(pb)) {
/*  60 */         userMap.remove(d.getId());
/*     */       }
/*     */       
/*  63 */       String result = "";
/*  64 */       String code = "";
/*  65 */       String uuid = UUID.randomUUID().toString();
/*  66 */       ValidationData data = new ValidationData();
/*     */       try {
/*  68 */         List<String> list = generateVerificationCode(200, 50, 4, "PNG");
/*  69 */         result = uuid + ";" + (String)list.get(1);
/*  70 */         code = (String)list.get(0);
/*     */       } catch (Exception e) {
/*  72 */         logger.debug("gen pic error");
/*     */       }
/*  74 */       data.setCode(code);
/*  75 */       data.setDate(new Date());
/*  76 */       data.setId(uuid);
/*  77 */       userMap.put(uuid, data);
/*  78 */       return result;
/*     */     } catch (RuntimeException ex) {
/*  80 */       logger.warn("gen validate code failed!", ex); }
/*  81 */     return null;
/*     */   }
/*     */   
/*     */   @RequestMapping(value={"/validateCodeCheck/{user}/{code}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public Boolean validateCodeCheck(@PathVariable("user") String user, @PathVariable("code") String code)
/*     */   {
/*     */     try
/*     */     {
/*  89 */       Boolean result = Boolean.valueOf(false);
/*  90 */       IMap<String, ValidationData> userMap = HzUtils.getMap(HcHzMap.UserValidateCode);
/*  91 */       if ((userMap.get(user) != null) && (code.equals(((ValidationData)userMap.get(user)).getCode()))) {
/*  92 */         result = Boolean.valueOf(true);
/*     */       }
/*  94 */       userMap.remove(user);
/*  95 */       return result;
/*     */     } catch (RuntimeException ex) {
/*  97 */       logger.warn("user validate failed!", ex); }
/*  98 */     return Boolean.valueOf(false);
/*     */   }
/*     */   
/*     */   public List<String> generateVerificationCode(int width, int height, int codeCount, String imgFormat)
/*     */     throws IOException
/*     */   {
/* 104 */     List<String> list = new ArrayList();
/* 105 */     BufferedImage bufferedImage = new BufferedImage(width, height, 1);
/* 106 */     Graphics2D graphics = bufferedImage.createGraphics();
/* 107 */     graphics.setColor(Color.WHITE);
/* 108 */     graphics.fillRect(0, 0, width, height);
/* 109 */     graphics.setColor(Color.BLACK);
/* 110 */     graphics.drawRect(0, 0, width - 1, height - 1);
/* 111 */     Font font = new Font("Fixedsys", 0, height - 2);
/* 112 */     graphics.setFont(font);
/*     */     
/* 114 */     Random random = new Random();
/* 115 */     for (int i = 0; i < codeCount * 2; i++) {
/* 116 */       graphics.setColor(getRandomColor());
/* 117 */       graphics.drawLine(random
/* 118 */         .nextInt(width), random
/* 119 */         .nextInt(height), random
/* 120 */         .nextInt(width), random
/* 121 */         .nextInt(height));
/*     */     }
/* 123 */     for (int i = 0; i < codeCount * 3; i++) {
/* 124 */       int x = random.nextInt(width);
/* 125 */       int y = random.nextInt(height);
/* 126 */       graphics.setColor(getRandomColor());
/* 127 */       graphics.fillRect(x, y, 2, 2);
/*     */     }
/*     */     
/* 130 */     StringBuffer randomCode = new StringBuffer();
/* 131 */     int charWidth = width / (codeCount + 2);
/* 132 */     int charHeight = height - 5;
/* 133 */     for (int i = 0; i < codeCount; i++) {
/* 134 */       int x = (i + 1) * charWidth;
/* 135 */       String strRandom = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
/* 136 */       randomCode.append(strRandom);
/* 137 */       graphics.setColor(getRandomColor());
/* 138 */       int degree = random.nextInt() % 30;
/* 139 */       graphics.rotate(degree * 3.141592653589793D / 180.0D, x, 45.0D);
/* 140 */       graphics.drawString(strRandom, x, charHeight);
/* 141 */       graphics.rotate(-degree * 3.141592653589793D / 180.0D, x, 45.0D);
/*     */     }
/*     */     
/* 144 */     ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 145 */     ImageIO.write(bufferedImage, imgFormat, os);
/* 146 */     list.add(randomCode.toString());
/* 147 */     list.add(Base64.getEncoder().encodeToString(os.toByteArray()));
/* 148 */     return list;
/*     */   }
/*     */   
/*     */   private Color getRandomColor() {
/* 152 */     Random ran = new Random();
/* 153 */     Color color = new Color(ran.nextInt(128), ran.nextInt(128), ran.nextInt(128));
/* 154 */     return color;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\web\ValidationCodeRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */