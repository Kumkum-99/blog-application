package blog.com.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import blog.com.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationErroe(MethodArgumentNotValidException ex){
    Map<String,String>errors=new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
    errors.put(error.getField(), error.getDefaultMessage()));
    return ResponseEntity.badRequest().body(errors);
	}
  
   @ExceptionHandler( ResourceNotFound.class)
   public ResponseEntity<ErrorResponse> handleResourceNotFOund(ResourceNotFound ex,WebRequest request){
   ErrorResponse respose=new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false), LocalDateTime.now());
	   return new ResponseEntity<>(respose,HttpStatus.NOT_FOUND);
   }
   @ExceptionHandler(AuthenticationException.class)
   public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex,WebRequest request){
	   ErrorResponse respose=new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false), LocalDateTime.now());
	   return new ResponseEntity<>(respose,HttpStatus.UNAUTHORIZED);
   }
}
