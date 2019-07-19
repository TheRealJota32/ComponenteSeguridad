package edu.seguridad.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Aplicacion
 *
 */
@Entity
public class Aplicacion implements Serializable {

	@Id
	@GeneratedValue
	private Integer idApp;
	private String nombre;
	
	// many to many relationship between user and app
	@ManyToMany(mappedBy = "aplicaciones")
	private Set<Usuario> usuarios;
	
	// one to many relationship between app and rol
	@OneToMany(mappedBy = "app", cascade = CascadeType.ALL)
	private Set<Rol> roles;

	private static final long serialVersionUID = 1L;

	public Aplicacion() {
		super();
	}

	public Integer getIdApp() {
		return idApp;
	}

	public void setIdApp(Integer idApp) {
		this.idApp = idApp;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

}
