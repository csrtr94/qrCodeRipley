package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}
