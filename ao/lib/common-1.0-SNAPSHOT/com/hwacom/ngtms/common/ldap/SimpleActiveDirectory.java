/*     */ package com.hwacom.ngtms.common.ldap;
/*     */ 
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.oplog.service.BaseOpLogger;
/*     */ import com.hwacom.ngtms.base.oplog.shared.CoreSystem;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.common.fm.model.Role;
/*     */ import com.hwacom.ngtms.common.fm.model.User;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.BasicAttribute;
/*     */ import javax.naming.directory.BasicAttributes;
/*     */ import javax.naming.directory.ModificationItem;
/*     */ import javax.naming.directory.SearchControls;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.ldap.core.AttributesMapper;
/*     */ import org.springframework.ldap.core.DirContextAdapter;
/*     */ import org.springframework.ldap.core.LdapTemplate;
/*     */ import org.springframework.ldap.core.support.LdapContextSource;
/*     */ import org.springframework.ldap.filter.AndFilter;
/*     */ import org.springframework.ldap.filter.EqualsFilter;
/*     */ import org.springframework.ldap.query.ConditionCriteria;
/*     */ import org.springframework.ldap.query.ContainerCriteria;
/*     */ import org.springframework.ldap.query.LdapQuery;
/*     */ import org.springframework.ldap.query.LdapQueryBuilder;
/*     */ import org.springframework.ldap.support.LdapNameBuilder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleActiveDirectory
/*     */ {
/*  51 */   private static Logger logger = LoggerFactory.getLogger(SimpleActiveDirectory.class);
/*     */   
/*     */   private static final int UF_ACCOUNTENABLE = 1;
/*     */   
/*     */   private static final int UF_PASSWORD_EXPIRED = 8388608;
/*     */   
/*     */   private static final int UF_TEMP_DUPLICATE_ACCOUNT = 256;
/*     */   
/*     */   private static final int UF_NORMAL_ACCOUNT = 512;
/*     */   
/*     */   private static final int UF_INTERDOMAIN_TRUST_ACCOUNT = 2048;
/*     */   
/*     */   private static final int UF_WORKSTATION_TRUST_ACCOUNT = 4096;
/*     */   
/*     */   private static final int UF_SERVER_TRUST_ACCOUNT = 8192;
/*     */   
/*     */   private static final int UF_DONT_EXPIRE_PASSWD = 65536;
/*     */   
/*     */   private static final int UF_SCRIPT = 1;
/*     */   
/*     */   private static final int UF_ACCOUNTDISABLE = 2;
/*     */   
/*     */   private static final int UF_HOMEDIR_REQUIRED = 8;
/*     */   
/*     */   private static final int UF_LOCKOUT = 16;
/*     */   
/*     */   private static final int UF_PASSWD_NOTREQD = 32;
/*     */   
/*     */   private static final int UF_PASSWD_CANT_CHANGE = 64;
/*     */   
/*     */   private static final int UF_ACCOUNT_LOCKOUT = 16;
/*     */   
/*     */   private static final int UF_ENCRYPTED_TEXT_PASSWORD_ALLOWED = 128;
/*     */   
/*     */   private static final int UF_USE_DES_KEY_ONLY = 2097152;
/*     */   
/*     */   private static final int UF_DONT_REQUIRE_PREAUTH = 67108864;
/*     */   
/*     */   private static final long MAX_END_DATE = 4112735757541L;
/*     */   
/*     */   private static final long FILETIME_EPOCH_DIFF = 11644473600000L;
/*     */   
/*     */   private static final long FILETIME_ONE_MILLISECOND = 10000L;
/*     */   
/*     */   @Value("${security.ad.searchBase:cn=Users}")
/*     */   private String searchBase;
/*     */   
/*     */   @Value("${security.ad.roleLikes:tcs,emm,ao}")
/*     */   private String roleLikes;
/*     */   
/*     */   @Autowired(required=true)
/*     */   @Qualifier("ldapTemplate")
/*     */   private LdapTemplate ldapTemplate;
/*     */   
/*     */   @Autowired(required=true)
/*     */   @Qualifier("contextSource")
/*     */   private LdapContextSource ldapContextSource;
/*     */   
/*     */   @Autowired
/*     */   private BaseOpLogger opLogger;
/*     */   
/*     */ 
/*     */   @PostConstruct
/*     */   private void init()
/*     */   {
/* 116 */     this.ldapTemplate.setIgnorePartialResultException(true);
/*     */   }
/*     */   
/*     */   public boolean authenticateByName(String base, String userName, String password) {
/* 120 */     logger.debug("executing {authenticate}");
/* 121 */     if ((base == null) || ("".equals(base.trim()))) base = this.searchBase;
/* 122 */     boolean result = this.ldapTemplate.authenticate(base, "(cn=" + userName + ")", password);
/*     */     
/* 124 */     OperationResult opResult = OperationResult.SUCCESS;
/* 125 */     if (result) {
/* 126 */       opResult = OperationResult.SUCCESS;
/*     */     } else {
/* 128 */       opResult = OperationResult.FAILURE;
/*     */     }
/* 130 */     this.opLogger.addLog(userName, 
/*     */     
/* 132 */       HzUtils.getLocalMemberIpAddress(), CoreSystem.HCCE
/* 133 */       .toString(), OperationItem.GET, null, new Date(), opResult, null, "user.login", new Object[0]);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 140 */     return result;
/*     */   }
/*     */   
/*     */   public boolean authenticate(String base, String login, String password) {
/* 144 */     logger.debug("executing {authenticate}");
/* 145 */     if ((base == null) || ("".equals(base.trim()))) base = this.searchBase;
/* 146 */     boolean result = this.ldapTemplate.authenticate(base, "(sAMAccountName=" + login + ")", password);
/*     */     
/* 148 */     OperationResult opResult = OperationResult.SUCCESS;
/* 149 */     if (result) {
/* 150 */       opResult = OperationResult.SUCCESS;
/*     */     } else {
/* 152 */       opResult = OperationResult.FAILURE;
/*     */     }
/* 154 */     this.opLogger.addLog(login, 
/*     */     
/* 156 */       HzUtils.getLocalMemberIpAddress(), CoreSystem.HCCE
/* 157 */       .toString(), OperationItem.GET, null, new Date(), opResult, null, "user.login", new Object[0]);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 164 */     return result;
/*     */   }
/*     */   
/*     */   public List<String> getAllUserNames() {
/* 168 */     logger.debug("searchBase:" + this.searchBase);
/* 169 */     LdapQuery query = LdapQueryBuilder.query().base(this.searchBase);
/* 170 */     List<String> list = this.ldapTemplate.list(query.base());
/* 171 */     logger.debug("Users -> " + list);
/* 172 */     List<String> userNames = new ArrayList();
/* 173 */     for (User user : getAllUsers()) {
/* 174 */       userNames.add(user.getName());
/*     */     }
/* 176 */     logger.debug("userNames:" + userNames);
/* 177 */     return userNames;
/*     */   }
/*     */   
/*     */   public List<User> getAllUsers() {
/* 181 */     logger.debug("searchBase:" + this.searchBase);
/* 182 */     SearchControls controls = new SearchControls();
/* 183 */     controls.setSearchScope(2);
/*     */     
/* 185 */     List<User> users = this.ldapTemplate.search(
/* 186 */       LdapQueryBuilder.query().base(this.searchBase).where("objectClass").is("person"), new UserAttributesMapper(null));
/* 187 */     List<User> result = new ArrayList();
/* 188 */     List<String> roleNames = getAllGroupNames();
/* 189 */     for (User user : users) {
/* 190 */       List<String> matchedRoles = new ArrayList(user.getRoleNames());
/* 191 */       matchedRoles.retainAll(roleNames);
/* 192 */       if (!matchedRoles.isEmpty()) {
/* 193 */         result.add(user);
/*     */       }
/*     */     }
/* 196 */     logger.debug("result:" + result);
/* 197 */     return result;
/*     */   }
/*     */   
/*     */   public User getUserDetails(String userName)
/*     */   {
/* 202 */     List<User> list = this.ldapTemplate.search(
/* 203 */       LdapQueryBuilder.query().base(this.searchBase).where("cn").is(userName), new UserAttributesMapper(null));
/* 204 */     if ((list != null) && (!list.isEmpty())) {
/* 205 */       return (User)list.get(0);
/*     */     }
/* 207 */     return null;
/*     */   }
/*     */   
/*     */   public User getUserDetailsByLogin(String login)
/*     */   {
/* 212 */     List<User> list = this.ldapTemplate.search(
/* 213 */       LdapQueryBuilder.query().base(this.searchBase).where("sAMAccountName").is(login), new UserAttributesMapper(null));
/* 214 */     if ((list != null) && (!list.isEmpty())) {
/* 215 */       return (User)list.get(0);
/*     */     }
/* 217 */     return null;
/*     */   }
/*     */   
/*     */   public String printUserDetails(String userName)
/*     */   {
/* 222 */     List<String> list = this.ldapTemplate.search(
/* 223 */       LdapQueryBuilder.query().base(this.searchBase).where("cn").is(userName), new MultipleAttributesMapper(null));
/* 224 */     if ((list != null) && (!list.isEmpty())) {
/* 225 */       return (String)list.get(0);
/*     */     }
/* 227 */     return null;
/*     */   }
/*     */   
/*     */   public String printUserDetailsByLogin(String login)
/*     */   {
/* 232 */     List<String> list = this.ldapTemplate.search(
/* 233 */       LdapQueryBuilder.query().base(this.searchBase).where("sAMAccountName").is(login), new MultipleAttributesMapper(null));
/*     */     
/* 235 */     if ((list != null) && (!list.isEmpty())) {
/* 236 */       return (String)list.get(0);
/*     */     }
/* 238 */     return null;
/*     */   }
/*     */   
/*     */   public void createUser(User user) {
/*     */     try {
/* 243 */       Attributes personAttributes = new BasicAttributes(true);
/* 244 */       personAttributes.put("objectclass", "organizationalPerson");
/* 245 */       personAttributes.put("objectclass", "person");
/* 246 */       personAttributes.put("objectclass", "top");
/* 247 */       personAttributes.put("objectclass", "user");
/* 248 */       personAttributes.put("cn", user.getName());
/* 249 */       personAttributes.put("sAMAccountName", user.getLogin());
/* 250 */       if (user.getEndTime() != null) {
/* 251 */         personAttributes.put("accountExpires", 
/* 252 */           Long.toString(millisToFiletime(user.getEndTime().getTime())));
/*     */       }
/* 254 */       personAttributes.put("displayName", user.getName());
/* 255 */       personAttributes.put("userPrincipalName", user
/*     */       
/* 257 */         .getLogin() + "@" + toHostName(this.ldapContextSource.getBaseLdapPathAsString()));
/* 258 */       personAttributes.put("mail", user.getMail());
/* 259 */       personAttributes.put("mobile", user.getMobile());
/* 260 */       personAttributes.put("description", user.getDescription());
/*     */       
/* 262 */       int userAccountControl = 544;
/* 263 */       if (user.getEnable().booleanValue()) {
/* 264 */         userAccountControl++;
/*     */       } else {
/* 266 */         userAccountControl += 2;
/*     */       }
/* 268 */       personAttributes.put("userAccountControl", Integer.toString(userAccountControl));
/*     */       
/*     */ 
/* 271 */       Name userDN = toDistinguishedName(user.getLogin());
/* 272 */       this.ldapTemplate.bind(userDN, null, personAttributes);
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*     */ 
/* 279 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeUser(String name)
/*     */   {
/*     */     try {
/* 286 */       Name userDN = toDistinguishedName(name);
/* 287 */       this.ldapTemplate.unbind(userDN);
/*     */     } catch (Exception ex) {
/* 289 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateUser(User user) {
/*     */     try {
/* 295 */       ModificationItem[] mods = new ModificationItem[5];
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 303 */       mods[0] = new ModificationItem(2, new BasicAttribute("cn", user
/*     */       
/* 305 */         .getName()));
/*     */       
/* 307 */       mods[1] = new ModificationItem(2, new BasicAttribute("mail", user
/*     */       
/* 309 */         .getMail()));
/*     */       
/* 311 */       mods[2] = new ModificationItem(2, new BasicAttribute("mobile", user
/*     */       
/* 313 */         .getMobile()));
/*     */       
/* 315 */       int userAccountControl = 544;
/* 316 */       if (user.getEnable().booleanValue()) {
/* 317 */         userAccountControl++;
/*     */       } else {
/* 319 */         userAccountControl += 2;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 324 */       mods[3] = new ModificationItem(2, new BasicAttribute("userAccountControl", Integer.toString(userAccountControl)));
/*     */       
/* 326 */       mods[4] = new ModificationItem(2, new BasicAttribute("accountExpires", 
/*     */       
/*     */ 
/* 329 */         Long.toString(user.getEndTime().getTime())));
/*     */       
/* 331 */       Name userDN = toDistinguishedName(user.getName());
/* 332 */       this.ldapTemplate.modifyAttributes(userDN, mods);
/*     */       
/* 334 */       User oldUser = getUserDetails(user.getName());
/*     */       
/* 336 */       if ((oldUser != null) && (oldUser.getRoleNames() != null) && (!oldUser.getRoleNames().isEmpty())) {
/* 337 */         for (String roleName : oldUser.getRoleNames()) {
/* 338 */           removeUserFromGroup(oldUser.getName(), roleName);
/*     */         }
/*     */       }
/*     */       
/* 342 */       for (String roleName : user.getRoleNames()) {
/* 343 */         addUserToGroup(user.getName(), roleName);
/*     */       }
/*     */     } catch (Exception ex) {
/* 346 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateUserPassword(String username, String password)
/*     */   {
/*     */     try
/*     */     {
/* 358 */       ModificationItem[] mods = new ModificationItem[1];
/* 359 */       mods[0] = new ModificationItem(2, new BasicAttribute("unicodePwd", 
/*     */       
/*     */ 
/* 362 */         encodePassword(password)));
/* 363 */       Name userDN = toDistinguishedName(username);
/* 364 */       this.ldapTemplate.modifyAttributes(userDN, mods);
/*     */     } catch (Exception ex) {
/* 366 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     }
/*     */   }
/*     */   
/*     */   public User updateUserDisplayName(String username, String displayName) {
/* 371 */     Name userDN = toDistinguishedName(username);
/* 372 */     ModificationItem item = new ModificationItem(2, new BasicAttribute("displayName", displayName));
/*     */     
/*     */ 
/* 375 */     this.ldapTemplate.modifyAttributes(userDN, new ModificationItem[] { item });
/* 376 */     return getUserDetails(username);
/*     */   }
/*     */   
/*     */   public void addUserToGroup(String userName, String roleName) throws NamingException {
/* 380 */     Name userDN = toFullDistinguishedName(userName);
/* 381 */     Name groupDN = toDistinguishedName(roleName);
/* 382 */     ModificationItem[] mods = new ModificationItem[1];
/* 383 */     mods[0] = new ModificationItem(1, new BasicAttribute("member", userDN
/*     */     
/* 385 */       .toString()));
/* 386 */     this.ldapTemplate.modifyAttributes(groupDN, mods);
/*     */   }
/*     */   
/*     */   public void removeUserFromGroup(String userName, String roleName) throws NamingException {
/* 390 */     Name userDN = toFullDistinguishedName(userName);
/* 391 */     Name groupDN = toDistinguishedName(roleName);
/* 392 */     ModificationItem[] mods = new ModificationItem[1];
/* 393 */     mods[0] = new ModificationItem(3, new BasicAttribute("member", userDN
/*     */     
/* 395 */       .toString()));
/* 396 */     this.ldapTemplate.modifyAttributes(groupDN, mods);
/*     */   }
/*     */   
/*     */   public void createGroup(Role role) {
/*     */     try {
/* 401 */       Name roleDN = toDistinguishedName(role.getName());
/* 402 */       DirContextAdapter ctx = new DirContextAdapter(roleDN);
/* 403 */       ctx.setAttributeValues("objectclass", new String[] { "top", "group" });
/*     */       
/* 405 */       ctx.setAttributeValue("sAMAccountName", role.getName());
/* 406 */       ctx.setAttributeValue("description", role.getDescription());
/* 407 */       this.ldapTemplate.bind(ctx);
/*     */     } catch (Exception ex) {
/* 409 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateGroup(Role role) {
/*     */     try {
/* 415 */       Name groupDN = toDistinguishedName(role.getName());
/* 416 */       ModificationItem[] mods = new ModificationItem[1];
/* 417 */       mods[0] = new ModificationItem(2, new BasicAttribute("description", role
/*     */       
/*     */ 
/* 420 */         .getDescription()));
/* 421 */       this.ldapTemplate.modifyAttributes(groupDN, mods);
/*     */     } catch (Exception ex) {
/* 423 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeGroup(String name)
/*     */   {
/*     */     try {
/* 430 */       Name roleDN = toDistinguishedName(name);
/* 431 */       this.ldapTemplate.unbind(roleDN);
/*     */     } catch (Exception ex) {
/* 433 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<String> getAllGroupNames() {
/* 438 */     return getAllGroups(cnMapper());
/*     */   }
/*     */   
/*     */   private AttributesMapper<String> cnMapper() {
/* 442 */     new AttributesMapper()
/*     */     {
/*     */       public String mapFromAttributes(Attributes attributes) throws NamingException {
/* 445 */         return (String)attributes.get("cn").get();
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public List<String> getGroupNamesLike(String name) {
/* 451 */     return getGroupLike(name, cnMapper());
/*     */   }
/*     */   
/*     */   private <T> List<T> getAllGroups(AttributesMapper<T> mapper) {
/* 455 */     List<T> roleList = new ArrayList();
/* 456 */     String[] like = this.roleLikes.split(",");
/* 457 */     for (int i = 0; i < like.length; i++) {
/* 458 */       roleList.addAll(getGroupLike(like[i], mapper));
/*     */     }
/* 460 */     return roleList;
/*     */   }
/*     */   
/*     */   private <T> List<T> getGroupLike(String roleName, AttributesMapper<T> mapper)
/*     */   {
/* 465 */     LdapQuery query = LdapQueryBuilder.query().base(this.searchBase).where("objectclass").is("group").and("cn").like(roleName + "*");
/* 466 */     return this.ldapTemplate.search(query, mapper);
/*     */   }
/*     */   
/*     */   public List<Role> getAllGroups() {
/* 470 */     return getAllGroups(new RoleAttributesMapper(null));
/*     */   }
/*     */   
/*     */   public List<Role> getGroupLike(String name) {
/* 474 */     return getGroupLike(name, new RoleAttributesMapper(null));
/*     */   }
/*     */   
/*     */   public List<String> getMembersByGroupName(String roleName)
/*     */   {
/* 479 */     AndFilter andFilter = new AndFilter();
/* 480 */     andFilter.and(new EqualsFilter("objectclass", "person"));
/* 481 */     andFilter.and(new EqualsFilter("memberOf", toFullDistinguishedName(roleName).toString()));
/* 482 */     SearchControls controls = new SearchControls();
/* 483 */     controls.setSearchScope(2);
/* 484 */     this.ldapTemplate.setIgnorePartialResultException(true);
/*     */     
/* 486 */     List<String> list = this.ldapTemplate.search("", andFilter
/*     */     
/* 488 */       .encode(), new AttributesMapper()
/*     */       {
/*     */         public Object mapFromAttributes(Attributes attrs) throws NamingException, NamingException
/*     */         {
/* 492 */           return attrs.get("cn").get();
/*     */         }
/* 494 */       });
/* 495 */     return list;
/*     */   }
/*     */   
/*     */   private long filetimeToMillis(long filetime) {
/* 499 */     return filetime / 10000L - 11644473600000L;
/*     */   }
/*     */   
/*     */   private long millisToFiletime(long millis) {
/* 503 */     return (millis + 11644473600000L) * 10000L;
/*     */   }
/*     */   
/*     */   private String toHostName(String ldapPath) {
/* 507 */     String[] values = ldapPath.split(",");
/* 508 */     StringBuffer sb = new StringBuffer();
/* 509 */     for (int i = 0; i < values.length; i++) {
/* 510 */       sb.append(values[i].substring(3)).append(".");
/*     */     }
/* 512 */     return sb.toString().substring(0, sb.toString().length() - 1);
/*     */   }
/*     */   
/*     */   private Name toDistinguishedName(String name) {
/* 516 */     return LdapNameBuilder.newInstance().add(this.searchBase).add("CN=" + name).build();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private Name toFullDistinguishedName(String name)
/*     */   {
/* 523 */     return LdapNameBuilder.newInstance(this.ldapContextSource.getBaseLdapPathAsString()).add(this.searchBase).add("CN=" + name).build();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private byte[] encodePassword(String password)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 531 */     String quotedPassword = "\"" + password + "\"";
/* 532 */     char[] unicodePwd = quotedPassword.toCharArray();
/* 533 */     byte[] pwdArray = new byte[unicodePwd.length * 2];
/* 534 */     for (int i = 0; i < unicodePwd.length; i++) {
/* 535 */       pwdArray[(i * 2 + 1)] = ((byte)(unicodePwd[i] >>> '\b'));
/* 536 */       pwdArray[(i * 2 + 0)] = ((byte)(unicodePwd[i] & 0xFF));
/*     */     }
/* 538 */     return pwdArray;
/*     */   }
/*     */   
/*     */   private class UserAttributesMapper implements AttributesMapper<User> {
/*     */     private UserAttributesMapper() {}
/*     */     
/*     */     public User mapFromAttributes(Attributes attributes) throws NamingException {
/* 545 */       if (attributes == null) {
/* 546 */         return null;
/*     */       }
/* 548 */       User user = new User();
/*     */       try {
/* 550 */         user.setLogin((String)SimpleActiveDirectory.this.getAttribute(attributes, "sAMAccountName"));
/* 551 */         user.setName((String)SimpleActiveDirectory.this.getAttribute(attributes, "cn"));
/* 552 */         user.setMail((String)SimpleActiveDirectory.this.getAttribute(attributes, "mail"));
/* 553 */         user.setMobile((String)SimpleActiveDirectory.this.getAttribute(attributes, "mobile"));
/* 554 */         user.setDescription((String)SimpleActiveDirectory.this.getAttribute(attributes, "description"));
/* 555 */         user.setStartTime(new Date());
/* 556 */         String accountExpires = (String)SimpleActiveDirectory.this.getAttribute(attributes, "accountExpires");
/* 557 */         if (accountExpires != null) {
/* 558 */           Long value = new Long(accountExpires);
/*     */           
/* 560 */           if ((value.longValue() > 4112735757541L) || (value.longValue() == 0L)) {
/* 561 */             value = Long.valueOf(4112735757541L);
/* 562 */             user.setEndTime(new Date(value.longValue()));
/*     */           } else {
/* 564 */             user.setEndTime(new Date(SimpleActiveDirectory.this.filetimeToMillis(value.longValue())));
/*     */           }
/*     */         }
/*     */         
/* 568 */         List<String> memberOf = new ArrayList();
/* 569 */         List<String> tmpRoles = SimpleActiveDirectory.this.getAllGroupNames();
/* 570 */         String tmp; if (attributes.get("memberOf") != null) {
/* 571 */           NamingEnumeration<?> vals = attributes.get("memberOf").getAll();
/* 572 */           while (vals.hasMoreElements())
/*     */           {
/* 574 */             tmp = (String)vals.nextElement();
/* 575 */             for (int i = 0; i < tmpRoles.size(); i++) {
/* 576 */               if (tmp.indexOf((String)tmpRoles.get(i)) != -1) {
/* 577 */                 memberOf.add(tmp);
/* 578 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 583 */         if (!memberOf.isEmpty()) {
/* 584 */           Set<String> roleNames = new HashSet();
/* 585 */           for (String member : memberOf) {
/* 586 */             String temp = "," + SimpleActiveDirectory.this.searchBase + "," + SimpleActiveDirectory.this.ldapContextSource.getBaseLdapPathAsString();
/* 587 */             Role role = new Role();
/* 588 */             role.setName(member.substring(3, member.length() - temp.length()));
/* 589 */             roleNames.add(role.getName());
/*     */           }
/* 591 */           user.setRoleNames(roleNames);
/*     */         }
/* 593 */         Object unicodePwd = SimpleActiveDirectory.this.getAttribute(attributes, "userpassword");
/* 594 */         if (unicodePwd != null) {
/* 595 */           user.setPwd1(new String((byte[])unicodePwd, "UTF-16LE"));
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 599 */         SimpleActiveDirectory.logger.error(e.getMessage(), e);
/*     */       }
/* 601 */       return user;
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoleAttributesMapper implements AttributesMapper<Role> {
/*     */     private RoleAttributesMapper() {}
/*     */     
/*     */     public Role mapFromAttributes(Attributes attributes) throws NamingException {
/* 609 */       if (attributes == null) {
/* 610 */         return null;
/*     */       }
/* 612 */       Role role = new Role();
/*     */       try {
/* 614 */         String roleName = (String)SimpleActiveDirectory.this.getAttribute(attributes, "cn");
/* 615 */         role.setName(roleName);
/*     */       } catch (Exception e) {
/* 617 */         SimpleActiveDirectory.logger.error(e.getMessage(), e);
/*     */       }
/* 619 */       return role;
/*     */     }
/*     */   }
/*     */   
/*     */   private class SingleNameAttributesMapper implements AttributesMapper<String> {
/*     */     private SingleNameAttributesMapper() {}
/*     */     
/*     */     public String mapFromAttributes(Attributes attrs) throws NamingException {
/* 627 */       Attribute cn = attrs.get("cn");
/* 628 */       return cn.get().toString();
/*     */     }
/*     */   }
/*     */   
/*     */   private class MultipleAttributesMapper implements AttributesMapper<String> {
/*     */     private MultipleAttributesMapper() {}
/*     */     
/*     */     public String mapFromAttributes(Attributes attrs) throws NamingException {
/* 636 */       NamingEnumeration<? extends Attribute> all = attrs.getAll();
/* 637 */       StringBuffer result = new StringBuffer();
/* 638 */       result.append("\n Result { \n");
/* 639 */       while (all.hasMore()) {
/* 640 */         Attribute id = (Attribute)all.next();
/* 641 */         result.append(" \t |_  #" + id.getID() + "= [ " + id.get() + " ]  \n");
/*     */       }
/* 643 */       result.append("\n } ");
/* 644 */       return result.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   private <T> T getAttribute(Attributes attributes, String attrName)
/*     */   {
/* 650 */     Attribute attr = attributes.get(attrName);
/* 651 */     if (attr != null) {
/*     */       try {
/* 653 */         return (T)attr.get();
/*     */       } catch (NamingException e) {
/* 655 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 658 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\ldap\SimpleActiveDirectory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */