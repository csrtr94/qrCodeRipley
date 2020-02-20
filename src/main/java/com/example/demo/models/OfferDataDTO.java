package com.example.demo.models;

public class OfferDataDTO {
	
	private long idTienda;
	private long codDepto;
	private String rut;
	
	public OfferDataDTO() {}
	
	public OfferDataDTO(long idTienda, long codDepto, String rut) {
		super();
		this.idTienda = idTienda;
		this.codDepto = codDepto;
		this.rut = rut;
	}
	
	public long getIdTienda() {
		return idTienda;
	}
	public void setIdTienda(long idTienda) {
		this.idTienda = idTienda;
	}
	public long getCodDepto() {
		return codDepto;
	}
	public void setCodDepto(long codDepto) {
		this.codDepto = codDepto;
	}
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	
	
	
	

}
