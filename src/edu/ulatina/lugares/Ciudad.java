package edu.ulatina.lugares;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Ciudad
 *
 */
@Entity

public class Ciudad implements Serializable {

	@Id
	@GeneratedValue
	private Integer id;
	private String nombre;
	@ManyToOne
	@JoinColumn(name = "idPais")
	private Pais pais;
	@OneToMany(mappedBy = "ciudad", cascade = CascadeType.ALL)
	private Set<LugarTuristico> lugares;

	private static final long serialVersionUID = 1L;

	public Ciudad() {
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

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Set<LugarTuristico> getLugares() {
		return lugares;
	}

	public void setLugares(Set<LugarTuristico> lugares) {
		this.lugares = lugares;
	}

}
