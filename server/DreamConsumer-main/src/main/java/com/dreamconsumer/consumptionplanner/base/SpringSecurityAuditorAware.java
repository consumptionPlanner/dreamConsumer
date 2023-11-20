package com.dreamconsumer.consumptionplanner.base;

import com.dreamconsumer.consumptionplanner.member.domain.Member;
import com.dreamconsumer.consumptionplanner.member.domain.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        String username = null;
        // 'principal'이 UserDetails의 인스턴스인 경우에만 처리
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            // UserDetails의 getUsername() 메서드를 사용하여 사용자 이름 가져오기
            username = userDetails.getUsername();
            // 사용자 이름을 사용하여 User 엔티티 조회
            // 이 부분은 실제 User 엔티티를 반환하는 로직으로 대체해야 합니다.
            // 예를 들어, userRepository를 주입받아 findByUsername 메서드를 사용할 수 있습니다.
//            Optional<Member> member = memberRepository.findByEmail(username);
//            return member;
        } else if (principal instanceof String) {
            // JWT 토큰에서 추출된 사용자 이름일 수 있습니다.
            username = (String) principal;
            // 사용자 이름을 사용하여 User 엔티티 조회
        }
        return Optional.ofNullable(username);
    }
}
