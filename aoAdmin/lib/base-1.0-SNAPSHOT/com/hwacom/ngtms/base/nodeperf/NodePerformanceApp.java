/*     */ package com.hwacom.ngtms.base.nodeperf;
/*     */ 
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Date;
/*     */ import java.util.Optional;
/*     */ import org.apache.commons.cli.CommandLine;
/*     */ import org.apache.commons.cli.DefaultParser;
/*     */ import org.apache.commons.cli.HelpFormatter;
/*     */ import org.apache.commons.cli.Options;
/*     */ import org.apache.commons.cli.ParseException;
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
/*     */ public class NodePerformanceApp
/*     */ {
/*     */   private String outputFile;
/*     */   private boolean serializeToStdOut;
/*     */   private boolean printErrMsg;
/*     */   private Long pid;
/*     */   
/*     */   private NodePerformance getNodePerformance() {
/*     */     try {
/*  33 */       return (new NodePerformanceCollector()).getNodePerformance("none,udev,tmpfs,sysfs,cgmfs", Optional.ofNullable(this.pid), this::log);
/*  34 */     } catch (Exception ex) {
/*  35 */       log("Failed to create Sigar.", ex);
/*  36 */       NodePerformance nodePerformance = new NodePerformance();
/*  37 */       nodePerformance.setTimestamp(new Date());
/*  38 */       return nodePerformance;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void log(String message, Exception ex) {
/*  43 */     if (this.printErrMsg) {
/*  44 */       System.err.println(message);
/*  45 */       ex.printStackTrace(System.err);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean parsingOptions(String[] args) throws ParseException {
/*  50 */     Options options = new Options();
/*  51 */     options.addOption("f", true, "Serialize NodePerformance to the file");
/*  52 */     options.addOption("pid", true, "process id");
/*  53 */     options.addOption("s", false, "Serialize NodePerformance to StdOut");
/*  54 */     options.addOption("e", false, "Print error messages");
/*  55 */     options.addOption("h", false, "Print this help message");
/*     */     
/*  57 */     DefaultParser defaultParser = new DefaultParser();
/*  58 */     CommandLine cmd = defaultParser.parse(options, args);
/*     */     
/*  60 */     if (cmd.hasOption("h")) {
/*  61 */       HelpFormatter formatter = new HelpFormatter();
/*  62 */       formatter.printHelp(NodePerformanceApp.class.getSimpleName(), options);
/*  63 */       return false;
/*     */     } 
/*  65 */     if (cmd.hasOption("e")) {
/*  66 */       this.printErrMsg = true;
/*     */     }
/*  68 */     if (cmd.hasOption("s")) {
/*  69 */       this.serializeToStdOut = true;
/*     */     }
/*  71 */     if (cmd.hasOption("f")) {
/*  72 */       this.outputFile = cmd.getOptionValue("f");
/*     */     }
/*  74 */     if (cmd.hasOption("pid")) {
/*  75 */       this.pid = Long.valueOf(Long.parseLong(cmd.getOptionValue("pid")));
/*     */     }
/*  77 */     return true;
/*     */   }
/*     */   
/*     */   public void run(String[] args) throws Exception {
/*  81 */     if (parsingOptions(args)) {
/*  82 */       NodePerformance nodePerformance = getNodePerformance();
/*     */       
/*  84 */       if (this.outputFile != null) {
/*  85 */         ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.outputFile));
/*  86 */         oos.writeObject(nodePerformance);
/*  87 */         oos.close();
/*  88 */       } else if (this.serializeToStdOut) {
/*  89 */         ObjectOutputStream oos = new ObjectOutputStream(System.out);
/*  90 */         oos.writeObject(nodePerformance);
/*  91 */         oos.close();
/*     */       } else {
/*  93 */         System.out.println(nodePerformance);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*  99 */     NodePerformanceApp nodePerfCollector = new NodePerformanceApp();
/* 100 */     nodePerfCollector.run(args);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\nodeperf\NodePerformanceApp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */