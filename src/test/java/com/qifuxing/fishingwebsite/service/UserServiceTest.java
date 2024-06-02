package com.qifuxing.fishingwebsite.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.qifuxing.fishingwebsite.exception.InvalidInputException;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.model.User;
import com.qifuxing.fishingwebsite.repository.UserRepository;
import com.qifuxing.fishingwebsite.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    @Mock
    private EntityManager entityManager;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.delete(1L);

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.delete(1L);
        });

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(0)).deleteById(1L);
    }

    @Test
    void testDeleteAllUsers_Success() {
        when(userRepository.count()).thenReturn(1L);
        doNothing().when(userRepository).deleteAll();

        userService.deleteAll();

        verify(userRepository, times(1)).count();
        verify(userRepository, times(1)).deleteAll();
    }

    @Test
    void testDeleteAllUsers_EmptyList() {
        when(userRepository.count()).thenReturn(0L);

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.deleteAll();
        });

        verify(userRepository, times(1)).count();
        verify(userRepository, times(0)).deleteAll();
    }

    @Test
    void testResetAutoIdIncrement_Success() {
        when(userRepository.count()).thenReturn(0L);
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery("ALTER TABLE users AUTO_INCREMENT = 1")).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        userService.resetAutoIdIncrement();

        verify(userRepository, times(1)).count();
        verify(entityManager, times(1)).createNativeQuery("ALTER TABLE users AUTO_INCREMENT = 1");
        verify(query, times(1)).executeUpdate();
    }

    @Test
    void testResetAutoIdIncrement_NotEmpty() {
        when(userRepository.count()).thenReturn(1L);

        assertThrows(InvalidInputException.class, () -> {
            userService.resetAutoIdIncrement();
        });

        verify(userRepository, times(1)).count();
        verify(entityManager, times(0)).createNativeQuery("ALTER TABLE users AUTO_INCREMENT = 1");
    }
}