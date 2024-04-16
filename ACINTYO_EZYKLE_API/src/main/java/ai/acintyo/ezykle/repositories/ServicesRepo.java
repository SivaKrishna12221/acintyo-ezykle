package ai.acintyo.ezykle.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ai.acintyo.ezykle.entities.EzAdminServices;

public interface ServicesRepo extends JpaRepository<EzAdminServices, Integer>{

	@Query("from EzAdminServices where serviceStatus<>'deleted'")
	Page<EzAdminServices> findAllServices(Pageable pageable);
}
