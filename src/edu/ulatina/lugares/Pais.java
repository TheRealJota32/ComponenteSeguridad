package edu.ulatina.lugares;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import edu.ulatina.lugares.Ciudad;

/**
 * Entity implementation class for Entity: Pais
 *
 */
@Entity

public class Pais implements Serializable {

	@Id
	@GeneratedValue
	private Integer id;
	private String nombre;
	@OneToMany(mappedBy = "pais", cascade = CascadeType.ALL)
	private Set<Ciudad> ciudades;

	private static final long serialVersionUID = 1L;

	public Pais() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Ciudad> getCiudades() {
		return ciudades;
	}

	public void setCiudades(Set<Ciudad> ciudades) {
		this.ciudades = ciudades;
	}

}
