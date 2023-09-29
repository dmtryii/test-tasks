package com.dmtryii.usersystem.controller;

import com.dmtryii.usersystem.model.User;
import com.dmtryii.usersystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetByIdValidUser() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        user.setEmail("old@gmail.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(LocalDate.of(2000,1,1));

        when(userService.getById(id)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.getById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());

        // Verify that the UserService's getById method was called with the correct argument
        verify(userService).getById(id);

        // Verify that the ModelMapper's map method was not called (since we didn't stub it)
        verifyNoMoreInteractions(mapper);
    }
}
