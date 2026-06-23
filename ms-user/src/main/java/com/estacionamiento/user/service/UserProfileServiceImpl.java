package com.estacionamiento.user.service;

import com.estacionamiento.user.dto.UserProfileRequestDto;
import com.estacionamiento.user.dto.UserProfileResponseDto;
import com.estacionamiento.user.model.UserProfileModel;
import com.estacionamiento.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private static final Logger log = LoggerFactory.getLogger(UserProfileServiceImpl.class);

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
        log.info("Buscando usuario con id: {}", id);
        UserProfileResponseDto result = userProfileRepository.findById(id).map(this::toDto).orElse(null);
        if (result == null) log.warn("Usuario con id {} no encontrado", id);
        return result;
    }

    @Override
    public List<UserProfileResponseDto> findAll() {
        log.info("Consultando todos los usuarios");
        return userProfileRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public UserProfileResponseDto create(UserProfileRequestDto dto){
        log.info("Creando usuario con username: {}", dto.getUsername());
        UserProfileResponseDto result = toDto(userProfileRepository.save(toEntity(dto)));
        log.info("Usuario creado con id: {}", result.getId());
        return result;
    }

    @Override
    public UserProfileResponseDto update(UserProfileRequestDto dto) {
        log.info("Actualizando usuario con id: {}", dto.getId());
        return toDto(userProfileRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id){
        if(userProfileRepository.existsById(id)){
            userProfileRepository.deleteById(id);
            log.info("Usuario con id {} eliminado", id);
            return true;
        }
        log.warn("No se pudo eliminar: usuario con id {} no existe", id);
        return false;
    }



}
