package com.patientForm.Controller;

import com.patientForm.Entity.Role;
import com.patientForm.Entity.User;
import com.patientForm.Repository.RoleRepository;
import com.patientForm.Repository.UserRepository;
import com.patientForm.Security.JwtTokenProvider;
import com.patientForm.payload.JWTAuthResponse;
import com.patientForm.payload.LoginDto;
import com.patientForm.payload.SignupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    //http://localhost:8080/api/auth/signUp

    @PostMapping("/signUp")
    public ResponseEntity<String > registerUser(@RequestBody SignupDto signupDto){


        if(userRepository.existsByEmail(signupDto.getEmail())){
            return new ResponseEntity<>("email exists",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(userRepository.existsByUsername(signupDto.getUsername())){
            return new ResponseEntity<>("user exists",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user=new User();
        user.setUsername(signupDto.getUsername());
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Role roles = roleRepository.findByRoleName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("user registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authenticate);

        return ResponseEntity.ok(new JWTAuthResponse(token));

    }
}
