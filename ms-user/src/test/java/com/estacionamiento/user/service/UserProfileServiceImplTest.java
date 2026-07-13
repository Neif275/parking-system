package com.estacionamiento.user.service;

import com.estacionamiento.user.dto.UserProfileRequestDto;
import com.estacionamiento.user.dto.UserProfileResponseDto;
import com.estacionamiento.user.model.UserProfileModel;
import com.estacionamiento.user.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    private UserProfileModel entity(long id) {
        return new UserProfileModel(id, "juan.perez", "juan@mail.com", "Juan Perez", "+56911111111", "USER");
    }

    private UserProfileRequestDto requestDto(Long id) {
        return new UserProfileRequestDto(id, "juan.perez", "juan@mail.com", "Juan Perez", "+56911111111", "USER");
    }

    @Test
    void findById_existingUser_returnsMappedDto() {
        given(userProfileRepository.findById(1L)).willReturn(Optional.of(entity(1L)));

        UserProfileResponseDto result = userProfileService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUsername()).isEqualTo("juan.perez");
    }

    @Test
    void findById_nonExistingUser_returnsNull() {
        given(userProfileRepository.findById(99L)).willReturn(Optional.empty());

        UserProfileResponseDto result = userProfileService.findById(99L);

        assertThat(result).isNull();
    }

    @Test
    void findAll_returnsAllMappedUsers() {
        given(userProfileRepository.findAll()).willReturn(List.of(entity(1L), entity(2L)));

        List<UserProfileResponseDto> result = userProfileService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(UserProfileResponseDto::getId).containsExactly(1L, 2L);
    }

    @Test
    void create_validRequest_savesAndReturnsDto() {
        given(userProfileRepository.save(any(UserProfileModel.class))).willReturn(entity(1L));

        UserProfileResponseDto result = userProfileService.create(requestDto(null));

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("juan@mail.com");
        verify(userProfileRepository, times(1)).save(any(UserProfileModel.class));
    }

    @Test
    void update_existingRequest_savesAndReturnsUpdatedDto() {
        UserProfileModel updated = entity(1L);
        updated.setFullname("Juan Perez Actualizado");
        given(userProfileRepository.save(any(UserProfileModel.class))).willReturn(updated);

        UserProfileResponseDto result = userProfileService.update(requestDto(1L));

        assertThat(result.getFullname()).isEqualTo("Juan Perez Actualizado");
    }

    @Test
    void deleteById_existingUser_deletesAndReturnsTrue() {
        given(userProfileRepository.existsById(1L)).willReturn(true);

        boolean result = userProfileService.deleteById(1L);

        assertThat(result).isTrue();
        verify(userProfileRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_nonExistingUser_returnsFalseWithoutDeleting() {
        given(userProfileRepository.existsById(99L)).willReturn(false);

        boolean result = userProfileService.deleteById(99L);

        assertThat(result).isFalse();
        verify(userProfileRepository, never()).deleteById(anyLong());
    }
}
