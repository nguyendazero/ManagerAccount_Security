package com.manager_account.repositories;

import com.manager_account.entities.User;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	
    boolean existsByUsername(String username);
    
    @Query("SELECT r.name FROM Users_Roles ur JOIN ur.role r WHERE ur.user.username = :username")
    Set<String> findRolesByUsername(@Param("username") String username);
}
