/*    */ package com.hwacom.ngtms.common.restful;
/*    */ 
/*    */ import com.hwacom.ngtms.common.shared.ErrorEnum;
/*    */ import com.hwacom.ngtms.common.shared.IncorrectPwdException;
/*    */ import com.hwacom.ngtms.common.shared.InvalidTokenException;
/*    */ import com.hwacom.ngtms.common.shared.RestfulErrorResponse;
/*    */ import com.hwacom.ngtms.common.shared.RestfulException;
/*    */ import com.hwacom.ngtms.common.shared.TokenExpiredException;
/*    */ import com.hwacom.ngtms.common.shared.TokenNotExpiredException;
/*    */ import com.hwacom.ngtms.common.shared.UserEmailUnmatchException;
/*    */ import com.hwacom.ngtms.common.shared.UserNotFoundException;
/*    */ import com.hwacom.ngtms.common.shared.VerificationFailedException;
/*    */ import org.springframework.core.annotation.Order;
/*    */ import org.springframework.http.HttpStatus;
/*    */ import org.springframework.http.ResponseEntity;
/*    */ import org.springframework.web.bind.annotation.ExceptionHandler;
/*    */ import org.springframework.web.bind.annotation.RestControllerAdvice;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @RestControllerAdvice
/*    */ @Order(50)
/*    */ public class RestfulExceptionHandler
/*    */ {
/*    */   @ExceptionHandler({UserNotFoundException.class})
/*    */   public ResponseEntity<RestfulErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
/* 32 */     RestfulErrorResponse response = new RestfulErrorResponse(ErrorEnum.USER_NOT_FOUND.getCode(), "User not found.");
/* 33 */     return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @ExceptionHandler({UserEmailUnmatchException.class})
/*    */   public ResponseEntity<RestfulErrorResponse> handleUserEmailUnmatchException(UserEmailUnmatchException e) {
/* 40 */     RestfulErrorResponse response = new RestfulErrorResponse(ErrorEnum.USER_EMAIL_UNMATCH.getCode(), "User email unmatch.");
/* 41 */     return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
/*    */   }
/*    */ 
/*    */   
/*    */   @ExceptionHandler({RestfulException.class})
/*    */   public ResponseEntity<RestfulErrorResponse> handleRestfulException(RestfulException e) {
/* 47 */     RestfulErrorResponse response = new RestfulErrorResponse(ErrorEnum.RESTFUL_EXCEPTION.getCode(), "Server side error.");
/* 48 */     return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
/*    */   }
/*    */ 
/*    */   
/*    */   @ExceptionHandler({InvalidTokenException.class})
/*    */   public ResponseEntity<RestfulErrorResponse> handleInvalidTokenException(InvalidTokenException e) {
/* 54 */     RestfulErrorResponse response = new RestfulErrorResponse(ErrorEnum.INVALID_TOKEN.getCode(), "Invalid token.");
/* 55 */     return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
/*    */   }
/*    */ 
/*    */   
/*    */   @ExceptionHandler({TokenExpiredException.class})
/*    */   public ResponseEntity<RestfulErrorResponse> handleTokenExpiredException(TokenExpiredException e) {
/* 61 */     RestfulErrorResponse response = new RestfulErrorResponse(ErrorEnum.TOKEN_EXPIRED.getCode(), "Token was expired.");
/* 62 */     return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @ExceptionHandler({TokenNotExpiredException.class})
/*    */   public ResponseEntity<RestfulErrorResponse> handleTokenNotExpiredException(TokenNotExpiredException e) {
/* 69 */     RestfulErrorResponse response = new RestfulErrorResponse(ErrorEnum.TOKEN_NOT_EXPIRED.getCode(), "Token not expired.");
/* 70 */     return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
/*    */   }
/*    */ 
/*    */   
/*    */   @ExceptionHandler({IncorrectPwdException.class})
/*    */   public ResponseEntity<RestfulErrorResponse> handleIncorrectPwdException(IncorrectPwdException e) {
/* 76 */     RestfulErrorResponse response = new RestfulErrorResponse(ErrorEnum.INCORRECT_PWD.getCode(), "Incorrect pwd.");
/* 77 */     return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @ExceptionHandler({VerificationFailedException.class})
/*    */   public ResponseEntity<RestfulErrorResponse> handleVerificationFailedException(VerificationFailedException e) {
/* 84 */     RestfulErrorResponse response = new RestfulErrorResponse(ErrorEnum.VERIFICATION_FAILED.getCode(), "Verification failed.");
/* 85 */     return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\restful\RestfulExceptionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */