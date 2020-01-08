package com.medinosys.api.service;

import java.util.List;

import com.medinosys.api.domain.User;

/**
 * Created by VDE on 10 Sept 2018.
 */
public interface GenericService {
    User findByUsername(String username);

    List<User> findAllUsers();

}
