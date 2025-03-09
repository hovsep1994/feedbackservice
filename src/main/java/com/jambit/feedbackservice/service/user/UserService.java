package com.jambit.feedbackservice.service.user;

import com.jambit.feedbackservice.error.EntityNotFoundException;
import com.jambit.feedbackservice.model.auth.RegisterRequest;
import com.jambit.feedbackservice.repository.UserRepository;
import com.jambit.feedbackservice.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity registerUser(RegisterRequest request) {
        Optional<UserEntity> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setLastUpdateTime(LocalDateTime.now());

        return userRepository.save(user);
    }

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

}
