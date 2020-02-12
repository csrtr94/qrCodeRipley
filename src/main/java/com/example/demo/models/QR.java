package com.example.demo.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class QR implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	private long idTienda;
	private long codDepto;
	private long contador;
	
	public QR() {}
	public QR(long idTienda, long codDepto, long contador) {
		this.idTienda = idTienda;
		this.codDepto = codDepto;
		this.contador = contador;
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
	
	
}
