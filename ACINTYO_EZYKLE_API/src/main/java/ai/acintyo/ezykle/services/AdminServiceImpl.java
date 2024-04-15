package ai.acintyo.ezykle.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ai.acintyo.ezykle.bindings.AdminServiceRegForm;
import ai.acintyo.ezykle.bindings.AdminServicesForm;
import ai.acintyo.ezykle.entities.EzAdminServiceCenter;
import ai.acintyo.ezykle.entities.EzAdminServices;
import ai.acintyo.ezykle.model.DataNotFoundException;
import ai.acintyo.ezykle.repositories.ServiceCenterRepo;
import ai.acintyo.ezykle.repositories.ServicesRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

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
			log.info("ai.acintyo.ezykle.services.AdminServiceImpl::Service Center registered successfully");
			return centerRepo.save(serviceCenter);

		} catch (Exception e) {
			log.error("ai.acintyo.ezykle.services.AdminServiceImpl::Error saving service center: {}", e.getMessage(),
					e);
			throw new RuntimeException("{admin.service.centerSaveError}", e);

		}

	}

	@Override
	public Page<EzAdminServiceCenter> fetchAllServiceCenters(Pageable pageable) {
		
	    Page<EzAdminServiceCenter> page = centerRepo.findAll(pageable);
	   if( page.isEmpty())
	    {
	    	throw new DataNotFoundException("Service centers not found");
	    }
	   else
	   {
		   return page;
	   }
	}
	@Override
	public EzAdminServiceCenter fetchServiceCenterById(Integer id) {
		
		Optional<EzAdminServiceCenter> center = centerRepo.findById(id);
		if(center.isEmpty())
		{
			throw new DataNotFoundException("Service center not found");
		}
		else
		{
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
			throw new IllegalArgumentException("{admin.service.centerNotFoundError}");
		}
		adminServices.setServiceName(servicesForm.getServiceName());
		adminServices.setServiceCost(servicesForm.getServiceCost());
		adminServices.setTermsConditions(servicesForm.getTermsAndConditions());
		adminServices.setRegistrationDate(LocalDate.now());
		adminServices.setServiceDesc(servicesForm.getServiceDesc());
		try {
			log.info(
					"ai.acintyo.ezykle.services.AdminServiceImpl::ai.acintyo.ezykle.services.AdminServiceImpl::Service Center registered successfully");

			return servicesRepo.save(adminServices);
		} catch (Exception e) {
			log.error("ai.acintyo.ezykle.services.AdminServiceImpl::Error adding service: {}", e.getMessage(), e);

			throw new RuntimeException("{admin.services.addError}", e);
		}

	}@Override
	public Page<EzAdminServices> fetchAllServices(Pageable pageable) {
	
		Page<EzAdminServices> page = servicesRepo.findAll(pageable);
		if(page.isEmpty())
		{
			throw new DataNotFoundException("Services not found ");
		}
		else
		{
			return page;
		}
		
	}
	@Override
	public EzAdminServices fetchServiceById(Integer id) {
	Optional<EzAdminServices> opt = servicesRepo.findById(id);
	if(opt.isEmpty())
	{
		throw new IllegalArgumentException("Service not found");
	}
	else
	{
		return opt.get();
	}
	}
	

}
