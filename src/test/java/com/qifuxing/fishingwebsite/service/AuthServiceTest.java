package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.model.User;
import com.qifuxing.fishingwebsite.repository.UserRepository;
import com.qifuxing.fishingwebsite.specificDTO.LoginDTO;
import com.qifuxing.fishingwebsite.specificDTO.UserRegistrationDTO;
import com.qifuxing.fishingwebsite.exception.UsernameAlreadyExistsException;
import com.qifuxing.fishingwebsite.exception.LoginFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/*
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private AuthService authService;

    private User user;
    private UserRegistrationDTO userRegistrationDTO;
    private LoginDTO loginDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setEmail("testuser@example.com");

        userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername("testuser");
        userRegistrationDTO.setPassword("testpassword");
        userRegistrationDTO.setEmail("testuser@example.com");

        loginDTO = new LoginDTO();
        loginDTO.setUsername("testuser");
        loginDTO.setPassword("testpassword");
    }

    @Test
    void testRegisterUser() {
        when(userRepository.findByUsername(userRegistrationDTO.getUsername())).thenReturn(null);
        when(passwordEncoder.encode(userRegistrationDTO.getPassword())).thenReturn("encodedpassword");

        authService.registerUser(userRegistrationDTO);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_UsernameAlreadyExists() {
        when(userRepository.findByUsername(userRegistrationDTO.getUsername())).thenReturn(user);

        assertThrows(UsernameAlreadyExistsException.class, () -> authService.registerUser(userRegistrationDTO));
    }

    @Test
    void testFindByUsername() {
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User foundUser = authService.findByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    void testAuthenticateUser() {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername("testuser")
                .password("encodedpassword")
                .authorities("USER")
                .build();

        when(customUserDetailsService.loadUserByUsername(loginDTO.getUsername())).thenReturn(userDetails);
        when(passwordEncoder.matches(loginDTO.getPassword(), userDetails.getPassword())).thenReturn(true);

        UserDetails authenticatedUser = authService.authenticateUser(loginDTO);

        assertNotNull(authenticatedUser);
        assertEquals("testuser", authenticatedUser.getUsername());
    }

    @Test
    void testAuthenticateUser_InvalidCredentials() {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername("testuser")
                .password("encodedpassword")
                .authorities("USER")
                .build();

        when(customUserDetailsService.loadUserByUsername(loginDTO.getUsername())).thenReturn(userDetails);
        when(passwordEncoder.matches(loginDTO.getPassword(), userDetails.getPassword())).thenReturn(false);

        //assertThrows(LoginFailedException.class, () -> authService.authenticateUser(loginDTO));
    }


 */