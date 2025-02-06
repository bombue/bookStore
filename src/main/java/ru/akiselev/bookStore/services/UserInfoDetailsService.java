package ru.akiselev.bookStore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.akiselev.bookStore.models.UserInfo;
import ru.akiselev.bookStore.repositories.UserInfoRepository;
import ru.akiselev.bookStore.security.UserInfoDetails;

import java.util.Optional;

@Service
public class UserInfoDetailsService implements UserDetailsService {
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoDetailsService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByUsername(username);
        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new UserInfoDetails(userInfo.get());
    }
}
