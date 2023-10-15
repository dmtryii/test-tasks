package com.dmtryii.internetshop.service;

import com.dmtryii.internetshop.model.User;

import java.security.Principal;

public interface UserService {
    User getUserByPrincipal(Principal principal);
}
