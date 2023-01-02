package com.example.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.finalproject.models.Agency;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {

}
