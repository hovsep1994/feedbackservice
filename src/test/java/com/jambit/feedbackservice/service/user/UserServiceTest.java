package com.jambit.feedbackservice.service.user;

import com.jambit.feedbackservice.model.auth.RegisterRequest;
import com.jambit.feedbackservice.repository.UserRepository;
import com.jambit.feedbackservice.repository.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private RegisterRequest registerRequest;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setEmail("user@example.com");
        registerRequest.setPassword("password123");

        user = UserEntity.builder()
                .id(1L)
                .email(registerRequest.getEmail())
                .password("hashedPassword")
                .build();
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        // Mock behavior
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        // Call service method
        UserEntity registeredUser = userService.registerUser(registerRequest);

        // Assertions
        assertNotNull(registeredUser);
        assertEquals(registerRequest.getEmail(), registeredUser.getEmail());
        assertEquals("hashedPassword", registeredUser.getPassword());

        // Verify interactions
        verify(userRepository, times(1)).findByEmail(registerRequest.getEmail());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Mock behavior
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(user));

        // Expect RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.registerUser(registerRequest)
        );

        assertEquals("Email already in use", exception.getMessage());

        // Verify save is never called
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void shouldReturnCurrentUser() {
        // Mock authentication
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user.getEmail());
        SecurityContextHolder.setContext(securityContext);

        // Mock repository behavior
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Call service method
        UserEntity currentUser = userService.getCurrentUser();

        // Assertions
        assertNotNull(currentUser);
        assertEquals(user.getEmail(), currentUser.getEmail());

        // Verify interactions
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUserNotAuthenticated() {
        // Mock authentication
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("unknown@example.com");
        SecurityContextHolder.setContext(securityContext);

        // Mock repository behavior
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        // Expect RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.getCurrentUser()
        );

        assertEquals("User not logged in", exception.getMessage());

        // Verify repository interaction
        verify(userRepository, times(1)).findByEmail("unknown@example.com");
    }
}
