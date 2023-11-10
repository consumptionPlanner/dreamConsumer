package com.example.demo.auth.utils;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

public class ErrorResponder {
    public static void sendErrorResponse(HttpServletResponse response , HttpStatus status) {
        Gson gson = new Gson();
    // 여기부터 시작!!!
    }
}
