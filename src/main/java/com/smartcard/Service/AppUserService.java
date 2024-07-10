package com.smartcard.Service;

import com.smartcard.Dto.AppUserDto;
import com.smartcard.Repository.AppUserRepository;
import com.smartcard.entity.AppUser;
import com.smartcard.payload.LoginDto;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService {
    private AppUserRepository appUserRepository;
    private JwtService jwtService;

    public AppUserService(AppUserRepository appUserRepository, JwtService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    public AppUserDto addAppUser(AppUserDto appUserDto) {
        AppUser entity = new AppUser();
        entity.setName(appUserDto.getName());
        entity.setUsername(appUserDto.getUsername());
        entity.setEmail(appUserDto.getEmail());
        entity.setPassword(appUserDto.getPassword());
        entity.setRole(appUserDto.getRole());
        AppUser saved = appUserRepository.save(entity);
        AppUserDto dto =new AppUserDto();
        dto.setId(saved.getId());
        dto.setName(saved.getName());
        dto.setUsername(saved.getUsername());
        dto.setEmail(saved.getEmail());
        dto.setRole(saved.getRole());
        return dto;

    }


    public String verifyLogin(LoginDto loginDto) {
        Optional<AppUser> opappuser = appUserRepository.findByUsername(loginDto.getUsername());
        if (opappuser.isPresent()){
            AppUser appUser = opappuser.get();
            if (BCrypt.checkpw(loginDto.getPassword(),appUser.getPassword())){
                String token = jwtService.generateToken(loginDto);
                return token;
            }
        }
        return null;
    }

}
