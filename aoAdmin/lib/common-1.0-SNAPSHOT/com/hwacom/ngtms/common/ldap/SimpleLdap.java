/*     */ package com.hwacom.ngtms.common.ldap;
/*     */ 
/*     */ import com.hwacom.ngtms.common.fm.model.Role;
/*     */ import com.hwacom.ngtms.common.fm.model.User;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.Attributes;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.ldap.core.AttributesMapper;
/*     */ import org.springframework.ldap.core.DirContextAdapter;
/*     */ import org.springframework.ldap.core.LdapTemplate;
/*     */ import org.springframework.ldap.filter.AndFilter;
/*     */ import org.springframework.ldap.filter.EqualsFilter;
/*     */ import org.springframework.ldap.filter.Filter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleLdap
/*     */ {
/*  32 */   private static final Logger logger = LoggerFactory.getLogger(SimpleLdap.class);
/*     */   
/*     */   private static final String ACCOUNT_QUERY_INFO = "ou=people";
/*     */   
/*     */   private static final String ACCOUNT_PROP_ENCRYPTEDPASSWORD = "userPassword";
/*     */   
/*     */   private static final String ACCOUNT_PROP_FIRSTNAME = "givenName";
/*     */   
/*     */   private static final String ACCOUNT_PROP_LASTNAME = "sn";
/*     */   
/*     */   private static final String ACCOUNT_PROP_MOBILEPHONE = "mobilePhone";
/*     */   
/*     */   private static final String ACCOUNT_PROP_EMAIL = "mail";
/*     */   
/*     */   private static final String ROLE_QUERY_INFO = "ou=group";
/*     */   
/*     */   private static final String ROLE_PROP_CN = "cn";
/*     */   
/*     */   private static final String ROLE_PROP_MEMBERUID = "memberUid";
/*     */   private static final String PROP_DESCRIPTION = "description";
/*     */   @Value("${security.ldap.search.base:cn=users}")
/*     */   private String ldapSearchBase;
/*     */   @Autowired(required = true)
/*     */   @Qualifier("ldapTemplate")
/*     */   private LdapTemplate ldapTemplate;
/*     */   
/*     */   public User getAccount(final String account) {
/*  59 */     AttributesMapper<User> mapper = new AttributesMapper<User>()
/*     */       {
/*     */         public User mapFromAttributes(Attributes attributes) throws NamingException
/*     */         {
/*  63 */           SimpleLdap.logger.debug("attributes :", attributes);
/*  64 */           String firstName = (String)SimpleLdap.this.getAttribute(attributes, "givenName");
/*  65 */           String lastName = (String)SimpleLdap.this.getAttribute(attributes, "sn");
/*  66 */           String mobilePhone = (String)SimpleLdap.this.getAttribute(attributes, "mobilePhone");
/*  67 */           String email = (String)SimpleLdap.this.getAttribute(attributes, "mail");
/*  68 */           String desc = (String)SimpleLdap.this.getAttribute(attributes, "description");
/*  69 */           byte[] password = (byte[])SimpleLdap.this.getAttribute(attributes, "userPassword");
/*  70 */           Set<Role> roles = SimpleLdap.this.getRoles(account);
/*  71 */           User user = new User();
/*  72 */           user.setLogin(account);
/*  73 */           user.setPwd1(new String(password));
/*  74 */           user.setName(firstName + lastName);
/*  75 */           user.setMail(email);
/*  76 */           user.setMobile(mobilePhone);
/*  77 */           user.setRoleNames((Set)roles
/*  78 */               .stream().map(role -> role.getName()).collect(Collectors.toSet()));
/*  79 */           return user;
/*     */         }
/*     */       };
/*     */     
/*  83 */     User accountInfo = (User)this.ldapTemplate.lookup("uid=" + account + "," + "ou=people", mapper);
/*  84 */     return accountInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> T getAttribute(Attributes attributes, String attrName) {
/*  89 */     Attribute attr = attributes.get(attrName);
/*  90 */     if (attr != null) {
/*     */       try {
/*  92 */         return (T)attr.get();
/*  93 */       } catch (NamingException e) {
/*  94 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Role> getRoles(final String account) {
/* 107 */     HashSet<Role> result = new HashSet<>();
/* 108 */     AttributesMapper<Role> mapper = new AttributesMapper<Role>()
/*     */       {
/*     */         public Role mapFromAttributes(Attributes attributes) throws NamingException
/*     */         {
/* 112 */           Attribute members = attributes.get("memberUid");
/* 113 */           if (members == null) {
/* 114 */             return null;
/*     */           }
/* 116 */           if (!members.contains(account)) {
/* 117 */             return null;
/*     */           }
/* 119 */           String name = (String)attributes.get("cn").get();
/* 120 */           String desc = (String)SimpleLdap.this.getAttribute(attributes, "description");
/* 121 */           Role role = new Role();
/* 122 */           role.setName(name);
/* 123 */           role.setDescription(desc);
/* 124 */           return role;
/*     */         }
/*     */       };
/*     */     
/* 128 */     List<String> roles = this.ldapTemplate.list("ou=group");
/* 129 */     for (String r : roles) {
/*     */       
/* 131 */       Role role = (Role)this.ldapTemplate.lookup(r + "," + "ou=group", new String[] { "cn", "memberUid", "description" }, mapper);
/*     */ 
/*     */ 
/*     */       
/* 135 */       if (role == null) {
/*     */         continue;
/*     */       }
/* 138 */       result.add(role);
/*     */     } 
/* 140 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Role> getAllRoles() {
/* 149 */     HashSet<Role> result = new HashSet<>();
/* 150 */     List<String> roles = this.ldapTemplate.list("ou=group");
/* 151 */     for (String r : roles) {
/*     */       
/* 153 */       DirContextAdapter adapter = (DirContextAdapter)this.ldapTemplate.lookup(r + "," + "ou=group");
/* 154 */       String name = null;
/* 155 */       String description = null;
/*     */       try {
/* 157 */         name = (String)adapter.getObjectAttribute("cn");
/* 158 */         description = (String)adapter.getObjectAttribute("description");
/* 159 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/* 162 */       Role role = new Role();
/* 163 */       role.setName(name);
/* 164 */       role.setDescription(description);
/* 165 */       result.add(role);
/*     */     } 
/* 167 */     return result;
/*     */   }
/*     */   
/*     */   public User getUserDetails(String userName) {
/* 171 */     AndFilter filter = new AndFilter();
/* 172 */     filter
/* 173 */       .and((Filter)new EqualsFilter("objectclass", "person"))
/* 174 */       .and((Filter)new EqualsFilter("employeeID", userName));
/* 175 */     List<User> users = this.ldapTemplate.search("", filter.encode(), new UaserAttributesMapper());
/* 176 */     if (!users.isEmpty()) {
/* 177 */       return users.get(0);
/*     */     }
/* 179 */     return null;
/*     */   }
/*     */   
/*     */   private class UaserAttributesMapper implements AttributesMapper<User> {
/*     */     private UaserAttributesMapper() {}
/*     */     
/*     */     public User mapFromAttributes(Attributes attributes) throws NamingException {
/* 186 */       if (attributes == null) {
/* 187 */         return null;
/*     */       }
/* 189 */       User user = new User();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 266 */       return user;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\ldap\SimpleLdap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */