package ai.acintyo.ezykle.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "EZ_USER_REGISTRATION")
public class EzUserRegistration implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 99523977307357780L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String mobile;

	private String email;

	private String password;

	private String confirmPassword;

	private LocalDate registrationDate;

	@CreationTimestamp
	private LocalDateTime serviceOptedOn;

	private String insertedBy;

	@UpdateTimestamp
	private LocalDateTime lastUpdatedOn;

	private String updatedBy;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_account_id")
	private EzUserAccount userAccount;

	private String userStatus="Active";
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public String getUsername() {
	
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}
}
