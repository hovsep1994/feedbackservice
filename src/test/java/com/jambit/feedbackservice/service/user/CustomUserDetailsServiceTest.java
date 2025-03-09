package com.jambit.feedbackservice.service.user;

import com.jambit.feedbackservice.repository.entity.UserEntity;
import com.jambit.feedbackservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .id(1L)
                .email("user@example.com")
                .password("$2a$10$hashedpassword") // Fake hashed password
                .build();
    }

    @Test
    void shouldReturnUserDetailsWhenUserExists() {
        // Mock repository behavior
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        // Call service method
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("user@example.com");

        // Assertions
        assertNotNull(userDetails);
        assertEquals("user@example.com", userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());

        // Verify repository interaction
        verify(userRepository, times(1)).findByEmail("user@example.com");
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Mock repository behavior to return empty
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        // Expect UsernameNotFoundException
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                customUserDetailsService.loadUserByUsername("notfound@example.com")
        );

        assertEquals("User not found", exception.getMessage());

        // Verify repository interaction
        verify(userRepository, times(1)).findByEmail("notfound@example.com");
    }
}
