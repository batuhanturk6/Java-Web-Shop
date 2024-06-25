package com.turkproject.repository;

import com.turkproject.model.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<ApplicationUser,Long> {
    ApplicationUser findByUsername(final String username);
}
