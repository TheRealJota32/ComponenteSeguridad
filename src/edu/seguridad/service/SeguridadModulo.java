package edu.seguridad.service;

import java.util.List;

import edu.seguridad.model.Funcionalidad;
import edu.seguridad.model.Modulo;

public class SeguridadModulo {
	private static Conector ch = new Conector();

	public List<Modulo> getModulos() {
		List<Modulo> modulos = null;
		try {
			ch.startEntityManagerFactory();
			String query = "SELECT m FROM Modulo m";
			modulos = ch.getEm().createQuery(query, Modulo.class).getResultList();

			if (modulos == null) {
				System.out.println("No se encontro ningun modulo");
			}

			ch.stopEntityManagerFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modulos;
	}

	public void updateModulo(int id, String nombre) {
		Modulo item = null;
		try {
			ch.startEntityManagerFactory();
			ch.getEm().getTransaction().begin();
			item = ch.getEm().find(Modulo.class, id);
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

	public void addFunction(int idMod, String nombre) {
		try {
			ch.startEntityManagerFactory();
			Modulo mod = new Modulo();
			mod = ch.getEm().find(Modulo.class, idMod);

			Funcionalidad func = new Funcionalidad();
			func.setNombre(nombre);
			func.setMod(mod);

			mod.getFuncionalidades().add(func);

			ch.getEm().getTransaction().begin();
			ch.getEm().persist(mod);
			ch.getEm().getTransaction().commit();

			ch.stopEntityManagerFactory();
			System.out.println("Finalizo");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
