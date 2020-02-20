package com.example.demo.services;

import org.springframework.http.ResponseEntity;

import com.example.demo.models.EntryData;
import com.example.demo.models.QRData;
import com.example.demo.util.GenericResponse;

public interface MainService {
	
	public ResponseEntity<GenericResponse> createQr(QRData qrData);
	
	public ResponseEntity<GenericResponse> countQr(long idTienda, long codDepto);
	
	public ResponseEntity<GenericResponse> findTiendaByFecha(EntryData entryData);

}
