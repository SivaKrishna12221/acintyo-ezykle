package ai.acintyo.ezykle.bindings;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminServiceRegForm {

	@NotBlank(message = "{admin.service.centerNameRequired}")
	private String centerName;

	@NotBlank(message = "{admin.service.centerLocationRequired}")
	private String centerLocation;

	@NotBlank(message = "{admin.service.contactRequired}")
	private String contact;

	@NotBlank(message = "{admin.service.emailRequired}")
	@Email(message = "{admin.service.emailFormat}")
	private String email;

	@NotNull(message = "{admin.service.openingTimeRequired}")
	@DateTimeFormat(pattern = "HH:mm")

	private LocalTime openingTime;

	@NotNull(message = "{admin.service.closingTimeRequired}")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime closingTime;
}
