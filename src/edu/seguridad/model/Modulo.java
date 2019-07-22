package edu.seguridad.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Modulo
 *
 */
@Entity

public class Modulo implements Serializable {

	@Id
	@GeneratedValue
	private Integer idMod;
	private String nombre;

	// many to many relationship between rol and modulo
	@ManyToMany(mappedBy = "modulos")
	private Set<Rol> roles;

	// one to many relationship between modulo and funcionalidad
	@OneToMany(mappedBy = "mod", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Funcionalidad> funcionalidades;

	private static final long serialVersionUID = 1L;

	public Modulo() {
		super();
	}

	public Integer getIdMod() {
		return idMod;
	}

	public void setIdMod(Integer idMod) {
		this.idMod = idMod;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	public Set<Funcionalidad> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(Set<Funcionalidad> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

}
