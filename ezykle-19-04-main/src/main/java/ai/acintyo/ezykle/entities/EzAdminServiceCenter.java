package ai.acintyo.ezykle.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
@Table(name="EZ_ADMIN_SERVICE_CENTER")
@SQLDelete(sql="update EZ_ADMIN_SERVICE_CENTER set status='deleted' where id=?")
public class EzAdminServiceCenter {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String centerName;
	
	private String centerLocation;
	
	private String contact;
	
	private String email;
	
	private LocalTime centerOpenTime;
	
	private LocalTime centerCloseTime;
	
	private LocalDate registrationDate;
	
	@CreationTimestamp
	private LocalDateTime serviceOptedOn;
	
	private String insertedBy;
	
	@UpdateTimestamp
	private LocalDateTime lastUpdatedOn;
	
	private String updatedBy;
	
	private String status="Active";
	
}
