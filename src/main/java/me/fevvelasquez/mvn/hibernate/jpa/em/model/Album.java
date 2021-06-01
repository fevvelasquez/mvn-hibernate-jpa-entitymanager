/**
 * Persist, Merge, Remove and 'java.time' instead 'Date'.
 * 
 * Trying basic EntityManager operations and 
 * using 'java.time.LocalDate' instead 'java.util.Date'.
 */
package me.fevvelasquez.mvn.hibernate.jpa.em.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * This class uses standard <strong>JavaBean</strong> naming conventions for
 * property getter and setter methods, as well as private visibility for the
 * fields. Although this is the recommended design, it is not a
 * <strong>Hibernate</strong> requirement. <br>
 * <br>
 * The <strong>no-argument constructor</strong>, which is also a JavaBean
 * convention, <strong>is a requirement</strong> for all persistent classes.
 * <strong>Hibernate</strong> needs to create objects for you, using Java
 * Reflection. The constructor can be private. However, package or public
 * visibility is required for runtime proxy generation and efficient data
 * retrieval without bytecode instrumentation.
 * 
 * @version 0.0.5-SNAPSHOT Persist, Merge, Remove and 'java.time'.
 * @author fevvelasquez@gmail.com
 *
 */
/*
 * The @javax.persistence.Entity annotation is used to mark a class as an
 * entity. It functions the same as the <class/> mapping element.
 */
@Entity
/*
 * The @javax.persistence.Table annotation explicitly specifies the table name.
 * Without this specification, the default table name would be ALBUM.
 */
@Table(name = "albums")
public class Album {

	private Long id;
	private String title;
	private LocalDate releaseDate;

	/**
	 * For Hibernate use.
	 */
	public Album() {
	}

	public Album(String title, LocalDate releaseDate) {
		this.title = title;
		this.releaseDate = releaseDate;
	}

	/*
	 * @javax.persistence.Id marks the property which defines the entity’s
	 * identifier.
	 */
	@Id()
	/*
	 * @javax.persistence.GeneratedValue
	 * and @org.hibernate.annotations.GenericGenerator work in tandem to indicate
	 * that Hibernate should use Hibernate’s increment generation strategy for this
	 * entity’s identifier values.
	 */
	@GeneratedValue(generator = "aincrement", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name = "aincrement", strategy = "increment")
	@Column(name = "album_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * Attributes of an entity are considered persistent by default when mapping
	 * with annotations, which is why we don’t see any mapping information
	 * associated here.
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * The mapping types are neither Java data types nor SQL database types.
	 * Instead, they are Hibernate mapping types, which are converters which
	 * translate between Java and SQL data types. Hibernate attempts to determine
	 * the correct conversion by using Java reflection. In some cases this automatic
	 * detection might not chose the default you expect or need, as seen with the
	 * date property.
	 * 
	 * For example, in this case, Hibernate cannot know if the property, which is of
	 * type java.util.Date, should map to a SQL DATE, TIME, or TIMESTAMP datatype.
	 * Full date and time information is preserved by mapping the property to the
	 * timestamp converter, which identifies the converter class
	 * org.hibernate.type.TimestampType.
	 * 
	 * Aditionaly, as said, Hibernate determines the mapping type using reflection.
	 * This process adds overhead in terms of time and resources. If startup
	 * performance is important, consider explicitly defining the type to use.
	 */
	// @Temporal(TemporalType.DATE)
	// Not needed with LocalDate instead of Date.
	// Early versions of hibernate require hibernate-java8 dependecy to manage
	// java.time
	@Column(name = "release_date", nullable = false)
	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return "Album [id=" + id + ", title=" + title + ", releaseDate=" + releaseDate + "]";
	}

}
