package edu.seguridad.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
public class Usuario implements Serializable {

	@Id
	@GeneratedValue
	private Integer idUsuario;
	private String nombre;
	private String apellido;
	@Column(unique = true)
	private String correo;
	@Column(unique = true)
	private String username;
	private String password;
	@Column(columnDefinition = "boolean default false", nullable = false)
	private Boolean verificado = false;

	// many to many relationship between user and app
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "usuario_por_app", joinColumns = { @JoinColumn(name = "idUsuario") }, inverseJoinColumns = {
			@JoinColumn(name = "idApp") })
	private Set<Aplicacion> aplicaciones;

	// one to one relationship between usuario and rol
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idRol")
	private Rol rol;

	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getVerificado() {
		return verificado;
	}

	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;
	}

	public boolean isPasswordValid(String password) {
		return this.password.equals(password);
	}

	public Set<Aplicacion> getAplicaciones() {
		return aplicaciones;
	}

	public void setAplicaciones(Set<Aplicacion> aplicaciones) {
		this.aplicaciones = aplicaciones;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

}
