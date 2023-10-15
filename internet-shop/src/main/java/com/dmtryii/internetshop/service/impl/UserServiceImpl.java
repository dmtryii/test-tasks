package com.dmtryii.internetshop.service.impl;

import com.dmtryii.internetshop.exception.ResourceNotFoundException;
import com.dmtryii.internetshop.model.User;
import com.dmtryii.internetshop.repository.UserRepository;
import com.dmtryii.internetshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User not found: " + username)
        );
    }
}
