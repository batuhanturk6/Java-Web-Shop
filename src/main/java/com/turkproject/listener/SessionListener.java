package com.turkproject.listener;


import com.turkproject.model.Log;
import com.turkproject.service.LogsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.Objects;

@Component
@Slf4j
public class SessionListener implements HttpSessionListener {

    private LogsService logsService;

    @Autowired
    public SessionListener(LogsService logsService) {
        this.logsService = logsService;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();

        if(session.getAttribute("logged") == null) {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.currentRequestAttributes()))
                    .getRequest();

            String ipAddress = request.getRemoteAddr();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();


            logsService.save(new Log(username, ipAddress, new Date()));

            session.setAttribute("logged", true);
        }

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute("logged", false);
    }
}
