package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.models.EntryData;
import com.example.demo.models.OfferDataDTO;
import com.example.demo.models.QRData;
import com.example.demo.services.MainServiceImpl;
import com.example.demo.util.GenericResponse;

@Controller
public class MainController {
	
	@Autowired
	MainServiceImpl mainService;
	
	@PostMapping("/createqr")
	public ResponseEntity<GenericResponse> createQr(@RequestBody QRData qrData ){
		
		//System.out.println("Dummy: "+dummy.getIdTienda());
		
		return mainService.createQr(qrData);
	}
	
	@PostMapping("/query")
	public ResponseEntity<GenericResponse> getQrData(@RequestBody EntryData entryData){
		return mainService.findTiendaByFecha(entryData);
	}
	
	@GetMapping("/getdata/{id}/{codDepto}")
	public ResponseEntity<GenericResponse> sendData(@PathVariable long id, @PathVariable long codDepto){	
		return mainService.countQr(id, codDepto);
	}
	
	@PostMapping("/savedata")
	public ResponseEntity<GenericResponse> saveRegister(@RequestBody OfferDataDTO data){
		return mainService.saveQrData(data);
	}
	
	@PostMapping("/save")
	public ResponseEntity<GenericResponse> registro(@RequestBody OfferDataDTO data){
		
		return ResponseEntity.status(HttpStatus.OK).body(mainService.saveOfferData(data));
	}
	
}
