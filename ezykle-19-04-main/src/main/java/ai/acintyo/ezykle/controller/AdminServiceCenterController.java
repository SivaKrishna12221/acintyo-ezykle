package ai.acintyo.ezykle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ezykle-admin")
@Slf4j
@Data
@ConfigurationProperties(prefix = "admin.service")
public class AdminServiceCenterController {

	private String centerRegisteredSuccessfully;
	private String failedToRegisterServiceCenter;
	private String addedSuccessfully;
	private String failedToAddService;
	private String dataFind;
	private String centerUpdatedSuccessfully;
	private String serviceUpdateSuccess;

	@Autowired
	AdminService adminService;

	@PostMapping("/add-service-center")
	public ResponseEntity<ApiResponse<EzAdminServiceCenter>> saveRegistraion(
			@RequestBody @Valid AdminServiceRegForm regForm) {
		log.debug("Attempting to register new service center");

		try {
			EzAdminServiceCenter result = adminService.serviceRegistration(regForm);
			log.info("Service center registered successfully");

			return ResponseEntity.ok(new ApiResponse<>(true, centerRegisteredSuccessfully, result));
		} catch (Exception e) {
			log.error("Failed to register service center: {}", e.getMessage(), e);

			return new ResponseEntity<>(
					new ApiResponse<>(false, failedToRegisterServiceCenter + "" + e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/get-all-service-centers")
	public ResponseEntity<?> getAllServiceCenters(
			@PageableDefault Pageable pageable) {
		log.info("attempting find all service centers ");
		return ResponseEntity.ok(new ApiResponse<>(true, dataFind, adminService.fetchAllServiceCenters(pageable)));
	}

	@GetMapping("/get-service-center/{id}")
	public ResponseEntity<ApiResponse<EzAdminServiceCenter>> getServiceCenter(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(new ApiResponse<>(true, dataFind, adminService.fetchServiceCenterById(id)));
	}

	@PostMapping("/add-service")
	public ResponseEntity<ApiResponse<EzAdminServices>> addService(@RequestBody @Valid AdminServicesForm servicesForm) {
		log.debug("Attempting to add new service");

		try {
			EzAdminServices result = adminService.addService(servicesForm);
			log.info("Service added successfully");

			return ResponseEntity.ok(new ApiResponse<>(true, addedSuccessfully, result));
		} catch (Exception e) {
			log.error("Failed to add service: {}", e.getMessage(), e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(false, failedToAddService + "" + e.getMessage(), null));
		}

	}

	@GetMapping("/get-all-services")
	public ResponseEntity<?> getAllServices(@PageableDefault Pageable pageable) {
		
		log.info("attempting find all services ");
		return ResponseEntity.ok(new ApiResponse<>(true, dataFind, adminService.fetchAllServices(pageable)));
	}

	@GetMapping("/get-service/{id}")
	public ResponseEntity<ApiResponse<EzAdminServices>> getService(@PathVariable Integer id) {
		return ResponseEntity.ok(new ApiResponse<>(true, dataFind, adminService.fetchServiceById(id)));
	}

	@PutMapping("/update-service-center/{id}")
	public ResponseEntity<ApiResponse<EzAdminServiceCenter>> updateServiceCenter(@PathVariable("id") Integer id,
			@RequestBody AdminServiceRegForm regForm) {
		return ResponseEntity.ok(
				new ApiResponse<>(true, centerUpdatedSuccessfully, adminService.updateServiceCenterById(id, regForm)));
	}

	@PutMapping("/update-service/{id}")
	public ResponseEntity<?> updateService(@PathVariable("id") Integer id, @RequestBody AdminServicesForm serviceForm) {
		return ResponseEntity
				.ok(new ApiResponse<>(true, serviceUpdateSuccess, adminService.updateServiceById(id, serviceForm)));
	}
}
