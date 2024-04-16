package ai.acintyo.ezykle.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import ai.acintyo.ezykle.model.ErrorDetails;

@RestControllerAdvice
public class ExceptionController {

	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String,String> validationException(MethodArgumentNotValidException me)
	{
		Map<String,String> hashMap = new HashMap<>();
		me.getBindingResult().getFieldErrors().forEach(x->hashMap.put(x.getField(),x.getDefaultMessage()));
	    return hashMap;
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ErrorDetails> dataNotFoundException(DataNotFoundException dn,WebRequest request)
	{
		
	return new ResponseEntity<>(new ErrorDetails(HttpStatus.NO_CONTENT.value(), dn.getMessage(), request.getDescription(false)),HttpStatus.NO_CONTENT);
	}
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArugmentException(IllegalArgumentException il,WebRequest request)
	{
		return new ResponseEntity<ErrorDetails>(new ErrorDetails(HttpStatus.BAD_REQUEST.value(),il.getMessage(),request.getDescription(false)),HttpStatus.BAD_REQUEST);
	}
  
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleRuntimeException(RuntimeException re,WebRequest request)
	{
		return new ResponseEntity<>(new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),re.getMessage() ,request.getDescription(false)),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

}
