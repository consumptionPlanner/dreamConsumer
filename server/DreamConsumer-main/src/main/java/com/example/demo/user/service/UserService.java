package com.example.demo.user.service;

import com.example.demo.auth.utils.AuthorityUtils;
import com.example.demo.exception.BusinessLogicException;
import com.example.demo.exception.ExceptionCode;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final int TIER_SILVER_MIN = 1;
    private static final int TIER_GOLD_MIN = 3;
    private static final int TIER_DIAMOND_MIN = 5;
    private static final int TIER_EMERALD_MIN = 20;
    private static final int TIER_RUBY_MIN = 50;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityUtils authorityUtils;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityUtils authorityUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
    }

    @Transactional
    public User createUser(User user){
        verifyExistsEmail(user.getEmail());
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        List<String> roles = authorityUtils.createRoles(user.getEmail());
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUser(long userId) {
        return findVerifiedUser(userId);
    }

    @Transactional(readOnly = true)
    public void deleteUser(long userId) {
        User foundUser = findVerifiedUser(userId);
        userRepository.delete(foundUser);
    }

    @Transactional
    public User activateUser(long userId) {
        User foundUser = findVerifiedUser(userId);
        if(foundUser.getUserStatus() != User.UserStatus.USER_SLEEP) {
            throw new BusinessLogicException(ExceptionCode.USER_NOT_SLEEPING);
        }
        foundUser.setUserStatus(User.UserStatus.USER_ACTIVE);
        foundUser.setModifiedAt(LocalDateTime.now());
        return userRepository.save(foundUser);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    private void verifyExistsEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.USER_EXISTS);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    private User findVerifiedUser(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User foundUser = optionalUser.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return foundUser;
    }

    public User updateTier(long userId, int scoreChange) {
        User user = findVerifiedUser(userId);
        int newScore = user.getScore() + scoreChange;
        user.setScore(newScore);
        user.setTier(determineTier(newScore));
        return userRepository.save(user);
    }

    private User.Tier determineTier(int score) {
        if (score >= TIER_RUBY_MIN) {
            return User.Tier.RUBY;
        }
        else if (score >= TIER_EMERALD_MIN) {
            return User.Tier.EMERALD;
        }
        else if (score >= TIER_DIAMOND_MIN) {
            return User.Tier.DIAMOND;
        }
        else if (score >= TIER_GOLD_MIN) {
            return User.Tier.GOLD;
        }
        else if (score >= TIER_SILVER_MIN) {
            return User.Tier.SILVER;
        }
        return User.Tier.BRONZE;
    }
}
