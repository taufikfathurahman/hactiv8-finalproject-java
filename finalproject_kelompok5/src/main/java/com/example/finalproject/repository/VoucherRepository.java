package com.example.finalproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.finalproject.models.TripSchedule;
import com.example.finalproject.models.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
	@Query(value = "SELECT * FROM voucher WHERE voucher.owner_user_id = ?1", nativeQuery = true)
    List<Voucher> findByOwner(Long owner_user_id);
}
