package com.example.nexusconge.controllers;


import com.example.nexusconge.DTO.JwtResponse;
import com.example.nexusconge.DTO.LoginRequest;
import com.example.nexusconge.entities.ERole;
import com.example.nexusconge.entities.Role;
import com.example.nexusconge.entities.user;
import com.example.nexusconge.repositories.roleRepo;
import com.example.nexusconge.repositories.userRepo;
import com.example.nexusconge.security.JwtUtils;
import com.example.nexusconge.services.UserDetailsImpl;
import com.example.nexusconge.services.userService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
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
   // @Autowired
   // PasswordEncoder encoder;
   @PostMapping(value="/signUp")
   @ResponseBody
   public ResponseEntity<String> signUpV3(@RequestParam String username,
                                          @RequestParam String email,
                                          @RequestParam String password,
                                          @RequestParam List<String> roleType
   ) throws Exception {


        System.out.println(email);
       String user="{\"username\": \""+username+"\",   \"email\": \""+email+"\", \"password\": \""+password+"\" }";
       System.out.println("hneeeeeeeeeeeeeeee"+user);
       ObjectMapper objectMapper = new ObjectMapper();
       com.example.nexusconge.entities.user userDTO = objectMapper.readValue(user, user.class);
       System.out.println(userDTO.getUsername());
       // Validate input attributes

       if (userDTO.getUsername() == null || userDTO.getUsername().matches(".*\\d.*")) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Lastname");
       }


       if (userDTO.getEmail() == null || !userDTO.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Email Address");
       }

       Set<Role> roles = new HashSet<>();
       System.out.println("tagtag"+roleType);
       roleType.forEach(role -> {
           switch (role){
               case "admin":
                   Role rhrole= roleRepo.findByNameRole(ERole.RH).orElse(null);
                   roles.add(rhrole);
                   break;
               case "manager":
                   Role nplus1role= roleRepo.findByNameRole(ERole.NPlus1).orElse(null);
                   roles.add(nplus1role);
                   break;
               case "employee":
                   Role EMPLOYErole= roleRepo.findByNameRole(ERole.EMPLOYE).orElse(null);
                   roles.add(EMPLOYErole);
                   break;
               default:
                   Role defaultrole= roleRepo.findByNameRole(ERole.EMPLOYE).orElse(null);
                   roles.add(defaultrole);

           }
       });
      // Role role= roleRepo.findByNameRole(roleType).orElse(null);
     //  roles.add(role);
       // Create User object
       user u = new user();
       u.setEmail(userDTO.getEmail());
       u.setMot_de_passe(encoder.encode(userDTO.getMot_de_passe()));
       u.setUsername(userDTO.getUsername());
       u.setRoles(roles);

         // Add user and assign role
       if (userService.signup(u)){
           return ResponseEntity.status(HttpStatus.CREATED).body("User created");
       }else{
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email or Username exists");
       }
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
                    userDetails.getEmail()
                   // roles
            ));
        } catch (AuthenticationException e) {
            // Authentication failed, increment failed login attempt count
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");}
            }

        // Authentication failed, return error response

}
