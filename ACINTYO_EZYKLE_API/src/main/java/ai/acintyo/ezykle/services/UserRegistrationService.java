package ai.acintyo.ezykle.services;

import ai.acintyo.ezykle.bindings.UserRegistrationForm;
import ai.acintyo.ezykle.entities.EzUserRegistration;

public interface UserRegistrationService {

	EzUserRegistration saveRegistration(UserRegistrationForm registrationForm);

	
}
