package edu.ulatina.lugares;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: LugarTuristico
 *
 */
@Entity

public class LugarTuristico implements Serializable {

	@Id
	@GeneratedValue
	private Integer id;
	private String nombre;
	private String geoCorde;
	@ManyToOne
	@JoinColumn(name = "idCiudad")
	private Ciudad ciudad;
	private static final long serialVersionUID = 1L;

	public LugarTuristico() {
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

	public String getGeoCorde() {
		return geoCorde;
	}

	public void setGeoCorde(String geoCorde) {
		this.geoCorde = geoCorde;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

}
