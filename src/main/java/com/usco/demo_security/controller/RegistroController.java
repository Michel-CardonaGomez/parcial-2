package com.usco.demo_security.controller;

import com.usco.demo_security.Repository.RoleRepository;
import com.usco.demo_security.Repository.UserRepository;
import com.usco.demo_security.models.Role;
import com.usco.demo_security.models.User;
import com.usco.demo_security.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("user", new User());
        return "registro";
    }

    @PostMapping
    public String registrarUsuario(@ModelAttribute("user") User user, @RequestParam("password") String password, Model model) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if (!userOptional.isPresent()) {
            model.addAttribute("error", "usuario no registrado.");
            return "registro";
        }
        User userExistente = userOptional.get();
        if (userExistente.isEnabled()) {
            model.addAttribute("error", "Ya has creado tu cuenta.");
            return "registro";
        }
        userExistente.setPassword(passwordEncoder.encode(password)); // Encriptar la contraseña
        userExistente.setEnabled(true); // Cambiar el estado de 'activo' a true
        userRepository.save(userExistente);
        return "redirect:/login";
    }
}
