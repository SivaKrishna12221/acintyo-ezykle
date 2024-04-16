package ai.acintyo.ezykle.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ai.acintyo.ezykle.entities.EzAdminServiceCenter;

@Repository
public interface ServiceCenterRepo extends JpaRepository<EzAdminServiceCenter, Integer>{
	
	@Query("from EzAdminServiceCenter where status<>'deleted'")
	Page<EzAdminServiceCenter> findAllServiceCenters(Pageable pageable);
	
}
