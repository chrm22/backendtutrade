package com.test.backendtutrade.service;

import com.test.backendtutrade.entities.Usuario;
import com.test.backendtutrade.repository.UsuarioRepository;
import com.test.backendtutrade.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsernameWithRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (usuario !=null) {
            return new SecurityUser(usuario);
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}