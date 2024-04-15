package ai.acintyo.ezykle.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.acintyo.ezykle.bindings.UserRegistrationForm;
import ai.acintyo.ezykle.entities.EzUserRegistration;

public interface UserRegistrationService {

	EzUserRegistration saveRegistration(UserRegistrationForm registrationForm);

    Page<EzUserRegistration> fetchAllUsers(Pageable pageable);
	
    EzUserRegistration fetchUserById(Integer id);
}
