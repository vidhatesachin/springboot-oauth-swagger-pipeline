package com.springboot.sample.service;

import java.util.List;

import com.springboot.sample.domain.User;

/**
 * Created by VDE on 10 Sept 2018.
 */
public interface GenericService {
    User findByUsername(String username);

    List<User> findAllUsers();

}
