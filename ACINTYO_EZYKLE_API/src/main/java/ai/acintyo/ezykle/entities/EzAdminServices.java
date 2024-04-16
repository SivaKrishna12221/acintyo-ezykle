package ai.acintyo.ezykle.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ListIndexBase;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "EZ_SERVICES")
@SQLDelete( sql= "update  EZ_SERVICES set serviceStatus='deleted' where id=? ")
public class EzAdminServices {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String serviceName;
	
	private Double serviceCost;
	
	private String serviceDesc;
	
	private LocalDate registrationDate;
	
	@CreationTimestamp
	private LocalDateTime serviceOptedOn;
	
	private String insterdBy;
	
	@UpdateTimestamp
	private LocalDateTime lastUpdatedOn;
	
	private String updatedBy;
	
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="EZ_TERMS_CONDITIONS",joinColumns = @JoinColumn(name="service_id",referencedColumnName ="id"))
	@OrderColumn(name="terms_conditions_index")
	@ListIndexBase(value=1)
	private List<String> termsConditions;
	
	 @ManyToOne
	  @JoinColumn(name = "SERVICE_ID", referencedColumnName = "id")
	  private EzAdminServiceCenter serviceCenter;
	 
	 private String serviceStatus="Active";
}
