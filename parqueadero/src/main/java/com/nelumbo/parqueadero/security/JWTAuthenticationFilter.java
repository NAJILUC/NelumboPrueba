package com.nelumbo.parqueadero.security;

import com.nelumbo.parqueadero.exception.CustomErrorResponse;
import com.nelumbo.parqueadero.exception.ErrorResponseMessage;
import com.nelumbo.parqueadero.exception.NotFoundException;
import com.nelumbo.parqueadero.exception.UsuarioDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthCredentials authCredentials = new AuthCredentials();
        try {
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
            if (authCredentials.getCorreo() == null || authCredentials.getClave() == null || authCredentials.getCorreo().isEmpty() || authCredentials.getClave().isEmpty()) {
                CustomErrorResponse errorResponse = new CustomErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.toString(),
                        "Ingrese un correo y clave"
                );
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setContentType("application/json");
                response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
                return null;
            }
        } catch (IOException e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.toString(),
                    "Ingrese un correo y clave"
            );
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            try {
                response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return null;
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authCredentials.getCorreo(),
                authCredentials.getClave(),
                Collections.emptyList()
        );
        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException{

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Object[] authori = userDetails.getAuthorities().toArray();
        String token = TokenUtils.createToken(userDetails.getNombre(),userDetails.getUsername(),authori[0].toString(),userDetails.getId());
        response.addHeader("Authorization","Bearer "+token);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        response.getWriter().flush();
        super.successfulAuthentication(request,response,chain,authentication);
    }
}
