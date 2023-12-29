package com.sample.FreeCommunity.user.service;

import com.sample.FreeCommunity.user.entity.UserEntity;
import com.sample.FreeCommunity.user.repository.UserRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional //트랜잭션 단위의 로직 수행 실패시 롤백
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepositry userRepository;

    public UserEntity saveUser(UserEntity user) {
        validateDuplicateUser(user);
        return userRepository.save(user);
    }

    private void validateDuplicateUser(UserEntity user) {
        UserEntity findUser = userRepository.findByEmail(user.getEmail());
        if (findUser != null) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }

    }

    public boolean validateDuplicateEmail(String email) {
        UserEntity findUser = userRepository.findByEmail(email);
        if (findUser != null) {
            return false;
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }



}
