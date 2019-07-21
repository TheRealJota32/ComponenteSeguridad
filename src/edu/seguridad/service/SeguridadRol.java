package edu.seguridad.service;

import java.util.List;

import edu.seguridad.model.Rol;

public class SeguridadRol {
	private static Conector conectorHibernate = new Conector();

	public List<Rol> getRol() {
		List<Rol> roles = null;
		try {
			conectorHibernate.startEntityManagerFactory();
			String query = "SELECT r FROM Rol r";
			roles = conectorHibernate.getEm().createQuery(query, Rol.class).getResultList();

			if (roles == null) {
				System.out.println("No se encontro ningun rol");
			}
			conectorHibernate.stopEntityManagerFactory();
		} catch (Exception e) {

		}
		return roles;
	}

	public void updateRol(int id, String nombre) {
		Rol item = null;
		try {
			conectorHibernate.startEntityManagerFactory();
			conectorHibernate.getEm().getTransaction().begin();
			item = conectorHibernate.getEm().find(Rol.class, id);
			item.setNombre(nombre);
			conectorHibernate.getEm().persist(item);
			conectorHibernate.getEm().flush();
			conectorHibernate.getEm().getTransaction().commit();

			conectorHibernate.stopEntityManagerFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeRol(int id) {
		Rol item = null;
		try {
			conectorHibernate.startEntityManagerFactory();
			conectorHibernate.getEm().getTransaction().begin();
			item = conectorHibernate.getEm().find(Rol.class, id);
			conectorHibernate.getEm().remove(item);
			conectorHibernate.getEm().flush();
			conectorHibernate.getEm().getTransaction().commit();

			conectorHibernate.stopEntityManagerFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
