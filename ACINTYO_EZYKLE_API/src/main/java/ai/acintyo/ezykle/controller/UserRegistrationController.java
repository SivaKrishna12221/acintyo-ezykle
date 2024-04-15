package ai.acintyo.ezykle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.acintyo.ezykle.bindings.UserRegistrationForm;
import ai.acintyo.ezykle.entities.EzUserRegistration;
import ai.acintyo.ezykle.model.ApiResponse;
import ai.acintyo.ezykle.services.UserRegistrationService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ezykle-user")
@Slf4j
@ConfigurationProperties(prefix="user.registration")
@Data
public class UserRegistrationController {

	private String success;
	private String failed;
	@Autowired
	UserRegistrationService registrationService;

	@PostMapping("/save")
	public ResponseEntity<ApiResponse<EzUserRegistration>> getUserData(
			@RequestBody @Valid UserRegistrationForm userInfo) {

		
		log.info("ai.acintyo.ezykle.controller.UserRegistrationController::Attempting to register new user");

		try {
			EzUserRegistration result = registrationService.saveRegistration(userInfo);
			log.info("ai.acintyo.ezykle.controller.UserRegistrationController::User Registration Successfull");

			return ResponseEntity.ok(new ApiResponse<>(true, success, result));
		} catch (Exception e) {
			log.error("ai.acintyo.ezykle.controller.UserRegistrationController::User Registration Failed"
					+ e.getMessage());

			return new ResponseEntity<>(new ApiResponse<>(false, failed + e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@GetMapping("/all-users")
	public ResponseEntity<ApiResponse<Page<EzUserRegistration>>> getAllUserDetails(@PageableDefault Pageable pageable)
	{
       log.info("ai.acintyo.ezykle.controller.UserRegistrationController::Attempting to get all Users");
       return ResponseEntity.ok(new ApiResponse<>(true,"{user.registration.users}",registrationService.fetchAllUsers(pageable)));
	}
	@GetMapping("/get-user/{id}")
	public ResponseEntity<ApiResponse<EzUserRegistration>> getUser(@PathVariable Integer id)
	{
		log.info("ai.acintyo.ezykle.controller.UserRegistrationController::Attempting fetch the user details");
		return ResponseEntity.ok(new ApiResponse<>(true,"user find successfully",registrationService.fetchUserById(id)));
	}

}
