package com.example.mercadinho.service;

import com.example.mercadinho.repository.UserRepository;
import com.example.mercadinho.repository.model.UserEntity;
import com.example.mercadinho.security.UserAuthenticated;
import com.example.mercadinho.service.cookies.CookieService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserFacade, UserDetailsService {

    final UserRepository userRepository;
    CookieService cookie;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity createUser(UserEntity request){
        return userRepository.save(request);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        if ("admin".equalsIgnoreCase(username)) {
//            return userRepository.findByNameAndPassword(username, "$2a$10$muh0PcyvZTvha2xYJSpT9eErpjxk6UK1D.KUlvZHNbKrZ7ZOurERS").map(UserAuthenticated::new)
//                .orElseThrow(
//                        () -> new UsernameNotFoundException("User Not Found with username: " + username));
//        } else {
//            // Throw this exception if the user was not found
//            throw new UsernameNotFoundException("User not found");
//        }
        return userRepository.findByName(name)
                .map(UserAuthenticated::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found with username: " + name));
    }

}
