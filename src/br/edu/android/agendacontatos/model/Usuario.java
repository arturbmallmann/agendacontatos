package br.edu.android.agendacontatos.model;

import java.io.Serializable;

/**
 * classe que representa um usuario
 * @author el professor
 */
public class Usuario implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2512229137870365596L;
	private Integer codigo;
	private String login;
	private String senha;
	
	public Integer getCodigo() {
		return codigo;
	}
	public String getLogin() {
		return login;
	}
	public String getSenha() {
		return senha;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
		
}
