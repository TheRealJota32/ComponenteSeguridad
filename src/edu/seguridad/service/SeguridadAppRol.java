package edu.seguridad.service;

import java.util.HashSet;

import edu.seguridad.model.Aplicacion;
import edu.seguridad.model.Rol;

public class SeguridadAppRol {
	private static Conector conectorHibernate = new Conector();

	public void addAppRol(String appParam, String rolParam) {
		try {
			conectorHibernate.startEntityManagerFactory();
			Aplicacion app = new Aplicacion();
			app.setNombre(appParam);
			app.setRoles(new HashSet<Rol>());

			Rol rol = new Rol();
			rol.setNombre(rolParam);
			rol.setApp(app);

			app.getRoles().add(rol);

			conectorHibernate.getEm().getTransaction().begin();
			conectorHibernate.getEm().persist(app);
			conectorHibernate.getEm().getTransaction().commit();

			conectorHibernate.stopEntityManagerFactory();
			System.out.println("Finalizo");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addRol(String rolParam) {
		try {
			conectorHibernate.startEntityManagerFactory();
			Aplicacion app = new Aplicacion();
			app = conectorHibernate.getEm().find(Aplicacion.class, 1);

			Rol rol = new Rol();
			rol.setNombre(rolParam);
			rol.setApp(app);

			app.getRoles().add(rol);

			conectorHibernate.getEm().getTransaction().begin();
			conectorHibernate.getEm().merge(app);
			conectorHibernate.getEm().getTransaction().commit();

			conectorHibernate.stopEntityManagerFactory();
			System.out.println("Finalizo");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
