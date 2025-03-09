package com.jambit.feedbackservice.service.user;

import com.jambit.feedbackservice.repository.entity.UserEntity;
import com.jambit.feedbackservice.repository.UserRepository;
import com.jambit.feedbackservice.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
