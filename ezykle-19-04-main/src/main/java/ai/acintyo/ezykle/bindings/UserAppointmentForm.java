package ai.acintyo.ezykle.bindings;

import java.sql.Date;
import java.sql.Time;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserAppointmentForm {

	@NotBlank(message = "{user.appointment.name.required}")
	private String name;

	@NotBlank(message = "{user.appointment.phoneNumberRequired}")
	private String phno;

	@NotBlank(message = "{user.appointment.vehicleModelRequired}")
	private String vehicalModel;

	@NotNull(message = "{user.appointment.serviceRequestDateRequired}")
	private Date serviceRequestDate;

	@NotNull(message = "{user.appointment.serviceRequestTimeRequired}")
	private Time serviceRequestTime;

	@NotBlank(message = "{user.appointment.serviceTypeRequired}")
	private String serviceType;
}
