package com.dmtryii.usersystem.service;

import com.dmtryii.usersystem.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User getById(Long id);
    User save(User user);
    User updateFields(Long id, User user);
    User updateAllFields(Long id, User user);
    void delete(Long id);
    List<User> getAllByDataRange(LocalDate from, LocalDate to);
}
