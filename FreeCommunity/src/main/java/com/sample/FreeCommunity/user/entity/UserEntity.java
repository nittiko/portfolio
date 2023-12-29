package com.sample.FreeCommunity.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sample.FreeCommunity.board.boardParents.BoardEntityParent;
import com.sample.FreeCommunity.user.DTO.SignUpFormDTO;
import com.sample.FreeCommunity.user.constant.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Entity
@Data
//restAPI가 아닌 thymeleaf니까
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEntity extends BoardEntityParent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static UserEntity SignUpUser(SignUpFormDTO signUpFormDTO,
                                        Argon2PasswordEncoder passwordEncoder){
        UserEntity user = new UserEntity();
        user.setName(signUpFormDTO.getName());
        user.setEmail(signUpFormDTO.getEmail());
        String password = passwordEncoder.encode(signUpFormDTO.getPassword());
        user.setPassword(password);
        user.setRole(Role.USER);
        return user;
    }
}
