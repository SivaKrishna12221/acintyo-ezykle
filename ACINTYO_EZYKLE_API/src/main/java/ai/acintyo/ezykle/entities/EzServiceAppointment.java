package ai.acintyo.ezykle.entities;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "EZ_SERVICE_APPOINTMENT")
@SQLDelete(sql="update EZ_SERVICE_APPOINTMENT set status='deleted' where id=?")

public class EzServiceAppointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String phno;

	private String vehicalModel;

	private Date date;
	
	private Time time;

	private String serviceType;

	private LocalDate registrationDate;

	@CreationTimestamp
	private LocalDateTime serviceOptedOn;

	private String insterdBy;

	@UpdateTimestamp
	private LocalDateTime lastUpdatedOn;

	private String updatedBy;
	
	private String status="Active";

}
