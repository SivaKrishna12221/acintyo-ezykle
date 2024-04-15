package ai.acintyo.ezykle.services;

import ai.acintyo.ezykle.bindings.AdminServiceRegForm;
import ai.acintyo.ezykle.bindings.AdminServicesForm;
import ai.acintyo.ezykle.entities.EzAdminServiceCenter;
import ai.acintyo.ezykle.entities.EzAdminServices;

public interface AdminService {
	
	EzAdminServiceCenter serviceRegistration(AdminServiceRegForm serviceRegForm);
	
    EzAdminServices addService(AdminServicesForm servicesForm);
	
}
