package com.example.demo.services;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.models.QR;
import com.example.demo.models.QRData;
import com.example.demo.repository.QrRepository;
import com.example.demo.util.GenericResponse;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


@Service
public class MainServiceImpl implements MainService {
	
	
	EntityManager em;
	
	@Autowired
	QrRepository qrRepository;

	@Override
	public ResponseEntity<GenericResponse> createQr(QRData qrData) {
		
		GenericResponse genericResponse = new GenericResponse();
		HttpStatus status = null;
		try{
			QRCodeWriter writer = new QRCodeWriter();
			String url = "https://d1238455.ngrok.io?action="+qrData.getAction()+"&branch="+qrData.getBranch()+"&channel="+qrData.getChannel();
			
			BitMatrix matrix = writer.encode(url, BarcodeFormat.QR_CODE, 400, 400);
			
			Path path = FileSystems.getDefault().getPath("C:\\Users\\César Trincado\\Desktop\\contenedor\\rutQr.jpg");
			MatrixToImageWriter.writeToPath(matrix, "JPG", path);
			status = HttpStatus.OK;
			genericResponse.setCode(200);
			genericResponse.setMessage("QR Creado con exito!");
			genericResponse.setResponse(null);
			
		}catch(Exception ex) {
			status = HttpStatus.NO_CONTENT;
			System.out.println("ERROR: "+ex);		
		}
		
		return new ResponseEntity<>(genericResponse,status);
	}


	@Override
	public ResponseEntity<GenericResponse> countQr(long idTienda, long codDepto) {
		
		GenericResponse genericResponse = new GenericResponse();
		HttpStatus status = null;
		
		try {
			
			Optional<QR> qr = qrRepository.findByIdTienda(idTienda);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();
			String fecha = sdf.format(currentDate);
			
			if(!qr.isPresent()) {
				
					
				QR newQR = new QR();
				newQR.setIdTienda(idTienda);
				newQR.setCodDepto(codDepto);
				newQR.setContador(1);
				newQR.setFecha(fecha);
			
				qrRepository.save(newQR);
				status = HttpStatus.OK;
				genericResponse.setCode(200);
				genericResponse.setMessage("QR Creado");
				genericResponse.setResponse(newQR);
				
			}else{
				
				qr.get().setContador(qr.get().getContador()+1);
				qrRepository.save(qr.get());		
				status = HttpStatus.FOUND;
				genericResponse.setCode(200);
				genericResponse.setMessage("Contador QR actualizado");
				genericResponse.setResponse(qr.get());	
			}
			
				
		}catch(Exception ex) {
			status = HttpStatus.FOUND;
			
			
		}
		
		return new ResponseEntity<>(genericResponse, status);
	}


}
