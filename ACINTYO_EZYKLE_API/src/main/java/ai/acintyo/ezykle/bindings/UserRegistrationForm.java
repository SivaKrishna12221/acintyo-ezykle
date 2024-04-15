package ai.acintyo.ezykle.bindings;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegistrationForm {

	@NotBlank(message = "{user.registration.nameRequired}")
	private String name;

	@NotBlank(message = "{user.registration.mobileNumberRequired}")
	private String mobileNumber;

	@NotBlank(message = "{user.registration.emailRequired}")
	@Email(message = "{user.registration.emailFormat}")
	private String email;

	@NotBlank(message = "{user.registration.passwordRequired}")
	private String password;

	@NotBlank(message = "{user.registration.confirmPasswordRequired}")
	private String confirmPassword;

	@NotBlank(message = "{user.registration.bankNameRequired}")
	private String bankName;

	@NotBlank(message = "{user.registration.accountNumberRequired}")
	@Pattern(regexp = "\\d+", message = "{user.registration.accountNumberFormat}")
	private String accountNumber;

	@NotBlank(message = "{user.registration.ifscCodeRequired}")
	@Pattern(regexp = "[A-Z]{4}0[A-Z0-9]{6}", message = "{user.registration.ifscCodeFormat}")
	private String ifscCode;

	@NotBlank(message = "{user.registration.bankBranchRequired}")
	private String bankBranch;
}
