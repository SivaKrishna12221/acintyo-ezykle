package ai.acintyo.ezykle.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.acintyo.ezykle.bindings.AdminServiceRegForm;
import ai.acintyo.ezykle.bindings.AdminServicesForm;
import ai.acintyo.ezykle.entities.EzAdminServiceCenter;
import ai.acintyo.ezykle.entities.EzAdminServices;

public interface AdminService {
	
	EzAdminServiceCenter serviceRegistration(AdminServiceRegForm serviceRegForm);
	
    EzAdminServices addService(AdminServicesForm servicesForm);
	
    Page<EzAdminServiceCenter> fetchAllServiceCenters(Pageable pageable);
    
    EzAdminServiceCenter fetchServiceCenterById(Integer id);
    
    String deleteServiceCenter(Integer id);
    
    Page<EzAdminServices> fetchAllServices(Pageable pageable);
    
    EzAdminServices fetchServiceById(Integer id);
    
    EzAdminServiceCenter updateServiceCenterById(Integer id,AdminServiceRegForm serviceRegForm);
    
    EzAdminServices updateServiceById(Integer id,AdminServicesForm serviceForm);
    
    String deleteServiceById(Integer id);
}
