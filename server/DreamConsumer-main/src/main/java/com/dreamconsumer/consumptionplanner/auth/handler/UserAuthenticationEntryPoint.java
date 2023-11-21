package com.dreamconsumer.consumptionplanner.auth.handler;

import com.dreamconsumer.consumptionplanner.auth.utils.ErrorResponder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws ServletException, IOException {
        Exception exception = (Exception) request.getAttribute("exception");
        ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED);

        logExceptionMessage(authenticationException, exception);
    }

    private void logExceptionMessage(AuthenticationException authenticationException, Exception exception) {
        // request에 exception attribute 포함 -> 그 exception, 아니면 AuthenticationExeption
        String message = exception != null ? exception.getMessage() : authenticationException.getMessage();
        log.warn("Unauthorized Error Happend : {}", message);
    }
}
