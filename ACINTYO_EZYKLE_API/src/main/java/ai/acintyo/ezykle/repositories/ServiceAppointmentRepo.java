package ai.acintyo.ezykle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.acintyo.ezykle.entities.EzServiceAppointment;

public interface ServiceAppointmentRepo extends JpaRepository<EzServiceAppointment, Integer> {

}
