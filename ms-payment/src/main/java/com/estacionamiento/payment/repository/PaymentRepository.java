package com.estacionamiento.payment.repository;

import com.estacionamiento.payment.model.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentModel, Long> {
    List<PaymentModel> findByEntryExitId(Long entryExitId);
    List<PaymentModel> findByStatus(String status);
    Optional<PaymentModel> findByEntryExitIdAndStatus(Long entryExitId, String status);
}
