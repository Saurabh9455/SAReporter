package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.PaymentEntity;

@Repository
public interface PaymentRepository extends PagingAndSortingRepository<PaymentEntity, Integer>{

	@Query(value = "select count(*) from payment where is_active = 1",nativeQuery = true)
	public int getTotalCountForPayments();
	
	@Query(value = "select count(*) from payment where status = ?1 AND is_active = 1 ",nativeQuery = true)
	public int getTotalCountForSuccessPayments(String status);
	
	@Query(value = "select sum(amount) from payment where status = ?1 and is_active = 1",nativeQuery = true)
	public double getTotalSuccessPaymentAmount(String status);
	
	@Query(value = "select * from payment where transaction_id = ?1",nativeQuery = true)
	public PaymentEntity findByTransaction_id(String orderId);
	
}
