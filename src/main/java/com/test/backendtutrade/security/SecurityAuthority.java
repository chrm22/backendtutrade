package com.test.backendtutrade.security;

import com.test.backendtutrade.entities.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {

    private Rol rol;

    @Override
    public String getAuthority() {
        return rol.getNombre();
    }

}