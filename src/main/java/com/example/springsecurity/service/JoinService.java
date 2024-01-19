package com.example.springsecurity.service;

import com.example.springsecurity.dto.JoinDTO;
import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public void joinProcess(JoinDTO joinDTO){
        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if(isUser){ // username이 중복이면 true;
            return; // true면 return
        }

        UserEntity data = new UserEntity();
        data.setUsername(joinDTO.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        data.setRole("ROLE_ADMIN");
        userRepository.save(data);
    }
}
