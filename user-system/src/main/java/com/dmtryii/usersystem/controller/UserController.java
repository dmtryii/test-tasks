package com.dmtryii.usersystem.controller;

import com.dmtryii.usersystem.dto.request.DataRangeDTO;
import com.dmtryii.usersystem.dto.request.RegisterUserDTO;
import com.dmtryii.usersystem.dto.response.UserDTO;
import com.dmtryii.usersystem.exception.UserNotCreatedException;
import com.dmtryii.usersystem.exception.UserNotUpdateException;
import com.dmtryii.usersystem.exception.handle_exception.BadRequestRecorder;
import com.dmtryii.usersystem.model.User;
import com.dmtryii.usersystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;
    private final BadRequestRecorder errorRecorder;
    private final ModelMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                userService.getById(id)
        );
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid RegisterUserDTO registerUserDTO,
                                     BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String errors = errorRecorder.getRecordedErrors(bindingResult);
            throw new UserNotCreatedException(errors);
        }
        User users = convert(registerUserDTO);
        return ResponseEntity.ok(
                userService.save(users)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateFields(@PathVariable Long id,
                                             @RequestBody @Valid UserDTO userDTO,
                                             BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String errors = errorRecorder.getRecordedErrors(bindingResult);
            throw new UserNotUpdateException(errors);
        }
        User user = convert(userDTO);
        return ResponseEntity.ok(
                userService.updateFields(id, user)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateAllFields(@PathVariable Long id,
                                                @RequestBody @Valid UserDTO userDTO,
                                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String errors = errorRecorder.getRecordedErrors(bindingResult);
            throw new UserNotUpdateException(errors);
        }
        User user = convert(userDTO);
        return ResponseEntity.ok(
                userService.updateAllFields(id, user)
        );
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllByDataRange(@RequestBody @Valid DataRangeDTO dataRangeDTO) {
        List<User> users = userService.getAllByDataRange(
                dataRangeDTO.getFrom(),
                dataRangeDTO.getTo()
        );
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private User convert(RegisterUserDTO registerUserDTO) {
        return mapper.map(registerUserDTO, User.class);
    }

    private User convert(UserDTO userDTO) {
        return mapper.map(userDTO, User.class);
    }
}
