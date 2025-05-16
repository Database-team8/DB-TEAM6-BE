package com.ajoufinder.be.global.api_response.exception;


import com.ajoufinder.be.global.api_response.dto.ApiDto;

public interface BaseErrorCode {

    ApiDto getReasonHttpStatus();
}
