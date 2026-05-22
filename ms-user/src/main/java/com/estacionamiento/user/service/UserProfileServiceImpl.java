package com.estacionamiento.user.service;

import com.estacionamiento.user.dto.UserProfileRequestDto;
import com.estacionamiento.user.dto.UserProfileResponseDto;
import com.estacionamiento.user.model.UserProfileModel;
import com.estacionamiento.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    private UserProfileResponseDto toDto (UserProfileModel entity){
        return  new UserProfileResponseDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getFullname(),
                entity.getPhone(),
                entity.getRole()
        );
    }

    private UserProfileModel toEntity (UserProfileRequestDto dto){
        return new  UserProfileModel(
                dto.getId(),
                dto.getUsername(),
                dto.getEmail(),
                dto.getFullname(),
                dto.getPhone(),
                dto.getRole()
        );
    }

    @Override
    public UserProfileResponseDto findById(long id) {
        return userProfileRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public List<UserProfileResponseDto> findAll() {
        return userProfileRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public UserProfileResponseDto create(UserProfileRequestDto dto){
        return toDto(userProfileRepository.save(toEntity(dto)));
    }

    @Override
    public UserProfileResponseDto update(UserProfileRequestDto dto) {
        return toDto(userProfileRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id){
        if(userProfileRepository.existsById(id)){
            userProfileRepository.deleteById(id);
            return true;
        }
        return false;
    }



}
