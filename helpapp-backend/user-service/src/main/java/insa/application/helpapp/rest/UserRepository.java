package insa.application.helpapp.rest;

import org.springframework.data.jpa.repository.JpaRepository;
/*
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
*/

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUsername(String username);

    /*
    @Query("SELECT t FROM ConnectionToken t WHERE t.userId = :userId")
    List<ConnectionToken> findCustomTokensByUserId(@Param("userId") int userId);
     */
}
