package com.example.foody.services;

import com.example.foody.constants.Provider;
import com.example.foody.constants.Role;
import com.example.foody.entity.User;
import com.example.foody.repository.IRoleRepository;
import com.example.foody.repository.IUserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.Optional;
@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    public void save(User user)
    {
        userRepository.save(user);
        Long userId = userRepository.getUserIdByUsername(user.getUsername());
        Long roleId = roleRepository.getRoleIdByName("USER");
        if (roleId != 0 && userId !=0)
            userRepository.addRoleToUser(userId, roleId);
    }

    public void saveOauthUser(String email, @NotNull String username) {

        if (userRepository.findByUsername(username) != null) return;
        var user = new User();
        if (user.getName() == null) {
            String randomName = "User-" + UUID.randomUUID().toString();
            user.setName(randomName);
        }
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(username));
        user.setProvider(Provider.GOOGLE.value);
        user.getRoles().add(roleRepository.findRoleById(Role.USER.value));
        userRepository.save(user);
    }
    public void setDefaultRole(String username){
        userRepository.findByUsername(username).getRoles()
                .add(roleRepository
                        .findRoleById(Role.USER.value));
    }
}