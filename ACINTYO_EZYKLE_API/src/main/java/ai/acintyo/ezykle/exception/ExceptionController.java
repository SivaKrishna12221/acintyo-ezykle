package ai.acintyo.ezykle.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
