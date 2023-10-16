package com.dmtryii.internetshop.test_service;

import com.dmtryii.internetshop.exception.ResourceNotFoundException;
import com.dmtryii.internetshop.model.User;
import com.dmtryii.internetshop.repository.UserRepository;
import com.dmtryii.internetshop.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void GetUserByPrincipal_ReturnUser() {
        String username = "testuser";
        Principal mockPrincipal = () -> username;
        User mockUser = new User();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        User result = userService.getUserByPrincipal(mockPrincipal);

        assertEquals(mockUser, result);
    }

    @Test
    public void GetUserByPrincipal_NotFound() {
        String username = "nonexistentuser";
        Principal mockPrincipal = () -> username;
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByPrincipal(mockPrincipal));
    }
}
