package com.infoshareacademy.responsibledrinkersweb.service;

import com.infoshareacademy.responsibledrinkersweb.domain.User;
import com.infoshareacademy.responsibledrinkersweb.dto.CreateUserDto;
import com.infoshareacademy.responsibledrinkersweb.dto.UserDto;
import com.infoshareacademy.responsibledrinkersweb.entity.UserDAO;
import com.infoshareacademy.responsibledrinkersweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public UserDto addUser(CreateUserDto createUserDto) {
        UserDAO user = new UserDAO(createUserDto.getUserName(), createUserDto.getGender(), createUserDto.getEmail(), createUserDto.getPassword(), createUserDto.getDateOfBirth(), createUserDto.getRole());
        userRepository.save(user);
        return modelMapper.map(user,UserDto.class);
    }

}
