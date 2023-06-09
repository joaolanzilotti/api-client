package com.corporation.apiclient.services;

import com.corporation.apiclient.dto.security.AccountCredentialsDTO;
import com.corporation.apiclient.dto.security.TokenDTO;
import com.corporation.apiclient.repositories.UserRepository;
import com.corporation.apiclient.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthServices {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsDTO data) {
        try {
            var username = data.getEmail();
            var password = data.getPassword();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            var user = repository.findByEmail(username);


            var tokenResponse = new TokenDTO();
            if (user != null) {
                tokenResponse = tokenProvider.createAccessToken(username, user.get().getRoles());
            } else {
                throw new UsernameNotFoundException("Username " + username + " not found!");
            }
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken) {
        var user = repository.findByEmail(username);

        var tokenResponse = new TokenDTO();
        if (user.get() != null) {
            tokenResponse = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
        return ResponseEntity.ok(tokenResponse);
    }
}

