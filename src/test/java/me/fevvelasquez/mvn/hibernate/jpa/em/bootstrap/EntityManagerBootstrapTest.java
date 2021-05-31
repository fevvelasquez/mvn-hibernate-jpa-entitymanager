/**
 * Bootstrap a JPA EntityManagerFactory.
 * 
 */
package me.fevvelasquez.mvn.hibernate.jpa.em.bootstrap;

import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;
import me.fevvelasquez.mvn.hibernate.jpa.em.model.Album;

/**
 * Bootstrap a JPA EntityManagerFactory.
 * 
 * @version 0.1.0-SNAPSHOT. Bootstrap a JPA EntityManagerFactory.
 * @author fevvelasquez@gmail.com
 *
 */
public class EntityManagerBootstrapTest extends TestCase {
	private EntityManagerFactory entityManagerFactory;

	protected void setUp() throws Exception {
		/*
		 * EntityManagerFactory is set up once for an application. Notice that the
		 * persistence unit name matches the one in persistence.xml.
		 */
		entityManagerFactory = Persistence
				.createEntityManagerFactory("me.fevvelasquez.mvn.hibernate.jpa.em.persistenceunit");
	}

	protected void tearDown() throws Exception {
		if (entityManagerFactory.isOpen()) {
			entityManagerFactory.close();
		}
	}

	public void testBasicUsage() {

		EntityManager em;
		/*
		 * The javax.persistence.EntityManager interface is used instead of the
		 * org.hibernate.Session interface. JPA calls this operation "persist" instead
		 * of "save".
		 */
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(new Album("A Love Supreme", new GregorianCalendar(1965, 1, 1).getTime()));
		em.persist(new Album("KIDS SEE GHOSTS", new GregorianCalendar(2018, 5, 8).getTime()));
		em.getTransaction().commit();
		em.close();

		/*
		 * Here we see an example of the JPQL (Java Persistence Query Language)to load
		 * all existing Event objects from the database by generating the appropriate
		 * SELECT SQL, sending it to the database and populating Album objects with the
		 * result set data.
		 * 
		 * Session object uses HQL (Hibernate Query Language).
		 */
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		List<Album> list = em.createQuery("FROM Album", Album.class).getResultList();
		list.stream().forEach(System.out::println);
		em.getTransaction().commit();
		em.close();
	}

}
