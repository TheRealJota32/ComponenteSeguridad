package edu.seguridad.service;

import java.util.HashSet;
import java.util.List;

import edu.seguridad.model.Modulo;
import edu.seguridad.model.Rol;

public class SeguridadRol {
	private static Conector ch = new Conector();

	public void addRolForModulo(int idRol, String nombre) {
		try {
			ch.startEntityManagerFactory();

			Rol rol = new Rol();
			rol.setModulos(new HashSet<Modulo>());
			rol = ch.getEm().find(Rol.class, idRol);

			Modulo mod = new Modulo();
			mod.setNombre(nombre);

			rol.getModulos().add(mod);

			ch.getEm().getTransaction().begin();
			ch.getEm().merge(rol);
			ch.getEm().getTransaction().commit();

			ch.stopEntityManagerFactory();
			System.out.println("Finalizo");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addOldRol(int idRol, int idMod) {
		try {
			ch.startEntityManagerFactory();

			Rol rol = new Rol();
			rol = ch.getEm().find(Rol.class, idRol);

			Modulo mod = new Modulo();
			mod = ch.getEm().find(Modulo.class, idMod);

			rol.getModulos().add(mod);

			ch.getEm().getTransaction().begin();
			ch.getEm().merge(rol);
			ch.getEm().getTransaction().commit();

			ch.stopEntityManagerFactory();
			System.out.println("Finalizo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Rol> getRol() {
		List<Rol> roles = null;
		try {
			ch.startEntityManagerFactory();
			String query = "SELECT r FROM Rol r";
			roles = ch.getEm().createQuery(query, Rol.class).getResultList();

			if (roles == null) {
				System.out.println("No se encontro ningun rol");
			}
			ch.stopEntityManagerFactory();
		} catch (Exception e) {

		}
		return roles;
	}

	public void updateRol(int id, String nombre) {
		Rol item = null;
		try {
			ch.startEntityManagerFactory();
			ch.getEm().getTransaction().begin();
			item = ch.getEm().find(Rol.class, id);
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

	public void removeRol(int id) {
		Rol item = null;
		try {
			ch.startEntityManagerFactory();
			ch.getEm().getTransaction().begin();
			item = ch.getEm().find(Rol.class, id);
			ch.getEm().remove(item);
			ch.getEm().flush();
			ch.getEm().getTransaction().commit();

			ch.stopEntityManagerFactory();
			System.out.println("Finalizo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
