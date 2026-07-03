package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterUserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
private final JwtService jwtService;

public UserService(
UserRepository userRepository,
PasswordEncoder passwordEncoder,
JwtService jwtService
) {
this.userRepository = userRepository;
this.passwordEncoder = passwordEncoder;
this.jwtService = jwtService;
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

public AuthResponse login(LoginRequest request) {
User user = userRepository
.findByEmail(request.email())
.orElseThrow(InvalidCredentialsException::new);

if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
  throw new InvalidCredentialsException();
}

String token = jwtService.generateToken(user.getEmail(), user.getRole());

UserResponse userResponse = new UserResponse(
  user.getId(),
  user.getEmail(),
  user.getRole(),
  user.getEmailVerified()
);

return new AuthResponse(
  token,
  "Bearer",
  3600L,
  userResponse
);


}
}
