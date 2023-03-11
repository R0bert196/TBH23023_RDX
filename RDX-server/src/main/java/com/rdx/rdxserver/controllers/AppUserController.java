package com.rdx.rdxserver.controllers;

import com.rdx.rdxserver.entities.AppUserEntity;
import com.rdx.rdxserver.models.AuthRequest;
import com.rdx.rdxserver.services.AppUserService;
import com.rdx.rdxserver.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;




@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping(produces = {"application/json"})
    private ResponseEntity<AppUserEntity> getUser(@RequestParam int id) {
        AppUserEntity appUserEntity = appUserService.getUserById(id);
        return  appUserEntity == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(appUserService.getUserById(id));
    }

    @GetMapping(value = "/all", produces = {"application/json"})
    public ResponseEntity<List<AppUserEntity>> getAllUsers() {
        return ResponseEntity.ok(appUserService.findAll());
    }


    @PostMapping(value = "/register", produces = {"application/json"})
    private ResponseEntity<String> saveUser(@RequestBody AppUserEntity tempAppuser) {

        AppUserEntity appUserEntity = appUserService.registerUser(tempAppuser);
        return  appUserEntity == null ? ResponseEntity.status(HttpStatus.CONFLICT).body("Email taken") : ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        // Authenticate the user
        boolean isAuthenticated = appUserService.authenticateUser(request.getEmail(), request.getPassword());
        if (isAuthenticated) {
            // Generate a JWT token for the user
            String token = JwtUtil.generateToken(request.getEmail());
            return ResponseEntity.ok(token);
        } else {
            // Return an error message
            return ResponseEntity.status(HttpStatus.CONFLICT).body("user not found");
        }
    }


}
