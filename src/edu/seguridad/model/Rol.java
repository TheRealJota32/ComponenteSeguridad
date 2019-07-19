package edu.seguridad.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Rol
 *
 */
@Entity

public class Rol implements Serializable {

	@Id
	@GeneratedValue
	private Integer idRol;
	private String nombre;
	
	// many to one relationship between app and rol
	@ManyToOne
	@JoinColumn(name = "idApp")
	private Aplicacion app;
	
	// many to many relationship between rol and modulo
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "rol_por_modulo", joinColumns = { @JoinColumn(name = "idRol") }, inverseJoinColumns = {
			@JoinColumn(name = "idMod") })
	private Set<Modulo> modulos;

	private static final long serialVersionUID = 1L;

	public Rol() {
		super();
	}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Aplicacion getApp() {
		return app;
	}

	public void setApp(Aplicacion app) {
		this.app = app;
	}

	public Set<Modulo> getModulos() {
		return modulos;
	}

	public void setModulos(Set<Modulo> modulos) {
		this.modulos = modulos;
	}

}
