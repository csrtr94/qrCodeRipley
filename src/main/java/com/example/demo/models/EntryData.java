package com.example.demo.models;

public class EntryData {
	
	private long idTienda;
	private String fecha;
	
	public EntryData() {}
	public EntryData(long idTienda, String fecha) {
		super();
		this.idTienda = idTienda;
		this.fecha = fecha;
	}
	
	public long getIdTienda() {
		return idTienda;
	}
	public void setIdTienda(long idTienda) {
		this.idTienda = idTienda;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	

}
