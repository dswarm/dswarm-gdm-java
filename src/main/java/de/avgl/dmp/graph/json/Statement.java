package de.avgl.dmp.graph.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.avgl.dmp.graph.json.deserializer.StatementDeserializer;

/**
 * @author tgaengler
 */
@JsonDeserialize(using = StatementDeserializer.class)
public class Statement {

	private Long		id			= null;

	@JsonProperty("s")
	private Node		subject		= null;

	@JsonProperty("p")
	private Predicate	predicate	= null;

	@JsonProperty("o")
	private Node		object		= null;

	/**
	 * Optional property of a statement, which doesn't represent a total order of a statement that belongs to a certain resource,
	 * but rather then it represent a order of values (objects) to a certain subject + predicate key. The order number should be a
	 * non-negative number.
	 */
	private Long		order		= null;

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

	public Statement(final Node subjectArg, final Predicate predicateArg, final Node objectArg, final Long orderArg) {

		setSubject(subjectArg);
		setPredicate(predicateArg);
		setObject(objectArg);
		setOrder(orderArg);
	}

	public Statement(final long idArg, final Node subjectArg, final Predicate predicateArg, final Node objectArg) {

		id = idArg;
		setSubject(subjectArg);
		setPredicate(predicateArg);
		setObject(objectArg);
	}

	public Statement(final long idArg, final Node subjectArg, final Predicate predicateArg, final Node objectArg, final Long orderArg) {

		id = idArg;
		setSubject(subjectArg);
		setPredicate(predicateArg);
		setObject(objectArg);
		setOrder(orderArg);
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

	public Long getOrder() {

		return order;
	}

	public void setOrder(final Long orderArg) {

		if (orderArg != null && orderArg.longValue() < 0) {

			throw new IllegalArgumentException("the order of a statement should be a non-negative number");
		}

		order = orderArg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result + ((predicate == null) ? 0 : predicate.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
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
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
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
