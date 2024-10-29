package com.usco.demo_security.services;

import com.usco.demo_security.Repository.RoleRepository;
import com.usco.demo_security.Repository.UserRepository;
import com.usco.demo_security.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

//
//    public List<User> user crearUsuarios {
//        List<User> users = new ArrayList<User>();
//
//        User user1
//    }
}
