package ai.acintyo.ezykle.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ai.acintyo.ezykle.bindings.UserAppointmentForm;
import ai.acintyo.ezykle.entities.EzServiceAppointment;
import ai.acintyo.ezykle.exception.DataNotFoundException;
import ai.acintyo.ezykle.repositories.ServiceAppointmentRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserAppointmentImpl implements UserAppointmentService {

	@Autowired
	ServiceAppointmentRepo appointmentRepo;

	@Override
	public EzServiceAppointment bookAppointment(UserAppointmentForm appointmentForm) {

		EzServiceAppointment appointment = new EzServiceAppointment();

		appointment.setName(appointmentForm.getName());
		appointment.setPhno(appointmentForm.getPhno());
		appointment.setVehicalModel(appointmentForm.getVehicalModel());
		appointment.setDate(appointmentForm.getServiceRequestDate());
		appointment.setTime(appointmentForm.getServiceRequestTime());
		appointment.setServiceType(appointmentForm.getServiceType());

		try {
			log.info("ai.acintyo.ezykle.services.UserAppointmentImpl::Appointment booked successfully for: {}");

			return appointmentRepo.save(appointment);
		} catch (Exception e) {
			log.error("ai.acintyo.ezykle.services.UserAppointmentImpl::Error booking appointment for: {}",
					e.getMessage(), e);

			throw new RuntimeException("{user.appointment.saveError}", e);
		}

	}

	@Override
	public List<EzServiceAppointment> fetchAllAppointments(Pageable pageable) {
		Page<EzServiceAppointment> page = appointmentRepo.findAll(pageable);
		if (page.isEmpty()) {
			throw new DataNotFoundException("Appointments are not available");
		} else {
			return page.getContent();
		}
	}

	@Override
	public EzServiceAppointment fetchAppointementById(Integer id) {
		Optional<EzServiceAppointment> opt = appointmentRepo.findById(id);
		if (opt.isEmpty()) {
			throw new IllegalArgumentException("Appointment is not available for the given id:" + id);
		} else {
			return opt.get();
		}
	}
	@Override
	public EzServiceAppointment updateServiceAppointmentById(Integer id, UserAppointmentForm appointmentForm) {
		
	 EzServiceAppointment existAppointment= appointmentRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Appointment not available"));
	 existAppointment.setName(appointmentForm.getName());
		existAppointment.setPhno(appointmentForm.getPhno());
		existAppointment.setVehicalModel(appointmentForm.getVehicalModel());
		existAppointment.setDate(appointmentForm.getServiceRequestDate());
		existAppointment.setTime(appointmentForm.getServiceRequestTime());
		existAppointment.setServiceType(appointmentForm.getServiceType());
	 return  appointmentRepo.save(existAppointment);

	}
	@Override
	public String deleteAppointmentById(Integer id) {
		EzServiceAppointment appointment = appointmentRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Appointment not available by given id:"+id));
		appointmentRepo.delete(appointment);
		return "appointment is deleted by given Id:"+id;
	}
	
}
