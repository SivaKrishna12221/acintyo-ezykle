package ai.acintyo.ezykle.services;


import java.util.List;

import org.springframework.data.domain.Pageable;

import ai.acintyo.ezykle.bindings.AuthenticationResponse;
import ai.acintyo.ezykle.bindings.UserRegistrationForm;
import ai.acintyo.ezykle.entities.EzUserRegistration;

public interface UserRegistrationService {
	
	AuthenticationResponse authenticate(UserRegistrationForm request);


	AuthenticationResponse saveRegistration(UserRegistrationForm registrationForm);

    List<EzUserRegistration> fetchAllUsers(Pageable pageable);
	
    EzUserRegistration fetchUserById(Integer id);
    
    EzUserRegistration UpdateUserById(Integer id,UserRegistrationForm userForm);
    
    String deleteUserById(Integer id);
}
