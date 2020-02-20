package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.OfferData;

@Repository
public interface OfferRepository extends JpaRepository<OfferData, Long> {

}
