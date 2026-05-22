package com.estacionamiento.user.service;

import com.estacionamiento.user.dto.UserProfileRequestDto;
import com.estacionamiento.user.dto.UserProfileResponseDto;

import java.util.List;

public interface UserProfileService {
    UserProfileResponseDto findById(long id);
    List<UserProfileResponseDto> findAll();
    UserProfileResponseDto create(UserProfileRequestDto userProfileRequestDto);
    UserProfileResponseDto update(UserProfileRequestDto userProfileRequestDto);
    boolean deleteById(Long id);
}
