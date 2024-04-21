package ai.acintyo.ezykle.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ai.acintyo.ezykle.entities.EzUserRegistration;
import jakarta.transaction.Transactional;

public interface UserRegistrationRepo extends JpaRepository<EzUserRegistration, Integer> {

	@Query("from EzUserRegistration where userStatus<>'deleted'")
	Page<EzUserRegistration> findAllUsers(Pageable pageable);

	Optional<EzUserRegistration> findByEmail(String email);

	@Transactional
	@Modifying
	@Query("update EzUserRegistration u set u.password = ?2 where u.email = ?1")
	void updatePassword(String email, String password);
}
