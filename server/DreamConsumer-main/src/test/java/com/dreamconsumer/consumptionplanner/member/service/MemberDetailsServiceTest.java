package com.dreamconsumer.consumptionplanner.member.service;

import com.dreamconsumer.consumptionplanner.member.domain.Member;
import com.dreamconsumer.consumptionplanner.member.domain.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MemberDetailsServiceTest {

    @Autowired
    private MemberDetailsService memberDetailsService;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    public void loadUserByUsernameTest() {
        // 예상되는 사용자 데이터를 설정합니다.
        Member expectedMember = new Member();
        expectedMember.setEmail("test@test.com");
        expectedMember.setPassword("testPassword");
        // ... 다른 속성들도 설정할 수 있습니다.

        // userRepository의 findByEmail 메서드가 호출되면 예상되는 사용자 데이터를 반환하도록 설정합니다.
        when(memberRepository.findByEmail("test@test.com")).thenReturn(Optional.of(expectedMember));

        UserDetails userDetails = memberDetailsService.loadUserByUsername("test@test.com");

        // 반환된 userDetails 객체가 기대하는 값과 일치하는지 확인합니다.
        assertEquals("test@test.com", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
        // ... 다른 속성들도 확인할 수 있습니다.
    }
}
