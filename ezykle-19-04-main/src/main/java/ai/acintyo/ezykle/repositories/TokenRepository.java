
package ai.acintyo.ezykle.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ai.acintyo.ezykle.entities.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("select t from Token t inner join EzUserRegistration u on t.user.id = u.id where t.user.id = :userId and t.loggedOut = false")
    List<Token> findAllTokensByUserId(@Param("userId") Integer userId);

	Optional<Token> findByToken(String token);
}
