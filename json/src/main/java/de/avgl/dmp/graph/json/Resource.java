package de.avgl.dmp.graph.json;

import java.util.HashSet;
import java.util.Set;

public class Resource {

	private String			uri;

	private Set<Statement>	statements	= new HashSet<Statement>();

	public Resource(final String uriArg) {

		uri = uriArg;
	}

	public String getUri() {

		return uri;
	}

	public void setUri(String uriArg) {

		uri = uriArg;
	}

	public Set<Statement> getStatements() {

		return statements;
	}

	public void setStatements(final Set<Statement> statementsArg) {

		statements = statementsArg;
	}

	public Resource addStatement(final Statement statement) {

		statements.add(statement);

		return this;
	}

	public Resource addStatement(final Long id, final Node subject, final Predicate predicate, final Node object) {

		final Statement statement;

		if (id != null) {

			statement = new Statement(id, subject, predicate, object);
		} else {

			statement = new Statement(subject, predicate, object);
		}

		statements.add(statement);

		return this;
	}

	public Resource addStatement(final Node subject, final Predicate predicate, final Node object) {

		final Statement statement = new Statement(subject, predicate, object);

		statements.add(statement);

		return this;
	}
}
