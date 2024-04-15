package ai.acintyo.ezykle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ai.acintyo.ezykle.entities.EzAdminServiceCenter;

@Repository
public interface ServiceCenterRepo extends JpaRepository<EzAdminServiceCenter, Integer>{
	
	
}
