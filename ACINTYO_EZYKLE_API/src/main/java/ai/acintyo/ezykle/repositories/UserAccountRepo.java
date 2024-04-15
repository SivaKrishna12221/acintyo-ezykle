package ai.acintyo.ezykle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.acintyo.ezykle.entities.EzUserAccount;

public interface UserAccountRepo extends JpaRepository<EzUserAccount, Integer> {

}
