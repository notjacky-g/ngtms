/*     */ package com.hwacom.ngtms.base.ssh;
/*     */ 
/*     */ import ch.ethz.ssh2.Connection;
/*     */ import ch.ethz.ssh2.Session;
/*     */ import ch.ethz.ssh2.StreamGobbler;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ public class SshClient
/*     */ {
/*  34 */   private static Logger logger = LoggerFactory.getLogger(SshClient.class);
/*     */   private Connection connection;
/*     */   
/*     */   public void connect(String host, String userName, String password) throws IOException {
/*  38 */     this.connection = new Connection(host);
/*  39 */     this.connection.connect();
/*  40 */     boolean isAuthenticated = this.connection.authenticateWithPassword(userName, password);
/*  41 */     if (!isAuthenticated) throw new IOException("Authentication failed.");
/*     */   
/*     */   }
/*     */   
/*     */   public void connect(String host, String userName, File privateKey, String privateKeyPassword) throws IOException {
/*  46 */     this.connection = new Connection(host);
/*  47 */     this.connection.connect();
/*  48 */     boolean isAuthenticated = false;
/*     */     
/*     */     try {
/*  51 */       isAuthenticated = this.connection.authenticateWithPublicKey(userName, privateKey, privateKeyPassword);
/*  52 */     } catch (IOException e) {
/*  53 */       this.connection.close();
/*  54 */       throw new IOException(e.getMessage(), e);
/*     */     } 
/*  56 */     if (!isAuthenticated) throw new IOException("Authentication failed."); 
/*     */   }
/*     */   public CmdExecBinaryResult execCmdBinary(String command) {
/*     */     StreamGobbler streamGobbler;
/*  60 */     Session session = null;
/*  61 */     InputStream stdout = null;
/*  62 */     BufferedReader stderrReader = null;
/*  63 */     CmdExecBinaryResult cmdExecResult = new CmdExecBinaryResult();
/*     */     try {
/*  65 */       if (this.connection == null) throw new IOException("No connection"); 
/*  66 */       session = this.connection.openSession();
/*  67 */       logger.debug("** exec: " + command);
/*  68 */       long startTime = System.currentTimeMillis();
/*  69 */       session.execCommand(command);
/*     */       
/*  71 */       streamGobbler = new StreamGobbler(session.getStdout());
/*  72 */       StreamGobbler streamGobbler1 = new StreamGobbler(session.getStderr());
/*     */       
/*  74 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  75 */       stderrReader = new BufferedReader(new InputStreamReader((InputStream)streamGobbler1, "UTF-8"));
/*     */       
/*  77 */       List<String> stdErrResult = new ArrayList<>();
/*  78 */       byte[] buffer = new byte[1024];
/*     */       while (true) {
/*  80 */         int len = streamGobbler.read(buffer);
/*  81 */         if (len == -1)
/*  82 */           break;  baos.write(buffer, 0, len);
/*     */       } 
/*     */       
/*     */       while (true) {
/*  86 */         String line = stderrReader.readLine();
/*  87 */         if (line == null) {
/*     */           break;
/*     */         }
/*  90 */         stdErrResult.add(line);
/*     */       } 
/*  92 */       cmdExecResult.setExecTime(System.currentTimeMillis() - startTime);
/*  93 */       cmdExecResult.setStdOut(baos.toByteArray());
/*  94 */       String[] lines = new String[stdErrResult.size()];
/*  95 */       stdErrResult.toArray(lines);
/*  96 */       cmdExecResult.setStdErr(lines);
/*  97 */       cmdExecResult.setExitStatus(session.getExitStatus());
/*  98 */     } catch (Exception ex) {
/*  99 */       logger.error("Failed to run command: {}", command, ex);
/* 100 */       cmdExecResult.setException(ex.toString());
/*     */     } finally {
/* 102 */       if (streamGobbler != null) {
/*     */         try {
/* 104 */           streamGobbler.close();
/* 105 */         } catch (Exception exception) {}
/*     */       }
/* 107 */       if (stderrReader != null)
/*     */         try {
/* 109 */           stderrReader.close();
/* 110 */         } catch (Exception ex) {
/* 111 */           logger.error("Failed to close stderrReader", ex);
/*     */         }  
/* 113 */       if (session != null) session.close(); 
/*     */     } 
/* 115 */     return cmdExecResult;
/*     */   }
/*     */   
/*     */   public CmdExecResult execCmd(String command) {
/* 119 */     Session session = null;
/* 120 */     BufferedReader stdoutReader = null;
/* 121 */     BufferedReader stderrReader = null;
/* 122 */     CmdExecResult cmdExecResult = new CmdExecResult();
/*     */     try {
/* 124 */       if (this.connection == null) throw new IOException("No connection"); 
/* 125 */       session = this.connection.openSession();
/* 126 */       logger.debug("** exec: " + command);
/* 127 */       long startTime = System.currentTimeMillis();
/* 128 */       session.execCommand(command);
/*     */       
/* 130 */       StreamGobbler streamGobbler1 = new StreamGobbler(session.getStdout());
/* 131 */       StreamGobbler streamGobbler2 = new StreamGobbler(session.getStderr());
/*     */       
/* 133 */       stdoutReader = new BufferedReader(new InputStreamReader((InputStream)streamGobbler1, "UTF-8"));
/* 134 */       stderrReader = new BufferedReader(new InputStreamReader((InputStream)streamGobbler2, "UTF-8"));
/*     */       
/* 136 */       List<String> stdOutResult = new ArrayList<>();
/* 137 */       List<String> stdErrResult = new ArrayList<>();
/*     */       while (true) {
/* 139 */         String line = stdoutReader.readLine();
/* 140 */         if (line == null) {
/*     */           break;
/*     */         }
/* 143 */         stdOutResult.add(line);
/*     */       } 
/*     */       
/*     */       while (true) {
/* 147 */         String line = stderrReader.readLine();
/* 148 */         if (line == null) {
/*     */           break;
/*     */         }
/* 151 */         stdErrResult.add(line);
/*     */       } 
/* 153 */       cmdExecResult.setExecTime(System.currentTimeMillis() - startTime);
/* 154 */       String[] lines = new String[stdOutResult.size()];
/* 155 */       stdOutResult.toArray(lines);
/* 156 */       cmdExecResult.setStdOut(lines);
/* 157 */       lines = new String[stdErrResult.size()];
/* 158 */       stdErrResult.toArray(lines);
/* 159 */       cmdExecResult.setStdErr(lines);
/* 160 */       cmdExecResult.setExitStatus(session.getExitStatus());
/* 161 */     } catch (Exception ex) {
/* 162 */       logger.error("Failed to run command: {}", command, ex);
/* 163 */       cmdExecResult.setException(ex.toString());
/*     */     } finally {
/* 165 */       if (stdoutReader != null)
/*     */         try {
/* 167 */           stdoutReader.close();
/* 168 */         } catch (Exception ex) {
/* 169 */           logger.error("Failed to close stdoutReader", ex);
/*     */         }  
/* 171 */       if (stderrReader != null)
/*     */         try {
/* 173 */           stderrReader.close();
/* 174 */         } catch (Exception ex) {
/* 175 */           logger.error("Failed to close stderrReader", ex);
/*     */         }  
/* 177 */       if (session != null) session.close(); 
/*     */     } 
/* 179 */     return cmdExecResult;
/*     */   }
/*     */   
/*     */   public void execCmd(String command, final CmdRealTimeResponse cmdRealTimeResponse) {
/* 183 */     Session session = null;
/* 184 */     BufferedReader stdoutReader = null;
/* 185 */     BufferedReader stderrReader = null;
/*     */     try {
/* 187 */       if (this.connection == null) throw new IOException("No connection"); 
/* 188 */       session = this.connection.openSession();
/* 189 */       logger.debug("** exec: " + command);
/* 190 */       session.execCommand(command);
/*     */       
/* 192 */       StreamGobbler streamGobbler1 = new StreamGobbler(session.getStdout());
/* 193 */       StreamGobbler streamGobbler2 = new StreamGobbler(session.getStderr());
/*     */       
/* 195 */       stdoutReader = new BufferedReader(new InputStreamReader((InputStream)streamGobbler1, "UTF-8"));
/* 196 */       stderrReader = new BufferedReader(new InputStreamReader((InputStream)streamGobbler2, "UTF-8"));
/* 197 */       final CountDownLatch countDownLatch = new CountDownLatch(2);
/* 198 */       final BufferedReader stdoutReader1 = stdoutReader;
/* 199 */       final BufferedReader stderrReader1 = stderrReader;
/* 200 */       Thread stdOutReadThread = new Thread(new Runnable()
/*     */           {
/*     */             
/*     */             public void run()
/*     */             {
/*     */               try {
/*     */                 while (true) {
/* 207 */                   String line = stdoutReader1.readLine();
/* 208 */                   if (line == null) {
/*     */                     break;
/*     */                   }
/* 211 */                   cmdRealTimeResponse.stdOut(line);
/*     */                 } 
/* 213 */               } catch (Exception ex) {
/* 214 */                 SshClient.logger.error("", ex);
/*     */               } finally {
/* 216 */                 countDownLatch.countDown();
/*     */               } 
/*     */             }
/*     */           });
/* 220 */       stdOutReadThread.setDaemon(true);
/* 221 */       stdOutReadThread.start();
/* 222 */       Thread stdErrReadThread = new Thread(new Runnable()
/*     */           {
/*     */             
/*     */             public void run()
/*     */             {
/*     */               try {
/*     */                 while (true) {
/* 229 */                   String line = stderrReader1.readLine();
/* 230 */                   if (line == null) {
/*     */                     break;
/*     */                   }
/* 233 */                   cmdRealTimeResponse.stdErr(line);
/*     */                 } 
/* 235 */               } catch (Exception ex) {
/* 236 */                 SshClient.logger.error("", ex);
/*     */               } finally {
/* 238 */                 countDownLatch.countDown();
/*     */               } 
/*     */             }
/*     */           });
/* 242 */       stdErrReadThread.setDaemon(true);
/* 243 */       stdErrReadThread.start();
/*     */       
/* 245 */       countDownLatch.await();
/* 246 */       cmdRealTimeResponse.onExitStatus(session.getExitStatus());
/* 247 */     } catch (Exception ex) {
/* 248 */       logger.error("Failed to run command: {}", command, ex);
/* 249 */       cmdRealTimeResponse.onException(ex.toString());
/*     */     } finally {
/* 251 */       if (stdoutReader != null)
/*     */         try {
/* 253 */           stdoutReader.close();
/* 254 */         } catch (Exception ex) {
/* 255 */           logger.error("Failed to close stdoutReader", ex);
/*     */         }  
/* 257 */       if (stderrReader != null)
/*     */         try {
/* 259 */           stderrReader.close();
/* 260 */         } catch (Exception ex) {
/* 261 */           logger.error("Failed to close stderrReader", ex);
/*     */         }  
/* 263 */       if (session != null) session.close(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() {
/* 268 */     if (this.connection != null) {
/* 269 */       this.connection.close();
/* 270 */       this.connection = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\ssh\SshClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */