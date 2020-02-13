package com.example.demo.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class QR implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long idTienda;
	private long codDepto;
	private long contador;
	
	private String fecha;
	
	public QR() {}
	public QR(long id, long idTienda, long codDepto, long contador, String fecha) {
		this.id = id;
		this.idTienda = idTienda;
		this.codDepto = codDepto;
		this.contador = contador;
		this.fecha = fecha;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public long getContador() {
		return contador;
	}
	public void setContador(long contador) {
		this.contador = contador;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
}
