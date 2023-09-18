package com.nelumbo.parqueadero.security;

import com.nelumbo.parqueadero.domain.Usuario;
import com.nelumbo.parqueadero.exception.ErrorResponseMessage;
import com.nelumbo.parqueadero.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService{

    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = new Usuario();
        try {
            usuario = userRepository.findByCorreo(correo);
        } catch (UsernameNotFoundException e){
            new ErrorResponseMessage("No existe usuario con el correo "+ correo);
        }
        return new UserDetailsImpl(usuario);
    }
}
