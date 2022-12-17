package com.example.webchatter.util;
import com.example.webchatter.api.dto.UserDto;
import com.example.webchatter.api.request.SignupRequest;
import com.example.webchatter.model.Users;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.MappedSuperclass;

@Mapper
@Component
@RequiredArgsConstructor
@MappedSuperclass
@AllArgsConstructor
public abstract class DtoMapper {
    @Autowired
    protected PasswordEncoder encoder;

    public abstract UserDto convertUser(Users user);

    @Mappings({
        //    @Mapping(target = "login", source = "request.username"),
            @Mapping(target = "password", expression = "java(encoder.encode(request.getPassword()))")
    })
    public abstract Users parseUser(SignupRequest request);
}
