package ai.acintyo.ezykle.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ai.acintyo.ezykle.bindings.UserAppointmentForm;
import ai.acintyo.ezykle.entities.EzServiceAppointment;
import ai.acintyo.ezykle.model.DataNotFoundException;
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

	}@Override
	public Page<EzServiceAppointment> fetchAllAppointments(Pageable pageable) {
		Page<EzServiceAppointment> page = appointmentRepo.findAll(pageable);
		if(page.isEmpty())
		{
			throw new DataNotFoundException("Appointments not found");
		}
		else
		{
			return page;
		}
	}
	@Override
	public EzServiceAppointment fetchAppointementById(Integer id) {
	Optional<EzServiceAppointment> opt = appointmentRepo.findById(id);
	if(opt.isEmpty())
	{
		throw new IllegalArgumentException("Appointment not found");
	}
	else
	{
		return opt.get();
	}
	}

}
