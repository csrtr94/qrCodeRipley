package com.example.demo.services;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.models.EntryData;
import com.example.demo.models.OfferData;
import com.example.demo.models.OfferDataDTO;
import com.example.demo.models.QR;
import com.example.demo.models.QRData;
import com.example.demo.repository.OfferRepository;
import com.example.demo.repository.QrRepository;
import com.example.demo.util.GenericResponse;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


@Service
public class MainServiceImpl implements MainService {
	
	
	
	@Autowired
	QrRepository qrRepository;
	
	@Autowired
	OfferRepository offerRepository;

	@Override
	public ResponseEntity<GenericResponse> createQr(QRData qrData) {
		
		GenericResponse genericResponse = new GenericResponse();
		HttpStatus status = null;
		try{
			QRCodeWriter writer = new QRCodeWriter();
			String url = "https://simple.ripley.cl/tarjeta-ripley?action="+qrData.getAction()+"&branch="+qrData.getBranch()+"&channel="+qrData.getChannel();
			
			BitMatrix matrix = writer.encode(url, BarcodeFormat.QR_CODE, 400, 400);
			
			Path path = FileSystems.getDefault().getPath("C:\\Users\\César Trincado\\Pictures\\QR\\rutQr.jpg");
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
			
			List<QR> qr = qrRepository.findAllByIdTienda(idTienda);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();
			String fecha = sdf.format(currentDate);
			status = HttpStatus.OK;
			if(!qr.isEmpty()) {
				int largo = qr.size()-1;
				//qr.get().getFecha().equals(fecha)	
				if(!qr.get(largo).getFecha().equals(fecha)) {
					
					QR qrExist = new QR();
					qrExist.setIdTienda(idTienda);
					qrExist.setCodDepto(codDepto);
					qrExist.setContador(1);
					qrExist.setFecha(fecha);
					qrRepository.save(qrExist);
					status = HttpStatus.OK;
					genericResponse.setCode(200);
					genericResponse.setMessage("Nuevo QR por fechas agregado!");
					genericResponse.setResponse(qr.get(largo));	
					
				}else {
					qr.get(largo).setContador(qr.get(largo).getContador()+1);
					qrRepository.save(qr.get(largo));		
					status = HttpStatus.FOUND;
					genericResponse.setCode(200);
					genericResponse.setMessage("Contador QR actualizado");
					genericResponse.setResponse(qr.get(largo));	
									
				}
						
				
			}else{
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
				
			}
			
			
		}catch(Exception ex) {
			System.out.println("La catch: "+ex);
			status = HttpStatus.FOUND;
			
			
		}
		
		return new ResponseEntity<>(genericResponse, status);
	}


	@Override
	public ResponseEntity<GenericResponse> findTiendaByFecha(EntryData entryData) {
			
		GenericResponse genericResponse = new GenericResponse();
		HttpStatus status = null;
		
		try{
			Optional<QR> qr = qrRepository.findTienda(entryData.getIdTienda(), entryData.getFecha());
			System.out.println("QR: "+qr.get().getFecha());
			status = HttpStatus.OK;
			genericResponse.setCode(200);
			genericResponse.setMessage("Registro encontrado!");
			genericResponse.setResponse(qr);
			
			
		}catch(Exception ex) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println("EXCEPTION EX: "+ex);
			
		}
		
		
		
