package com.turkproject.repository;

import com.turkproject.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface LogsRepository extends JpaRepository<Log, Long> {
}
