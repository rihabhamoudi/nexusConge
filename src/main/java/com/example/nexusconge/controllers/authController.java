package com.example.nexusconge.controllers;
import  com.example.nexusconge.config.MailConfiguration;

import com.example.nexusconge.DTO.JwtResponse;
import com.example.nexusconge.DTO.LoginRequest;
import com.example.nexusconge.entities.ERole;
import com.example.nexusconge.entities.Role;
import com.example.nexusconge.entities.user;
import com.example.nexusconge.security.JwtUtils;
import com.example.nexusconge.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

//@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class authController {
    @Autowired
    com.example.nexusconge.repositories.userRepo userRepo;
    @Autowired
    com.example.nexusconge.repositories.roleRepo roleRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    com.example.nexusconge.services.userService userService;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private JavaMailSender javaMailSender;

    // @Autowired
   // PasswordEncoder encoder;
    @PostMapping("/signUp")
    @ResponseBody
    public ResponseEntity<String> signUpV3(@Valid @RequestBody user userDTO) {
        try {
            // Validate the userDTO (you can add more validation annotations to your user class)
            // ... (validation and other code)
            String matricule = generateUniqueMatricule();
            userDTO.setMatricule(Long.valueOf(matricule));

            // Récupération des rôles à partir du corps de la requête
            Set<Role> roles = userDTO.getRoles();
            System.out.println("tagtag" + roles);

            // Construction de l'ensemble de rôles
            Set<Role> userRoles = new HashSet<>();
            roles.forEach(role -> {
                if ("RH".equals(role)) {
                    userRoles.add(roleRepo.findByNameRole(ERole.RH).orElse(null));
                } else if ("NPlus1".equals(role)) {
                    userRoles.add(roleRepo.findByNameRole(ERole.NPlus1).orElse(null));
                } else if ("EMPLOYE".equals(role)) {
                    userRoles.add(roleRepo.findByNameRole(ERole.EMPLOYE).orElse(null));
                } else {
                    userRoles.add(roleRepo.findByNameRole(ERole.EMPLOYE).orElse(null));
                }
            });

            // Création de l'utilisateur
            user u = new user();
            u.setEmail(userDTO.getEmail());
            u.setPassword(encoder.encode(userDTO.getPassword()));
            u.setUsername(userDTO.getUsername());
            u.setMatricule(userDTO.getMatricule());
            u.setActivite(userDTO.getActivite());
            u.setRoles(userRoles);

            // Ajout de l'utilisateur et attribution du rôle
            if (userService.signup(u)) {
                // Send email to the registered user
                sendRegistrationEmail(u);

                return ResponseEntity.status(HttpStatus.CREATED).body("User created");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email or Username exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during user registration: " + e.getMessage());
        }
    }
    private String generateUniqueMatricule() {
        // Logic to generate a unique matricule (you can use database sequences, timestamps, etc.)
        // For simplicity, let's generate a random 4-digit matricule
        Random random = new Random();
        int matriculeNumber = random.nextInt(9000) + 1000; // Generates a random 4-digit number
        return String.valueOf(matriculeNumber);
    }
        private void sendRegistrationEmail(user user) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Registration Successful");
            mailMessage.setText("Dear " + user.getUsername() + ",\n\n"
                    + "Congratulations! You have successfully registered.\n"
                    + "Matricule: " + user.getMatricule() + "\n"
                    + "Password: [Your message here with the password]\n\n"
                    + "Thank you for using our application.");


          try {  javaMailSender.send(mailMessage);}
          catch (Exception e) {
              e.printStackTrace();}
        }
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUserV2(@Valid @RequestBody LoginRequest loginRequest) throws IOException {

        user user = userRepo.findByUsername(loginRequest.getUsername()).orElse(null);
        System.out.println(user.getRoles());



        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
           // List<String> roles = userDetails.getAuthorities().stream()
                  //  .map(item -> item.getAuthority())
                //    .collect(Collectors.toList());

            // Reset the failed login attempt count if authentication succeeds


            return ResponseEntity.ok(new JwtResponse(jwt,
                    Math.toIntExact(userDetails.getId()),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                   userDetails.getMatricule(),
                   userDetails.getActivite()
                   // roles
            ));
        } catch (AuthenticationException e) { e.printStackTrace();
            // Authentication failed, increment failed login attempt count
           // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");}
            }

        // Authentication failed, return error response

        return null;
    }}
