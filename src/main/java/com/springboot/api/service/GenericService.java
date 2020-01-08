package com.springboot.api.service;

import java.util.List;

import com.springboot.api.domain.User;

/**
 * Created by VDE on 10 Sept 2018.
 */
public interface GenericService {
    User findByUsername(String username);

    List<User> findAllUsers();

}
