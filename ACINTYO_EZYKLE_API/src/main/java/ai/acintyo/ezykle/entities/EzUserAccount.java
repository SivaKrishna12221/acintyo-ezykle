package ai.acintyo.ezykle.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="EZ_USER_ACCOUNT")
public class EzUserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String bankName;
	
	private String accountNumber;
	
	private String ifscCode;
	
	private String branch;
	
	private LocalDate registrationDate;
	
	@CreationTimestamp
	private LocalDateTime serviceOptedOn;

	private String insertedBy;

	@UpdateTimestamp
	private LocalDateTime lastUpdatedOn;

	private String updatedBy;
	
	
	

}
