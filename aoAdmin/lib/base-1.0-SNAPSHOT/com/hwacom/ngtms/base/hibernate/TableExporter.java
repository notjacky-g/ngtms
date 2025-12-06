/*     */ package com.hwacom.ngtms.base.hibernate;
/*     */ import com.google.gwt.dev.util.collect.HashMap;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Field;
/*     */ import java.time.LocalDate;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.hibernate.boot.Metadata;
/*     */ import org.hibernate.boot.model.naming.Identifier;
/*     */ import org.hibernate.boot.model.relational.Exportable;
/*     */ import org.hibernate.boot.model.relational.QualifiedName;
/*     */ import org.hibernate.boot.model.relational.QualifiedNameParser;
/*     */ import org.hibernate.dialect.Dialect;
/*     */ import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
/*     */ import org.hibernate.engine.spi.Mapping;
/*     */ import org.hibernate.mapping.Column;
/*     */ import org.hibernate.mapping.Constraint;
/*     */ import org.hibernate.mapping.PersistentClass;
/*     */ import org.hibernate.mapping.Table;
/*     */ import org.hibernate.mapping.UniqueKey;
/*     */ import org.hibernate.tool.schema.internal.StandardTableExporter;
/*     */ 
/*     */ public class TableExporter extends StandardTableExporter {
/*  27 */   private Map<Table, Column> partitionColumnMap = null;
/*     */   
/*     */   public TableExporter(Dialect dialect) {
/*  30 */     super(dialect);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getSqlCreateStrings(Table table, Metadata metadata) {
/*  35 */     if (this.partitionColumnMap == null) initPartitionColumnNameMap(metadata); 
/*  36 */     if (table.hasPrimaryKey() && this.partitionColumnMap.containsKey(table)) {
/*  37 */       table.getPrimaryKey().addColumn(this.partitionColumnMap.get(table));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  43 */     QualifiedNameParser.NameParts nameParts = new QualifiedNameParser.NameParts(Identifier.toIdentifier(table.getCatalog(), table.isCatalogQuoted()), Identifier.toIdentifier(table.getSchema(), table.isSchemaQuoted()), table.getNameIdentifier());
/*     */     
/*  45 */     JdbcEnvironment jdbcEnvironment = metadata.getDatabase().getJdbcEnvironment();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     StringBuilder buf = (new StringBuilder(tableCreateString(table.hasPrimaryKey()))).append(' ').append(jdbcEnvironment.getQualifiedObjectNameFormatter().format((QualifiedName)nameParts, jdbcEnvironment.getDialect())).append(" (");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     boolean isPrimaryKeyIdentity = (table.hasPrimaryKey() && table.getIdentifierValue() != null && table.getIdentifierValue().isIdentityColumn(metadata.getIdentifierGeneratorFactory(), this.dialect));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     String pkColName = null;
/*  68 */     if (table.hasPrimaryKey()) {
/*  69 */       Column pkColumn = table.getPrimaryKey().getColumns().iterator().next();
/*  70 */       pkColName = pkColumn.getQuotedName(this.dialect);
/*     */     } 
/*     */     
/*  73 */     Iterator<Column> columnItr = table.getColumnIterator();
/*  74 */     boolean isFirst = true;
/*  75 */     while (columnItr.hasNext()) {
/*  76 */       Column col = columnItr.next();
/*  77 */       if (isFirst) {
/*  78 */         isFirst = false;
/*     */       } else {
/*  80 */         buf.append(", ");
/*     */       } 
/*  82 */       String colName = col.getQuotedName(this.dialect);
/*     */       
/*  84 */       buf.append(colName).append(' ');
/*     */       
/*  86 */       if (isPrimaryKeyIdentity && colName.equals(pkColName)) {
/*     */         
/*  88 */         if (this.dialect.getIdentityColumnSupport().hasDataTypeInIdentityColumn()) {
/*  89 */           buf.append(col.getSqlType(this.dialect, (Mapping)metadata));
/*     */         }
/*  91 */         buf.append(' ')
/*  92 */           .append(this.dialect
/*     */             
/*  94 */             .getIdentityColumnSupport()
/*  95 */             .getIdentityColumnString(col.getSqlTypeCode((Mapping)metadata)));
/*     */       } else {
/*  97 */         buf.append(col.getSqlType(this.dialect, (Mapping)metadata));
/*     */         
/*  99 */         String defaultValue = col.getDefaultValue();
/* 100 */         if (defaultValue != null) {
/* 101 */           buf.append(" default ").append(defaultValue);
/*     */         }
/*     */         
/* 104 */         if (col.isNullable()) {
/* 105 */           buf.append(this.dialect.getNullColumnString());
/*     */         } else {
/* 107 */           buf.append(" not null");
/*     */         } 
/*     */       } 
/*     */       
/* 111 */       if (col.isUnique()) {
/* 112 */         String keyName = Constraint.generateName("UK_", table, new Column[] { col });
/* 113 */         UniqueKey uk = table.getOrCreateUniqueKey(keyName);
/* 114 */         uk.addColumn(col);
/* 115 */         buf.append(this.dialect.getUniqueDelegate().getColumnDefinitionUniquenessFragment(col));
/*     */       } 
/*     */       
/* 118 */       if (col.getCheckConstraint() != null && this.dialect.supportsColumnCheck()) {
/* 119 */         buf.append(" check (").append(col.getCheckConstraint()).append(")");
/*     */       }
/*     */       
/* 122 */       String columnComment = col.getComment();
/* 123 */       if (columnComment != null) {
/* 124 */         buf.append(this.dialect.getColumnComment(columnComment));
/*     */       }
/*     */     } 
/* 127 */     if (table.hasPrimaryKey()) {
/* 128 */       buf.append(", ").append(table.getPrimaryKey().sqlConstraintString(this.dialect));
/*     */     }
/*     */     
/* 131 */     buf.append(this.dialect.getUniqueDelegate().getTableCreationUniqueConstraintsFragment(table));
/*     */     
/* 133 */     applyTableCheck(table, buf);
/*     */     
/* 135 */     buf.append(')');
/*     */     
/* 137 */     if (table.getComment() != null) {
/* 138 */       buf.append(this.dialect.getTableComment(table.getComment()));
/*     */     }
/*     */     
/* 141 */     applyTableTypeString(buf);
/*     */     
/* 143 */     applyTablePartition(table, buf);
/*     */     
/* 145 */     List<String> sqlStrings = new ArrayList<>();
/* 146 */     sqlStrings.add(buf.toString());
/*     */     
/* 148 */     applyComments(table, (QualifiedName)nameParts, sqlStrings);
/*     */     
/* 150 */     applyInitCommands(table, sqlStrings);
/*     */     
/* 152 */     return sqlStrings.<String>toArray(new String[sqlStrings.size()]);
/*     */   }
/*     */   
/*     */   private void applyTablePartition(Table table, StringBuilder sb) {
/* 156 */     Column partitionColumn = this.partitionColumnMap.get(table);
/* 157 */     if (partitionColumn == null)
/* 158 */       return;  sb.append(" partition by range columns(").append(partitionColumn.getName()).append(") (");
/* 159 */     LocalDate date = LocalDate.now();
/* 160 */     DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMdd");
/* 161 */     DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
/* 162 */     for (int i = 0; i < 8; i++) {
/* 163 */       if (i != 0) sb.append(","); 
/* 164 */       sb.append("PARTITION p").append(date.format(formatter1)).append(" VALUES LESS THAN ('");
/* 165 */       sb.append(date.plusDays(1L).format(formatter2)).append("')");
/* 166 */       date = date.plusDays(1L);
/*     */     } 
/* 168 */     sb.append(")");
/*     */   }
/*     */   
/*     */   private void initPartitionColumnNameMap(Metadata metadata) {
/* 172 */     this.partitionColumnMap = (Map<Table, Column>)new HashMap();
/* 173 */     for (PersistentClass persClass : metadata.getEntityBindings()) {
/* 174 */       Table table = persClass.getTable();
/* 175 */       if (!table.isPhysicalTable())
/* 176 */         continue;  Class<?> clazz = persClass.getMappedClass();
/* 177 */       if (clazz == null)
/* 178 */         continue;  Iterator<?> columnItr = table.getColumnIterator();
/* 179 */       while (columnItr.hasNext()) {
/* 180 */         Field field; Column column = (Column)columnItr.next();
/* 181 */         String columnName = column.getName();
/* 182 */         String fieldName = getFieldName(columnName);
/*     */         
/*     */         try {
/* 185 */           field = clazz.getDeclaredField(fieldName);
/* 186 */         } catch (NoSuchFieldException|SecurityException e) {
/*     */           continue;
/*     */         } 
/*     */         
/* 190 */         for (Annotation ann : field.getDeclaredAnnotations()) {
/* 191 */           if (ann.annotationType().getName().equals(TablePartitionColumn.class.getName())) {
/* 192 */             this.partitionColumnMap.put(table, column);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getFieldName(String name) {
/* 201 */     StringBuilder sb = new StringBuilder();
/* 202 */     boolean isUpper = false;
/* 203 */     for (int i = 0; i < name.length(); i++) {
/* 204 */       if (name.charAt(i) == '_')
/* 205 */       { isUpper = true;
/*     */          }
/*     */       
/* 208 */       else if (isUpper)
/* 209 */       { sb.append(Character.toUpperCase(name.charAt(i)));
/* 210 */         isUpper = false; }
/* 211 */       else { sb.append(name.charAt(i)); }
/*     */     
/* 213 */     }  return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hibernate\TableExporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */