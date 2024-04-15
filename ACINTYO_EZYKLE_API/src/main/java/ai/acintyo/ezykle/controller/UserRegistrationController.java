package ai.acintyo.ezykle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.acintyo.ezykle.bindings.UserRegistrationForm;
import ai.acintyo.ezykle.entities.EzUserRegistration;
import ai.acintyo.ezykle.model.ApiResponse;
import ai.acintyo.ezykle.services.UserRegistrationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ezykle-user")
@Slf4j
public class UserRegistrationController {

	@Autowired
	UserRegistrationService registrationService;

	@PostMapping("/save")
	public ResponseEntity<ApiResponse<EzUserRegistration>> getUserData(
			@RequestBody @Valid UserRegistrationForm userInfo) {

		log.info("ai.acintyo.ezykle.controller.UserRegistrationController::Attempting to register new user");

		try {
			EzUserRegistration result = registrationService.saveRegistration(userInfo);
			log.info("ai.acintyo.ezykle.controller.UserRegistrationController::User Registration Successfull");

			return ResponseEntity.ok(new ApiResponse<>(true, "{user.registration.success}", result));
		} catch (Exception e) {
			log.error("ai.acintyo.ezykle.controller.UserRegistrationController::User Registration Failed"
					+ e.getMessage());

			return new ResponseEntity<>(new ApiResponse<>(false, "{user.registration.failed}" + e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
