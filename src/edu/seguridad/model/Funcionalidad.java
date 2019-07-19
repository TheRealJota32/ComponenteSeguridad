package edu.seguridad.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Funcionalidad
 *
 */
@Entity

public class Funcionalidad implements Serializable {

	@Id
	@GeneratedValue
	private Integer idFunc;
	private String nombre;

	// many to one relationship between modulo and funcionalidad
	@ManyToOne
	@JoinColumn(name = "idMod")
	private Modulo mod;

	private static final long serialVersionUID = 1L;

	public Funcionalidad() {
		super();
	}

	public Integer getIdFunc() {
		return idFunc;
	}

	public void setIdFunc(Integer idFunc) {
		this.idFunc = idFunc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Modulo getMod() {
		return mod;
	}

	public void setMod(Modulo mod) {
		this.mod = mod;
	}

}
