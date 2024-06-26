package ai.acintyo.ezykle.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ai.acintyo.ezykle.bindings.AdminServiceRegForm;
import ai.acintyo.ezykle.bindings.AdminServicesForm;
import ai.acintyo.ezykle.entities.EzAdminServiceCenter;
import ai.acintyo.ezykle.entities.EzAdminServices;
import ai.acintyo.ezykle.exception.DataNotFoundException;
import ai.acintyo.ezykle.repositories.ServiceCenterRepo;
import ai.acintyo.ezykle.repositories.ServicesRepo;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ConfigurationProperties(prefix = "admin.service")
@Setter
public class AdminServiceImpl implements AdminService {

	private String centerSaveError;
	private String centerNotFoundError;
	private String centersNotFound;
	private String addError;
	private String servicesNotAvailable;
	private String serviceNotFound;
	private String centerDeleted;
	private String serviceDeleted;

	@Autowired
	ServiceCenterRepo centerRepo;

	@Autowired
	ServicesRepo servicesRepo;

	@Override
	public EzAdminServiceCenter serviceRegistration(AdminServiceRegForm serviceRegForm) {

		EzAdminServiceCenter serviceCenter = new EzAdminServiceCenter();

		serviceCenter.setCenterName(serviceRegForm.getCenterName());
		serviceCenter.setCenterLocation(serviceRegForm.getCenterLocation());
		serviceCenter.setContact(serviceRegForm.getContact());
		serviceCenter.setEmail(serviceRegForm.getEmail());
		serviceCenter.setCenterOpenTime(serviceRegForm.getOpeningTime());
		serviceCenter.setCenterCloseTime(serviceRegForm.getClosingTime());
		serviceCenter.setRegistrationDate(LocalDate.now());
		try {
			log.info("Service Center registered successfully");
			return centerRepo.save(serviceCenter);

		} catch (Exception e) {
			log.error("Error saving service center: {}", e.getMessage(), e);
			throw new RuntimeException(centerSaveError + e);

		}

	}

	@Override
	public Page<EzAdminServiceCenter> fetchAllServiceCenters(Pageable pageable) {

		Page<EzAdminServiceCenter> page = centerRepo.findAllServiceCenters(pageable);
		if (page.isEmpty()) {
			throw new DataNotFoundException(centersNotFound);
		} else {
			return page;
		}
	}

	@Override
	public EzAdminServiceCenter fetchServiceCenterById(Integer id) {

		Optional<EzAdminServiceCenter> center = centerRepo.findById(id);
		if (center.isEmpty()) {
			throw new IllegalArgumentException(centerNotFoundError + id);
		} else {
			return center.get();
		}
	}

	@Override
	public EzAdminServices addService(AdminServicesForm servicesForm) {
		// TODO Auto-generated method stub

		Optional<EzAdminServiceCenter> serviceCenter = centerRepo.findById(servicesForm.getServiceCenterId());
		EzAdminServices adminServices = new EzAdminServices();
		if (serviceCenter.isPresent()) {
			EzAdminServiceCenter ezAdminServiceCenter = serviceCenter.get();
			adminServices.setServiceCenter(ezAdminServiceCenter);
		} else {
			throw new IllegalArgumentException(centerNotFoundError);
		}
		adminServices.setServiceName(servicesForm.getServiceName());
		adminServices.setServiceCost(servicesForm.getServiceCost());
		adminServices.setTermsConditions(servicesForm.getTermsAndConditions());
		adminServices.setRegistrationDate(LocalDate.now());
		adminServices.setServiceDesc(servicesForm.getServiceDesc());
		try {
			log.info("Service Center registered successfully");

			return servicesRepo.save(adminServices);
		} catch (Exception e) {
			log.error("Error adding service: {}", e.getMessage(), e);

			throw new RuntimeException(addError + e);
		}

	}

	@Override
	public Page<EzAdminServices> fetchAllServices(Pageable pageable) {

		Page<EzAdminServices> page = servicesRepo.findAll(pageable);
		if (page.isEmpty()) {
			throw new DataNotFoundException(servicesNotAvailable);
		} else {
			return page;
		}

	}

	@Override
	public EzAdminServices fetchServiceById(Integer id) {
		Optional<EzAdminServices> opt = servicesRepo.findById(id);
		if (opt.isEmpty()) {
			throw new IllegalArgumentException(serviceNotFound + id);
		} else {
			return opt.get();
		}
	}

	@Override
	public EzAdminServiceCenter updateServiceCenterById(Integer id, AdminServiceRegForm serviceRegForm) {

		EzAdminServiceCenter existServiceCenter = centerRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(centerNotFoundError));
		existServiceCenter.setCenterName(serviceRegForm.getCenterName());
		existServiceCenter.setCenterLocation(serviceRegForm.getCenterLocation());
		existServiceCenter.setContact(serviceRegForm.getContact());
		existServiceCenter.setEmail(serviceRegForm.getEmail());
		existServiceCenter.setCenterOpenTime(serviceRegForm.getOpeningTime());
		existServiceCenter.setCenterCloseTime(serviceRegForm.getClosingTime());

		return centerRepo.save(existServiceCenter);
	}

	@Override
	public EzAdminServices updateServiceById(Integer id, AdminServicesForm serviceForm) {

		EzAdminServices existService = servicesRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(serviceNotFound));

		existService.setServiceName(serviceForm.getServiceName());
		existService.setServiceCost(serviceForm.getServiceCost());
		existService.setTermsConditions(serviceForm.getTermsAndConditions());
		existService.setServiceDesc(serviceForm.getServiceDesc());

		return servicesRepo.save(existService);
	}

	@Override
	public String deleteServiceCenter(Integer id) {

		EzAdminServiceCenter serviceCenter = centerRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(centerNotFoundError));
		centerRepo.delete(serviceCenter);
		return centerDeleted;
	}

	@Override
	public String deleteServiceById(Integer id) {

		EzAdminServices serviceObj = servicesRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(serviceNotFound + id));
		servicesRepo.delete(serviceObj);
		return serviceDeleted + id;
	}
}
