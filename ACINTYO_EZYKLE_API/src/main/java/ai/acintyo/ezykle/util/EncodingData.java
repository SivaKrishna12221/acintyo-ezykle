package ai.acintyo.ezykle.util;

import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.stereotype.Component;

@Component
public class EncodingData {
	
	public String getEncodedData(String str) {
		
		Encoder encoder = Base64.getEncoder();
		
		String encodeToString = encoder.encodeToString(str.getBytes());
		
		return encodeToString;
	}

}
