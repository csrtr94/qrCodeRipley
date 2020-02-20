package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.models.QR;

@Repository
public interface QrRepository extends JpaRepository<QR, Long>{
	
	Optional<QR> findByIdTienda(long id);
	
	List<QR> findAllByIdTienda(long id);
	
	
	@Query(value="SELECT * FROM qr q where q.id_tienda=?1 and q.fecha=?2", nativeQuery = true)
	Optional<QR> findTienda(long id, String fecha);

}
