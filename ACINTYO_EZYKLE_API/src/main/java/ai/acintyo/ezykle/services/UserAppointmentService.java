package ai.acintyo.ezykle.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import ai.acintyo.ezykle.bindings.UserAppointmentForm;
import ai.acintyo.ezykle.entities.EzServiceAppointment;

public interface UserAppointmentService {
	
	EzServiceAppointment bookAppointment(UserAppointmentForm appointmentForm);

	List<EzServiceAppointment> fetchAllAppointments(Pageable pageable);
	
	EzServiceAppointment fetchAppointementById(Integer id);
	
	EzServiceAppointment updateServiceAppointmentById(Integer id,UserAppointmentForm appointmentForm);
    
	String deleteAppointmentById(Integer id);

}
