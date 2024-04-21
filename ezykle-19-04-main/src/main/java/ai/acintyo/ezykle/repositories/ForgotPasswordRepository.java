package ai.acintyo.ezykle.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ai.acintyo.ezykle.entities.EzUserRegistration;
import ai.acintyo.ezykle.entities.ForgotPassword;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and fp.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, EzUserRegistration user);
}
