package com.project.shopapp.services;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private UserDetailRepository userDetailRepository;
    private RoleRepository roleRepository;
    @Override
    public User createUser(UserDTO userDTO) {
        if(userDetailRepository.exitstsByPhoneNumber(userDTO.getPhoneNumber())){
            throw  new DataIntegrityViolationException("Phone number already");
        }

        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAcountId())
                .googleAccountId(userDTO.getGoogleAcountId())
                .build();
        try {
            Role role = roleRepository.findById(userDTO.getRoleId())
                    .orElseThrow( () ->new DataNotFoundException("Role not found"));
            newUser.setRoleId(role);
            if (userDTO.getFacebookAcountId().equals("0") && userDTO.getGoogleAcountId().equals("0") ) {
                String passWord = userDTO.getPassword();
            }
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

        return userDetailRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String passWord) {
        // đoạn này liên quan nhiều đến security sẽ làm trong pần security
        return "";
    }
}
