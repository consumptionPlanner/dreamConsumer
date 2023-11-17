package com.dreamconsumer.consumptionplanner.member.service;

import com.dreamconsumer.consumptionplanner.auth.utils.AuthorityUtils;
import com.dreamconsumer.consumptionplanner.exception.BusinessLogicException;
import com.dreamconsumer.consumptionplanner.exception.ExceptionCode;
import com.dreamconsumer.consumptionplanner.member.domain.Member;
import com.dreamconsumer.consumptionplanner.member.domain.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private static final int TIER_SILVER_MIN = 1;
    private static final int TIER_GOLD_MIN = 3;
    private static final int TIER_DIAMOND_MIN = 5;
    private static final int TIER_EMERALD_MIN = 20;
    private static final int TIER_RUBY_MIN = 50;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityUtils authorityUtils;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, AuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
    }

    @Transactional
    public Member createUser(Member member){
        verifyExistsEmail(member.getEmail());
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);
        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member getUser(long userId) {
        return findVerifiedUser(userId);
    }

    @Transactional(readOnly = true)
    public void deleteUser(long userId) {
        Member foundMember = findVerifiedUser(userId);
        memberRepository.delete(foundMember);
    }

    @Transactional
    public Member activateUser(long userId) {
        Member foundMember = findVerifiedUser(userId);
        if(foundMember.getUserStatus() != Member.UserStatus.USER_SLEEP) {
            throw new BusinessLogicException(ExceptionCode.USER_NOT_SLEEPING);
        }
        foundMember.setUserStatus(Member.UserStatus.USER_ACTIVE);
        foundMember.setModifiedAt(LocalDateTime.now());
        return memberRepository.save(foundMember);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    private void verifyExistsEmail(String email) {
        Optional<Member> user = memberRepository.findByEmail(email);
        if(user.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.USER_EXISTS);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    private Member findVerifiedUser(long userId) {
        Optional<Member> optionalUser = memberRepository.findById(userId);
        Member foundMember = optionalUser.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return foundMember;
    }

    public Member updateTier(long userId, int scoreChange) {
        Member member = findVerifiedUser(userId);
        int newScore = member.getScore() + scoreChange;
        member.setScore(newScore);
        member.setTier(determineTier(newScore));
        return memberRepository.save(member);
    }

    private Member.Tier determineTier(int score) {
        if (score >= TIER_RUBY_MIN) {
            return Member.Tier.RUBY;
        }
        else if (score >= TIER_EMERALD_MIN) {
            return Member.Tier.EMERALD;
        }
        else if (score >= TIER_DIAMOND_MIN) {
            return Member.Tier.DIAMOND;
        }
        else if (score >= TIER_GOLD_MIN) {
            return Member.Tier.GOLD;
        }
        else if (score >= TIER_SILVER_MIN) {
            return Member.Tier.SILVER;
        }
        return Member.Tier.BRONZE;
    }
}
