package com.dmtryii.usersystem.service.impl;

import com.dmtryii.usersystem.exception.UserAgeException;
import com.dmtryii.usersystem.exception.UserNotFountException;
import com.dmtryii.usersystem.model.User;
import com.dmtryii.usersystem.repository.UserRepository;
import com.dmtryii.usersystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository usersRepository;

    @Value("${service.user.ageLimit}")
    private int ageLimit;

    @Override
    public User getById(Long id) {
        return usersRepository.findById(id).orElseThrow(
                () -> new UserNotFountException("User not fount by id " + id)
        );
    }

    @Override
    public User save(User user) {
        if(!isAgeLimited(user.getBirthDate())) {
            throw new UserAgeException("You are not old enough");
        }
        return usersRepository.save(user);
    }

    @Override
    public User updateFields(Long id, User updatedUser) {

        User currentUser = getById(id);

        Class<User> userClass = User.class;
        Field[] fields = userClass.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);

                if(field.getName().equals("id")) continue;

                Object updatedValue = field.get(updatedUser);
                Object existingValue = field.get(currentUser);

                if (updatedValue != null && !updatedValue.equals(existingValue)) {
                    field.set(currentUser, updatedValue);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error updating user fields", e);
            }
        }
        return save(currentUser);
    }

    @Override
    public User updateAllFields(Long id, User user) {
        User currentUser = getById(id);
        currentUser.setEmail(user.getEmail());
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setBirthDate(user.getBirthDate());
        currentUser.setAddress(user.getAddress());
        currentUser.setPhone(user.getPhone());
        return save(currentUser);
    }

    @Override
    public void delete(Long id) {
        usersRepository.delete(
                getById(id)
        );
    }

    @Override
    public List<User> getAllByDataRange(LocalDate from, LocalDate to) {
        LocalDate defaultFrom = LocalDate.of(1900, 1, 1);
        LocalDate defaultTo = LocalDate.now();

        LocalDate fromDate = Optional.ofNullable(from).orElse(defaultFrom);
        LocalDate toDate = Optional.ofNullable(to).orElse(defaultTo);

        return usersRepository.findByBirthDateBetween(fromDate, toDate);
    }

    private boolean isAgeLimited(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);
        return period.getYears() >= ageLimit;
    }
}
