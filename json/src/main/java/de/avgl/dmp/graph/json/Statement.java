package de.avgl.dmp.graph.json;

/**
 * @author tgaengler
 */
public class Statement {

	private Long		id			= null;
	private Node		subject		= null;
	private Predicate	predicate	= null;
	private Node		object		= null;

	public Statement() {

	}

	public Statement(final long idArg) {

		id = idArg;
	}
	
	public Statement(final Node subjectArg, final Predicate predicateArg, final Node objectArg) {

		subject = subjectArg;
		predicate = predicateArg;
		object = objectArg;
	}

	public Statement(final long idArg, final Node subjectArg, final Predicate predicateArg, final Node objectArg) {

		id = idArg;
		subject = subjectArg;
		predicate = predicateArg;
		object = objectArg;
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
		
		if(subjectArg.getType().equals(NodeType.Literal)) {
			
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
}
