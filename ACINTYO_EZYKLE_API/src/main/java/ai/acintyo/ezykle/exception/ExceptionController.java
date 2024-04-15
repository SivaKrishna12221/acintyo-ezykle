package ai.acintyo.ezykle.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ai.acintyo.ezykle.model.DataNotFoundException;
import ai.acintyo.ezykle.model.Response;

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
	public ResponseEntity<Response> dataNotFoundException(DataNotFoundException dn)
	{
	  return new ResponseEntity<Response>(new Response(LocalDateTime.now(), false, dn.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Response> illegalArugmentException(IllegalArgumentException il)
	{
		return new ResponseEntity<Response>(new Response(LocalDateTime.now(),false,il.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
