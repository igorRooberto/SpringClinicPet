package com.example.SpringClinicPet.infra.security;

import com.example.SpringClinicPet.model.User;
import com.example.SpringClinicPet.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserSecurityHelper {

    private final UserRepository userRepository;

    public UserSecurityHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User getAuthenticatedUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

         final String login;

        if(principal instanceof UserDetails userDetails){
            login = userDetails.getUsername();
        } else if(principal instanceof String usernameLogin){
            login = usernameLogin;
        }else{
            throw new RuntimeException("Usuário Errado");
        }

        return  userRepository.findUserByLogin(login)
                .orElseThrow(() -> new RuntimeException("Usuário Não Encontrado"));
    }
}
