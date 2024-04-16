package ai.acintyo.ezykle.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ai.acintyo.ezykle.entities.EzUserRegistration;

public interface UserRegistrationRepo extends JpaRepository<EzUserRegistration, Integer> {

	@Query("from EzUserRegistration where userStatus<>'deleted'")
	Page<EzUserRegistration> findAllUsers(Pageable pageable);
	
	Optional<EzUserRegistration> findByEmail(String email);
}
