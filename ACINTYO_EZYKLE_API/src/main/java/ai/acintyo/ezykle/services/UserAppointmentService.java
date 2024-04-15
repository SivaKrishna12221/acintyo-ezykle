package ai.acintyo.ezykle.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.acintyo.ezykle.bindings.UserAppointmentForm;
import ai.acintyo.ezykle.entities.EzServiceAppointment;

public interface UserAppointmentService {
	
	EzServiceAppointment bookAppointment(UserAppointmentForm appointmentForm);

	Page<EzServiceAppointment> fetchAllAppointments(Pageable pageable);
	
	EzServiceAppointment fetchAppointementById(Integer id);
}
