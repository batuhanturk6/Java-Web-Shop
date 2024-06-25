package com.turkproject.service;

import com.turkproject.model.Log;
import com.turkproject.repository.LogsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogsService {

    private final LogsRepository logRepository;

    public LogsService(LogsRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void save(Log log) {
        logRepository.save(log);
    }

    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

}
