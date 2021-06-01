/**
 * 
 * Persist, Merge, Remove and 'java.time' instead 'Date'.
 * 
 * Trying basic EntityManager operations and 
 * using 'java.time.LocalDate' instead 'java.util.Date'.
 */
package me.fevvelasquez.mvn.hibernate.jpa.em.bootstrap;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;
import me.fevvelasquez.mvn.hibernate.jpa.em.model.Album;

/**
 * Bootstrap a JPA EntityManagerFactory.
 * 
 * @version 0.0.5-SNAPSHOT Persist, Merge, Remove and 'java.time'.
 * @author fevvelasquez@gmail.com
 *
 */
public class EntityManagerBootstrapTest extends TestCase {
	private EntityManagerFactory emf;

	@Override
	protected void setUp() throws Exception {
		/*
		 * EntityManagerFactory is set up once for an application. Notice that the
		 * persistence unit name matches the one in persistence.xml.
		 */
		emf = Persistence.createEntityManagerFactory("me.fevvelasquez.mvn.hibernate.jpa.em.persistenceunit");
	}

	@Override
	protected void tearDown() throws Exception {
		if (emf.isOpen()) {
			emf.close();
		}
	}

	public void testBasicUsage() {

		EntityManager em;
		/*
		 * The javax.persistence.EntityManager interface is used instead of the
		 * org.hibernate.Session interface. JPA calls this operation "persist" instead
		 * of "save".
		 */
		em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(new Album("KIDS SEE GHOSTS", LocalDate.of(2018, Month.MAY, 8)));
		em.persist(new Album("A Love Supreme", LocalDate.of(1965, Month.FEBRUARY, 1)));
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
		em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Album> list = em.createQuery("FROM Album", Album.class).getResultList();
		list.stream().forEach(System.out::println);
		em.getTransaction().commit();
		em.close();

		/**
		 * merge, remove.
		 */
		Album album = new Album();
		em = emf.createEntityManager();
		em.getTransaction().begin();
		// nothing yet, object is detached here.
		album.setReleaseDate(LocalDate.now());
		// insert, return a managed object.
		album = em.merge(album);
		// update, because album is managed this time.
		album.setTitle("Test Album");
		// delete
		em.remove(album);
		em.getTransaction().commit();
		em.close();

	}

}
