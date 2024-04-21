package ai.acintyo.ezykle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.acintyo.ezykle.bindings.AuthenticationResponse;
import ai.acintyo.ezykle.bindings.UserRegistrationForm;
import ai.acintyo.ezykle.entities.EzUserRegistration;
import ai.acintyo.ezykle.model.ApiResponse;
import ai.acintyo.ezykle.services.UserRegistrationService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ezykle-user")
@ConfigurationProperties(prefix = "user.registration")
@ConfigurationPropertiesScan
@Data
@Slf4j
public class UserRegistrationController {

	private String success;
	private String failed;
	private String users;
	private String update;
	private String userDataFetchSuccess;

	@Autowired
	UserRegistrationService registrationService;

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody UserRegistrationForm request) {
		return registrationService.authenticate(request);
	}

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> regist(@RequestBody @Valid UserRegistrationForm userInfo) {
		log.info("Attempting to register new user");

		return registrationService.saveRegistration(userInfo);

	}

	@GetMapping("/get-all-users")
	public ResponseEntity<ApiResponse<Page<EzUserRegistration>>> getAllUserDetails(
	   @PageableDefault Pageable pageable) {

		log.info("Attempting to get all Users");
	
		return ResponseEntity.ok(new ApiResponse<>(true, users, registrationService.fetchAllUsers(pageable)));
	}

	@GetMapping("/get-user/{id}")
	public ResponseEntity<ApiResponse<EzUserRegistration>> getUser(@PathVariable("id") Integer id) {
		log.info("Attempting fetch the user details");
		return ResponseEntity.ok(new ApiResponse<>(true, userDataFetchSuccess, registrationService.fetchUserById(id)));
	}

	@PutMapping("/update-user/{id}")
	public ResponseEntity<ApiResponse<EzUserRegistration>> updateUser(@PathVariable("id") Integer id,
			@RequestBody @Valid UserRegistrationForm userInfo) {
		log.info("attempting to update user");
		return ResponseEntity.ok(new ApiResponse<>(true, update, registrationService.UpdateUserById(id, userInfo)));
	}
	@GetMapping("/get-user/{mrId")
}
