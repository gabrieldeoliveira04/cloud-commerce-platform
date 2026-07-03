package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RegisterUserRequest;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User register(RegisterUserRequest request) {
    boolean emailAlreadyExists = userRepository
      .findByEmail(request.email())
      .isPresent();

    if (emailAlreadyExists) {
      throw new EmailAlreadyExistsException(request.email());
    }

    String passwordHash = passwordEncoder.encode(request.password());

    User user = new User(request.email(), passwordHash, "CUSTOMER");

    return userRepository.save(user);
  }

}
