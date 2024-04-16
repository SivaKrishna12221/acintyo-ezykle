package ai.acintyo.ezykle.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ai.acintyo.ezykle.entities.EzServiceAppointment;

public interface ServiceAppointmentRepo extends JpaRepository<EzServiceAppointment, Integer> {

	@Query("from EzServiceAppointment where status<>'deleted'")
	Page<EzServiceAppointment> findAllAppointments(Pageable pageable);
}
