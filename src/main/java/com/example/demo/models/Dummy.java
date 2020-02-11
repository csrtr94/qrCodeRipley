package com.example.demo.models;



public class Dummy {
	
	private long id;
	private String rut;
	private long idTienda;
	
	public Dummy() {}
	public Dummy(long id, String rut, long idTienda) {
		this.id = id;
		this.rut = rut;
		this.idTienda = idTienda;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public long getIdTienda() {
		return idTienda;
	}
	public void setIdTienda(long idTienda) {
		this.idTienda = idTienda;
	}
	
	
	
}
