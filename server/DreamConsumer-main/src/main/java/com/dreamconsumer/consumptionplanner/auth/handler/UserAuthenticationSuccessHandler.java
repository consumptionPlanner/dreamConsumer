package com.dreamconsumer.consumptionplanner.auth.handler;

import com.dreamconsumer.consumptionplanner.member.domain.Member;
import com.dreamconsumer.consumptionplanner.member.dto.MemberLoginResponseDto;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Object principal = authentication.getPrincipal();
        if(principal instanceof Member) {
            log.info("# User Authenticated successfully!");

            Member member = (Member) principal;
            sendSuccessResponse(response, member);
        }
    }

    private void sendSuccessResponse(HttpServletResponse response, Member member) throws IOException {
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(new MemberLoginResponseDto(member.getTier(), member.getMemberId()));
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}
