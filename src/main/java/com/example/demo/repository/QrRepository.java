package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.QR;

public interface QrRepository extends JpaRepository<QR, Long>{
	
	Optional<QR> findByIdTienda(long id);
	
	List<QR> findAllByIdTienda(long id);

}
