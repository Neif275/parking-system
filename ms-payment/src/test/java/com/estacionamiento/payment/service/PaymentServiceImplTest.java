package com.estacionamiento.payment.service;

import com.estacionamiento.payment.client.EntryExitClient;
import com.estacionamiento.payment.client.dto.EntryExitResponseDto;
import com.estacionamiento.payment.dto.PaymentRequestDto;
import com.estacionamiento.payment.dto.PaymentResponseDto;
import com.estacionamiento.payment.model.PaymentModel;
import com.estacionamiento.payment.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private EntryExitClient entryExitClient;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private PaymentModel entity(long id) {
        return new PaymentModel(id, 1L, new BigDecimal("1500.00"), "TARJETA", LocalDateTime.now(), "COMPLETED");
    }

    private PaymentRequestDto requestDto(Long id) {
        return new PaymentRequestDto(id, 1L, new BigDecimal("1500.00"), "TARJETA", LocalDateTime.now(), "COMPLETED");
    }

    private EntryExitResponseDto entryExit() {
        return new EntryExitResponseDto(1L, "ABCD12", LocalDateTime.now(), null, 1L, 1L, "ACTIVE");
    }

    @Test
    void findById_existingPayment_returnsMappedDto() {
        given(paymentRepository.findById(1L)).willReturn(Optional.of(entity(1L)));

        PaymentResponseDto result = paymentService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getPaymentMethod()).isEqualTo("TARJETA");
    }

    @Test
    void findById_nonExistingPayment_returnsNull() {
        given(paymentRepository.findById(99L)).willReturn(Optional.empty());

        assertThat(paymentService.findById(99L)).isNull();
    }

    @Test
    void findAll_returnsAllMappedPayments() {
        given(paymentRepository.findAll()).willReturn(List.of(entity(1L), entity(2L)));

        assertThat(paymentService.findAll()).hasSize(2);
    }

    @Test
    void findByEntryExitId_returnsMatchingPayments() {
        given(paymentRepository.findByEntryExitId(1L)).willReturn(List.of(entity(1L)));

        assertThat(paymentService.findByEntryExitId(1L)).hasSize(1);
    }

    @Test
    void findByStatus_returnsMatchingPayments() {
        given(paymentRepository.findByStatus("COMPLETED")).willReturn(List.of(entity(1L)));

        assertThat(paymentService.findByStatus("COMPLETED")).hasSize(1);
    }

    @Test
    void findByEntryExitIdAndStatus_existingPayment_returnsMappedDto() {
        given(paymentRepository.findByEntryExitIdAndStatus(1L, "COMPLETED")).willReturn(Optional.of(entity(1L)));

        assertThat(paymentService.findByEntryExitIdAndStatus(1L, "COMPLETED")).isNotNull();
    }

    @Test
    void findByEntryExitIdAndStatus_noMatch_returnsNull() {
        given(paymentRepository.findByEntryExitIdAndStatus(99L, "COMPLETED")).willReturn(Optional.empty());

        assertThat(paymentService.findByEntryExitIdAndStatus(99L, "COMPLETED")).isNull();
    }

    @Test
    void create_existingEntryExit_savesAndReturnsDto() {
        given(entryExitClient.getEntryExitById(1L)).willReturn(entryExit());
        given(paymentRepository.save(any(PaymentModel.class))).willReturn(entity(1L));

        PaymentResponseDto result = paymentService.create(requestDto(null));

        assertThat(result.getId()).isEqualTo(1L);
        verify(paymentRepository, times(1)).save(any(PaymentModel.class));
    }

    @Test
    void create_entryExitNotFound_throwsIllegalArgumentException() {
        given(entryExitClient.getEntryExitById(1L)).willReturn(null);

        assertThatThrownBy(() -> paymentService.create(requestDto(null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No existe un registro de entrada/salida");
        verify(paymentRepository, never()).save(any(PaymentModel.class));
    }

    @Test
    void update_existingPayment_savesAndReturnsUpdatedDto() {
        PaymentModel updated = entity(1L);
        updated.setStatus("REFUNDED");
        given(paymentRepository.save(any(PaymentModel.class))).willReturn(updated);

        PaymentResponseDto result = paymentService.update(requestDto(1L));

        assertThat(result.getStatus()).isEqualTo("REFUNDED");
    }

    @Test
    void deleteById_existingPayment_deletesAndReturnsTrue() {
        given(paymentRepository.existsById(1L)).willReturn(true);

        assertThat(paymentService.deleteById(1L)).isTrue();
        verify(paymentRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_nonExistingPayment_returnsFalse() {
        given(paymentRepository.existsById(99L)).willReturn(false);

        assertThat(paymentService.deleteById(99L)).isFalse();
        verify(paymentRepository, never()).deleteById(99L);
    }
}
