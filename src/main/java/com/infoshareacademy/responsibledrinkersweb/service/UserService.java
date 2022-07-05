package com.infoshareacademy.responsibledrinkersweb.service;

import com.infoshareacademy.responsibledrinkersweb.dto.CreateUserDto;
import com.infoshareacademy.responsibledrinkersweb.dto.UserDto;
import com.infoshareacademy.responsibledrinkersweb.entity.UserDAO;
import com.infoshareacademy.responsibledrinkersweb.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDto addUser(CreateUserDto createUserDto) {
        UserDAO user = new UserDAO(createUserDto.getUserName(), createUserDto.getGender(), createUserDto.getEmail(), createUserDto.getPassword(), createUserDto.getDateOfBirth(), createUserDto.getRole());
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto findByUserName(String username) {
        UserDAO user = userRepository.findByUserName(username);
        return modelMapper.map(user, UserDto.class);

    }

    public List<UserDto> findAll() {
        List<UserDAO> users = userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
    }

    public void changeUserIsActiveFlag(UUID id) {
        UserDAO user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user.isActive()) {
            user.setActive(false);
        } else {
            user.setActive(true);
        }
        userRepository.save(user);
    }

    public void acceptUser(UUID id) {
        UserDAO user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setRole("USER");
        userRepository.save(user);
    }
}
