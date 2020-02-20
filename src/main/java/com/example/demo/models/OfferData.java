package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OfferData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String rut;
	private long codTienda;
	private long codDepto;
	private long cv;
	private long TAM;
	private long cp;
	private long tr;
	
	public OfferData() {}
	public OfferData(long id, String rut, long codTienda, long codDepto, long cv, long tAM, long cp, long tr) {
		
		this.id = id;
		this.rut = rut;
		this.codTienda = codTienda;
		this.codDepto = codDepto;
		this.cv = cv;
		TAM = tAM;
		this.cp = cp;
		this.tr = tr;
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
	public long getCodTienda() {
		return codTienda;
	}
	public void setCodTienda(long codTienda) {
		this.codTienda = codTienda;
	}
	public long getCodDepto() {
		return codDepto;
	}
	public void setCodDepto(long codDepto) {
		this.codDepto = codDepto;
	}
	public long getCv() {
		return cv;
	}
	public void setCv(long cv) {
		this.cv = cv;
	}
	public long getTAM() {
		return TAM;
	}
	public void setTAM(long tAM) {
		TAM = tAM;
	}
	public long getCp() {
		return cp;
	}
	public void setCp(long cp) {
		this.cp = cp;
	}
	public long getTr() {
		return tr;
	}
	public void setTr(long tr) {
		this.tr = tr;
	}
	
}
