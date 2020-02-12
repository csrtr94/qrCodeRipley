package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.QR;

public interface QrRepository extends JpaRepository<QR, Long>{

}
