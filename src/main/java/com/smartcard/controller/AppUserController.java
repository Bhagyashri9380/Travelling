package com.smartcard.controller;

import com.smartcard.Dto.AppUserDto;
import com.smartcard.Repository.AppUserRepository;
import com.smartcard.Service.AppUserService;
import com.smartcard.payload.JWTTokenDto;
import com.smartcard.payload.LoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class AppUserController {
    private AppUserService appUserService;
    private AppUserRepository appUserRepository;

    public AppUserController(AppUserService appUserService, AppUserRepository appUserRepository) {
        this.appUserService = appUserService;
        this.appUserRepository = appUserRepository;
    }
    @PostMapping("/createUser")
    public ResponseEntity<?> addAppUser(@RequestBody AppUserDto appUserDto){
        if (appUserRepository.existsByEmail(appUserDto.getEmail())){
            return new ResponseEntity<>("Email exists",HttpStatus.BAD_REQUEST);
        }
        if (appUserRepository.existsByUsername(appUserDto.getUsername())){
            return new ResponseEntity<>("Username exists",HttpStatus.BAD_REQUEST);
        }
        appUserDto.setPassword(BCrypt.hashpw(appUserDto.getPassword(),BCrypt.gensalt()));

        AppUserDto userDto = appUserService.addAppUser(appUserDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
    @PostMapping("/login")
     public ResponseEntity <?> verifyLogin(@RequestBody LoginDto loginDto){
        String token = appUserService.verifyLogin(loginDto);
        if (token !=null){

            JWTTokenDto jwtToken=new JWTTokenDto();
            jwtToken.setType("JWT Token");
            jwtToken.setToken(token);
            return new ResponseEntity<>(jwtToken,HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid token",HttpStatus.OK);
    }
}
