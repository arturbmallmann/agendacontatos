/**
 * 
 */
package br.edu.android.agendacontatos.model;

import java.io.Serializable;

/**
 * @author artur
 *
 */
public class Contato implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8042585506404291116L;
	private Integer codigo;
	private String nome;
	private String telefone;
	private Double latitude;
	private Double longitude;
	private String foto;
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	@Override
	public String toString() {
		return this.nome+" - "+this.telefone;
	}

}
