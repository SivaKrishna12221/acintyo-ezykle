package ai.acintyo.ezykle.bindings;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AdminServicesForm {

	@NotNull(message = "{admin.services.centerIdRequired}")
	private Integer serviceCenterId;

	@NotBlank(message = "{admin.services.nameRequired}")
	private String serviceName;

	@NotNull(message = "{admin.services.costRequired}")
	@Positive(message = "{admin.services.costPositive}")
	private Double serviceCost;

	@NotBlank(message = "{admin.services.descRequired}")
	private String serviceDesc;

	@NotNull(message = "{admin.services.termsAndConditionRequired}")
	private List<String> termsAndConditions;
}
