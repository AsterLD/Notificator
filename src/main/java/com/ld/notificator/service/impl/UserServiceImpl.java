package com.ld.notificator.service.impl;


import com.ld.notificator.dto.UserDTO;
import com.ld.notificator.entity.User;
import com.ld.notificator.exception.UserException;
import com.ld.notificator.repo.UserRepository;
import com.ld.notificator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO findUserById(long id) {
        ModelMapper modelMapper = new ModelMapper();
        User user = userRepository.findById(id).orElseThrow();
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUserById(UserDTO userDTO, long userId) {
        if (userRepository.existsById(userId)) {
            ModelMapper modelMapper = new ModelMapper();
            User editedUser = modelMapper.map(userDTO, User.class);
            editedUser.setId(userId);
            userRepository.save(editedUser);
            return userDTO;
        } else {
            throw new UserException("User with this id not found.");
        }
    }

    @Override
    public void deleteUserById(long id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }
}
