package com.dreamconsumer.consumptionplanner.member.service;

import com.dreamconsumer.consumptionplanner.auth.utils.AuthorityUtils;
import com.dreamconsumer.consumptionplanner.exception.BusinessLogicException;
import com.dreamconsumer.consumptionplanner.exception.ExceptionCode;
import com.dreamconsumer.consumptionplanner.member.domain.Member;
import com.dreamconsumer.consumptionplanner.member.domain.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class MemberDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final MemberRepository memberRepository;
    private final AuthorityUtils authorityUtils;

    public MemberDetailsService(MemberRepository memberRepository, AuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalUser = memberRepository.findByEmail(username);
        Member findMember = optionalUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));


        return new AuthorizedMemberDetails(findMember);
    }

    private final class AuthorizedMemberDetails extends Member implements UserDetails {
        AuthorizedMemberDetails(Member member) {
            // 이거 어디까지 해야 하지? -> 어디서 쓰이는지 확인해봐야겠다
            setMemberId(member.getMemberId());
            setName(member.getName());
            setEmail(member.getEmail());
            setPassword(member.getPassword());
            setRoles(member.getRoles());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRoles());
        }

        @Override
        public String getUsername() { // 여기서 username은 email(credential)
            return this.getEmail();
        }

        // 얘네도 나중에 써야겠다
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