		return new ResponseEntity<>(genericResponse, status);
	}


	@Override
	public ResponseEntity<GenericResponse> saveQrData(OfferDataDTO offerData) {
				
		RestTemplate plantilla = new RestTemplate();
		String url = "https://miportal.bancoripley.cl/apirest-campanas/campaign";
		
		HttpStatus status = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat horasdf = new SimpleDateFormat("HH:mm:ss");
		Date currentDate = new Date();
		
		GenericResponse genericResponse = new GenericResponse();
		try {
			
			JSONObject json = new JSONObject();
			json.put("action",2031);
			json.put("branch",31);
			json.put("channel",13);
			json.put("clientDni",Integer.parseInt(offerData.getRut()));
			json.put("executiveDni",0);
			json.put("filterApplication","");
			json.put("filterDevice","");
			json.put("filterProduct","");
			json.put("terminal",0);
			
			String hora = horasdf.format(currentDate);
			String fecha = sdf.format(currentDate);
		
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<String>(json.toString(), headers);
	
			String response = plantilla.postForObject(url, request, String.class);
			JSONObject jsonObject = new JSONObject(response);
			if( !jsonObject.get("offers").equals(null)) {
			
				JSONArray arrayObject = jsonObject.getJSONArray("offers");
				
				OfferData offer = 
						new OfferData(offerData.getRut(), offerData.getIdTienda(), offerData.getCodDepto(),0,0,0, 0, fecha,hora);

				offerRepository.save(offer);
		
				for(int i=0; i<arrayObject.length(); i++) {
			
					String productType = arrayObject.getJSONObject(i).get("productType").toString();
					String popup = arrayObject.getJSONObject(i).get("popup").toString();
					String popupArr[] = popup.split(" ");
					boolean flag = false;
					for(int x=0; x<popupArr.length; x++) {
						if(popupArr[x].equals("Mastercard")) {
							flag = true;
						}
					}
														
					switch(productType) {
				
						case "9" :
							
							if(flag) {
								offer.setTAM(1);
								offerRepository.save(offer);
								break;
							}else {
								offer.setTr(1);
								offerRepository.save(offer);
								break;
							}
							
											
						case "300" : 
							offer.setCv(1);
							offerRepository.save(offer);
							break;
						case "8" :
							offer.setCp(1);
							offerRepository.save(offer);
							break;
						default : System.out.println("No existen ofertas");			
					}
			
				}
				status = HttpStatus.OK;
				genericResponse.setCode(200);
				genericResponse.setMessage("Oferta encontrada");
				genericResponse.setResponse(offer);
			
			
			}else {
				genericResponse.setCode(200);
				genericResponse.setMessage("No hay oferta disponible");
				genericResponse.setResponse(null);
				status = HttpStatus.NO_CONTENT;
				
				
			}
			
		}catch(Exception ex) {
			System.out.println("aa: "+ex);
			status = HttpStatus.BAD_REQUEST;
			genericResponse.setCode(400);
			genericResponse.setMessage("No existen datos de entrada");
			genericResponse.setResponse(null);
			
		}
		
		return new ResponseEntity<>(genericResponse, status);
	}


	@Override
	public GenericResponse saveOfferData(OfferDataDTO offerData) {
		
		RestTemplate plantilla = new RestTemplate();
		String url = "https://miportal.bancoripley.cl/apirest-campanas/campaign";		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat horasdf = new SimpleDateFormat("HH:mm:ss");
		Date currentDate = new Date();
		GenericResponse genericResponse = new GenericResponse();
		
		try {
			JSONObject json = new JSONObject();
			json.put("action",2031);
			json.put("branch",31);
			json.put("channel",13);
			json.put("clientDni",Integer.parseInt(offerData.getRut()));
			json.put("executiveDni",0);
			json.put("filterApplication","");
			json.put("filterDevice","");
			json.put("filterProduct","");
			json.put("terminal",0);
			
			String hora = horasdf.format(currentDate);
			String fecha = sdf.format(currentDate);
		
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<String>(json.toString(), headers);
	
			String response = plantilla.postForObject(url, request, String.class);
			JSONObject jsonObject = new JSONObject(response);
			if( !jsonObject.get("offers").equals(null)){
			
				JSONArray arrayObject = jsonObject.getJSONArray("offers");
				
				OfferData offer = 
						new OfferData(offerData.getRut(), offerData.getIdTienda(), offerData.getCodDepto(),0,0,0, 0, fecha,hora);

				offerRepository.save(offer);
		
				for(int i=0; i<arrayObject.length(); i++) {
			
					String productType = arrayObject.getJSONObject(i).get("productType").toString();
					String popup = arrayObject.getJSONObject(i).get("popup").toString();
					String popupArr[] = popup.split(" ");
					boolean flag = false;
					for(int x=0; x<popupArr.length; x++) {
						if(popupArr[x].equals("Mastercard")) {
							flag = true;
						}
					}
														
					switch(productType) {
				
						case "9" :
							
							if(flag) {
								offer.setTAM(1);
								offerRepository.save(offer);
								break;
							}else {
								offer.setTr(1);
								offerRepository.save(offer);
								break;
							}
							
											
						case "300" : 
							offer.setCv(1);
							offerRepository.save(offer);
							break;
						case "8" :
							offer.setCp(1);
							offerRepository.save(offer);
							break;
						default : System.out.println("No existen ofertas");			
					}
			
				}
				
				genericResponse.setCode(200);
				genericResponse.setMessage("Oferta encontrada");
				genericResponse.setResponse(offer);
			
			
			}else {
				genericResponse.setCode(200);
				genericResponse.setMessage("No hay oferta disponible");
				genericResponse.setResponse(null);
				
			}
			
		}catch(Exception ex) {
			genericResponse.setCode(400);
			genericResponse.setMessage("No existen datos de entrada");
			genericResponse.setResponse(null);
		}
			
		return genericResponse;
	}

}
