package edu.seguridad.service;

import java.util.List;

import javax.persistence.TypedQuery;

import edu.seguridad.model.Funcionalidad;
import edu.seguridad.model.Modulo;;

public class SeguridadFuncionalidad {
	private static Conector ch = new Conector();

	public List<Funcionalidad> getFunctions(int idMod) {
		List<Funcionalidad> funcionalidades = null;
		try {
			ch.startEntityManagerFactory();

			Modulo mod = new Modulo();
			mod = ch.getEm().find(Modulo.class, idMod);

			String query = "SELECT f FROM Funcionalidad f WHERE f.mod = :modId";
			TypedQuery<Funcionalidad> tq = ch.getEm().createQuery(query, Funcionalidad.class);
			tq.setParameter("modId", mod);

			funcionalidades = tq.getResultList();

			if (funcionalidades == null) {
				System.out.println("No se encontro ninguna funcionalidad en el modulo: " + idMod);
			}

			ch.stopEntityManagerFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return funcionalidades;
	}

	public void updateFunction(int id, String nombre) {
		Funcionalidad item = null;
		try {
			ch.startEntityManagerFactory();
			ch.getEm().getTransaction().begin();
			item = ch.getEm().find(Funcionalidad.class, id);
			item.setNombre(nombre);
			ch.getEm().persist(item);
			ch.getEm().flush();
			ch.getEm().getTransaction().commit();
			ch.stopEntityManagerFactory();
			System.out.println("Finalizo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
