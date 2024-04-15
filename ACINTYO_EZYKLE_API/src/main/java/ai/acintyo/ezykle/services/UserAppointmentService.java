package ai.acintyo.ezykle.services;

import ai.acintyo.ezykle.bindings.UserAppointmentForm;
import ai.acintyo.ezykle.entities.EzServiceAppointment;

public interface UserAppointmentService {
	
	EzServiceAppointment bookAppointment(UserAppointmentForm appointmentForm);

}
