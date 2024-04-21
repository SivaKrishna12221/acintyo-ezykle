package ai.acintyo.ezykle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

import ai.acintyo.ezykle.bindings.UserAppointmentForm;
import ai.acintyo.ezykle.entities.EzServiceAppointment;
import ai.acintyo.ezykle.model.ApiResponse;
import ai.acintyo.ezykle.services.UserAppointmentService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ezykle-service")
@Data
@Slf4j
@ConfigurationProperties(prefix = "user.appointment")
public class UserAppointmentController {

	private String saveError;
	private String savedSuccessfully;
	private String saveFailed;
	private String dataFindSuccess;
	@Autowired
	private final UserAppointmentService appointmentService;

	@PostMapping("/book-appointment")
	public ResponseEntity<ApiResponse<EzServiceAppointment>> bookAppointment(
			@RequestBody @Valid UserAppointmentForm appointmentForm) {
		log.info("Attempting to book a service appointment");

		try {
			EzServiceAppointment result = appointmentService.bookAppointment(appointmentForm);
			log.info("Appointment booked successfully for user: {}", appointmentForm.getName()); // Logging user's name
																									// for context,
																									// ensure this does
																									// not breach
																									// privacy

			return ResponseEntity.ok(new ApiResponse<>(true, savedSuccessfully, result));
		} catch (Exception e) {
			log.error("Error booking appointment: {}", e.getMessage(), e);

			return new ResponseEntity<>(new ApiResponse<>(false, saveFailed + e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/get-all-appointments")
	public ResponseEntity<?> getAllAppointments(@PageableDefault Pageable pageable) {

		log.info("Attempting to get all Appointments");
		return ResponseEntity
				.ok(new ApiResponse<>(true, dataFindSuccess, appointmentService.fetchAllAppointments(pageable)));
	}

	@GetMapping("/get-appointment/{id}")
	public ResponseEntity<ApiResponse<EzServiceAppointment>> getAppointment(@PathVariable Integer id) {
		log.info("Attempting to get  Appointment");
		return ResponseEntity
				.ok(new ApiResponse<>(true, dataFindSuccess, appointmentService.fetchAppointementById(id)));
	}
}
