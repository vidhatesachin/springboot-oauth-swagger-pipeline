package com.springboot.sample.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.springboot.sample.domain.User;
import com.springboot.sample.repository.UserRepository;
import com.springboot.sample.util.SecretEncoder;

@Component
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecretEncoder cryptoLibrary;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);

        if(user == null) {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
//        user.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//        });
        
        authorities.add(new SimpleGrantedAuthority("STANDARD_USER")); //Only role at the moment

        UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(user.getEmail(),  this.cryptoLibrary.decrypt(user.getPassword()), authorities);

        return userDetails;
    }
}
