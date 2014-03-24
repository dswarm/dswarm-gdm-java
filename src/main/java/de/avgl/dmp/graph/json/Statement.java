package de.avgl.dmp.graph.json;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.avgl.dmp.graph.json.deserializer.StatementDeserializer;

/**
 * @author tgaengler
 */
@XmlRootElement
@JsonDeserialize(using = StatementDeserializer.class)
public class Statement {

	@XmlID
	private Long		id			= null;

	@XmlElement(name = "s")
	private Node		subject		= null;

	@XmlElement(name = "p")
	private Predicate	predicate	= null;

	@XmlElement(name = "o")
	private Node		object		= null;

	public Statement() {

	}

	public Statement(final long idArg) {

		id = idArg;
	}

	public Statement(final Node subjectArg, final Predicate predicateArg, final Node objectArg) {

		setSubject(subjectArg);
		setPredicate(predicateArg);
		setObject(objectArg);
	}

	public Statement(final long idArg, final Node subjectArg, final Predicate predicateArg, final Node objectArg) {

		id = idArg;
		setSubject(subjectArg);
		setPredicate(predicateArg);
		setObject(objectArg);
	}

	public Long getId() {

		return id;
	}

	public void setId(final long idArg) {

		id = idArg;
	}

	public Node getSubject() {

		return subject;
	}

	public void setSubject(final Node subjectArg) {

		if (subjectArg.getType().equals(NodeType.Literal)) {

			throw new IllegalArgumentException("literals are not allowed as subject");
		}

		subject = subjectArg;
	}

	public Predicate getPredicate() {

		return predicate;
	}

	public void setPredicate(final Predicate predicateArg) {

		predicate = predicateArg;
	}

	public Node getObject() {

		return object;
	}

	public void setObject(final Node objectArg) {

		object = objectArg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result + ((predicate == null) ? 0 : predicate.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statement other = (Statement) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		if (predicate == null) {
			if (other.predicate != null)
				return false;
		} else if (!predicate.equals(other.predicate))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}
}
