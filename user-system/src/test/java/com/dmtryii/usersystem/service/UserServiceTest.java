package com.dmtryii.usersystem.service;

import com.dmtryii.usersystem.exception.UserAgeException;
import com.dmtryii.usersystem.exception.UserNotFountException;
import com.dmtryii.usersystem.model.User;
import com.dmtryii.usersystem.repository.UserRepository;
import com.dmtryii.usersystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.ReflectionUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Value("${service.user.ageLimit}")
    private int ageLimitTest;

    @Test
    public void testGetById_ForExistingUser_UserFound() {
        Long id = 1L;
        User user = User.builder()
                .id(id)
                .build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getById(id);
        assertNotNull(result);
    }

    @Test
    public void testGetById_ForNotExistingUser_UserNotFoundThrows() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenThrow(UserNotFountException.class);
        assertThrows(UserNotFountException.class, () -> userService.getById(userId));
    }

    @Test
    public void testSave_ValidAge_UserNotNull() throws NoSuchFieldException {
        LocalDate validBirthDate = LocalDate.now().minusYears(ageLimitTest);

        User user = User.builder()
                .id(1L)
                .birthDate(validBirthDate)
                .build();

        setAgeLimitUserService();
        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.save(user);
        assertNotNull(savedUser);
    }

    @Test
    public void testSave_InvalidAge_UserAgeException() throws NoSuchFieldException {
        LocalDate invalidBirthDate = LocalDate.now().minusYears(ageLimitTest-1);

        User user = User.builder()
                .id(1L)
                .birthDate(invalidBirthDate)
                .build();

        setAgeLimitUserService();
        assertThrows(UserAgeException.class, () -> userService.save(user));
    }

    @Test
    public void updateAllFields_ValidUserAndId_UpdatedUser() throws NoSuchFieldException {
        Long id = 1L;

        User existingUser = User.builder()
                .id(id)
                .email("old@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(2000,1,1))
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));

        User newUser  = User.builder()
                .email("new@gmail.com")
                .firstName("Jane")
                .lastName("Smith")
                .birthDate(LocalDate.of(2000,1,1))
                .build();

        setAgeLimitUserService();

        when(userRepository.save(existingUser)).thenReturn(existingUser);
        User updatedResult = userService.updateAllFields(id, newUser);

        assertEquals(updatedResult.getEmail(), newUser.getEmail());
        assertEquals(updatedResult.getFirstName(), newUser.getFirstName());
        assertEquals(updatedResult.getLastName(), newUser.getLastName());
    }

    @Test
    public void updateFields_ValidUserAndId_UpdatedUser() throws NoSuchFieldException {
        Long id = 1L;

        User existingUser = User.builder()
                .id(id)
                .email("doe@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(2000,1,1))
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));

        User newUser  = User.builder()
                .firstName("Jane")
                .lastName("Smith")
                .phone("+380989788456")
                .birthDate(LocalDate.of(2001,10,1))
                .build();

        setAgeLimitUserService();

        when(userRepository.save(existingUser)).thenReturn(existingUser);
        User updatedResult = userService.updateFields(id, newUser);

        assertEquals(updatedResult.getEmail(), existingUser.getEmail());
        assertEquals(updatedResult.getFirstName(), newUser.getFirstName());
        assertEquals(updatedResult.getLastName(), newUser.getLastName());
        assertEquals(updatedResult.getPhone(), newUser.getPhone());
        assertEquals(updatedResult.getBirthDate(), newUser.getBirthDate());
    }

    @Test
    public void GetAllByDataRange_ForThreeUsers_ReturnsTwoUsers() {
        LocalDate fromDate = LocalDate.of(1990, 1, 1);
        LocalDate toDate = LocalDate.of(2000, 12, 31);

        User user1 = User.builder()
                .id(1L)
                .birthDate(LocalDate.of(1995, 5, 10))
                .build();

        User user2 = User.builder()
                .id(2L)
                .birthDate(LocalDate.of(1990, 2, 15))
                .build();

        User user3 = User.builder()
                .id(3L)
                .birthDate(LocalDate.of(2005, 8, 20))
                .build();

        List<User> usersInRange = Arrays.asList(user1, user2);

        when(userRepository.findByBirthDateBetween(fromDate, toDate)).thenReturn(usersInRange);

        List<User> result = userService.getAllByDataRange(fromDate, toDate);

        verify(userRepository).findByBirthDateBetween(fromDate, toDate);

        assertEquals(usersInRange.size(), result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
        assertFalse(result.contains(user3));
    }

    @Test
    public void testDelete_ForExistingUser_Delete() {
        Long id = 1L;
        User user = User.builder()
                .id(id)
                .build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userService.delete(id);
        verify(userRepository, times(1)).delete(user);
    }

    private void setAgeLimitUserService() throws NoSuchFieldException {
        ReflectionUtils.setField(
                userService.getClass().getDeclaredField("ageLimit"),
                userService,
                ageLimitTest
        );
    }
}
