package com.dreamconsumer.consumptionplanner.auth.utils;

import com.dreamconsumer.consumptionplanner.response.ErrorResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class ErrorResponder {
    public static void sendErrorResponse(HttpServletResponse response , HttpStatus status) throws IOException {
        Gson gson = new Gson();

        ErrorResponse errorResponse = ErrorResponse.of(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
    }
}
