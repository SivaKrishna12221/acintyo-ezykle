package ai.acintyo.ezykle.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import ai.acintyo.ezykle.bindings.AuthenticationResponse;
import ai.acintyo.ezykle.bindings.UserRegistrationForm;
import ai.acintyo.ezykle.entities.EzUserRegistration;

public interface UserRegistrationService {

	public ResponseEntity<AuthenticationResponse> authenticate(UserRegistrationForm request);

	public ResponseEntity<AuthenticationResponse> saveRegistration(UserRegistrationForm registrationForm);

	public Page<EzUserRegistration> fetchAllUsers(Pageable pageable);

	public EzUserRegistration fetchUserById(Integer id);

	public EzUserRegistration UpdateUserById(Integer id, UserRegistrationForm userForm);

	public String deleteUserById(Integer id);

}
