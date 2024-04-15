package ai.acintyo.ezykle.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

	private LocalDateTime ldt;
	private boolean status;
	private String message;
}
