package com.ajoufinder.be.user.dto;

public record UserLoginRequest(
        String email,
        String password
){
}
