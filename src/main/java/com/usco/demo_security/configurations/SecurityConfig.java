package com.usco.demo_security.configurations;

import com.usco.demo_security.models.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/registro", "/login").permitAll() // Permitir acceso a las páginas de registro y login
                .requestMatchers("/css/**", "/js/**", "/imagenes/**").permitAll() // Permitir acceso a los recursos estáticos
                .requestMatchers("/").hasAnyAuthority("ADMINISTRADOR", "CLIENTE", "ACOMODADOR")
                .requestMatchers("/new/**").hasAnyAuthority("ADMINISTRADOR")
                .requestMatchers("/edit/**").hasAnyAuthority("ADMINISTRADOR", "ACOMODADOR")
                .requestMatchers("/delete/**").hasAnyAuthority("ADMINISTRADOR")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true) // Redirigir tras login exitoso
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout") // Cerrar sesión
                .logoutSuccessUrl("/login?logout") // Redirigir a la página de inicio después de cerrar sesión
                .invalidateHttpSession(true) // Invalidar la sesión
                .deleteCookies("JSESSIONID") // Eliminar las cookies
                .permitAll();

        return http.build();
    }


}

