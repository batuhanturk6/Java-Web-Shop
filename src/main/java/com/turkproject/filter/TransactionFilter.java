package com.turkproject.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
@Slf4j
public class TransactionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        long startTime = System.currentTimeMillis();

        log.info(
                "Starting a transaction for req : {}",
                req.getRequestURI());

        try{
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            log.info("Do in {} ms", time);
        }

        log.info(
                "Committing a transaction for req : {}",
                req.getRequestURI());
    }
}