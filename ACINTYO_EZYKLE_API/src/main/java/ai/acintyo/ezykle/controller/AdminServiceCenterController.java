package ai.acintyo.ezykle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.acintyo.ezykle.bindings.AdminServiceRegForm;
import ai.acintyo.ezykle.bindings.AdminServicesForm;
import ai.acintyo.ezykle.entities.EzAdminServiceCenter;
import ai.acintyo.ezykle.entities.EzAdminServices;
import ai.acintyo.ezykle.model.ApiResponse;
import ai.acintyo.ezykle.services.AdminService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ezykle-admin")
@Slf4j
public class AdminServiceCenterController {

	@Autowired
	AdminService adminService;

	@PostMapping("/add-service-center")
	public ResponseEntity<ApiResponse<EzAdminServiceCenter>> saveRegistraion(
			@RequestBody @Valid AdminServiceRegForm regForm) {
		log.debug(
				"ai.acintyo.ezykle.controller.AdminServiceCenterController::Attempting to register new service center");

		try {
			EzAdminServiceCenter result = adminService.serviceRegistration(regForm);
			log.info(
					"ai.acintyo.ezykle.controller.AdminServiceCenterController::Service center registered successfully");

			return ResponseEntity.ok(new ApiResponse<>(true, "{admin.service.centerRegisteredSuccessfully}", result));
		} catch (Exception e) {
			log.error(
					"ai.acintyo.ezykle.controller.AdminServiceCenterController::Failed to register service center: {}",
					e.getMessage(), e);

			return new ResponseEntity<>(
					new ApiResponse<>(false, "{admin.service.failedToRegisterServiceCenter}" + e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/add-service")
	public ResponseEntity<ApiResponse<EzAdminServices>> addService(@RequestBody @Valid AdminServicesForm servicesForm) {
		log.debug("ai.acintyo.ezykle.controller::Attempting to add new service");

		try {
			EzAdminServices result = adminService.addService(servicesForm);
			log.info("ai.acintyo.ezykle.controller.AdminServiceCenterController::Service added successfully");

			return ResponseEntity.ok(new ApiResponse<>(true, "{admin.services.addedSuccessfully}", result));
		} catch (Exception e) {
			log.error("ai.acintyo.ezykle.controller.AdminServiceCenterController::Failed to add service: {}",
					e.getMessage(), e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(false, "{admin.services.failedToAddService}" + e.getMessage(), null));
		}

	}

}
