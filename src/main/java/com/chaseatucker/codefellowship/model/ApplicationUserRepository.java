package com.chaseatucker.codefellowship.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
  UserDetails findByUsername(String username);
}
