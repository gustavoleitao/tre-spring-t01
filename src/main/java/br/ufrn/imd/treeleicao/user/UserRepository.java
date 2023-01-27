package br.ufrn.imd.treeleicao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.status is true")
    Collection<User> findAllActiveUsers();

}
