/**
 * Copyright (C) 2013 – 2015 SLUB Dresden & Avantgarde Labs GmbH (<code@dswarm.org>)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dswarm.graph.json;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.dswarm.graph.json.deserializer.ResourceDeserializer;
import org.dswarm.graph.json.serializer.ResourceSerializer;

/**
 * @author tgaengler
 */
@JsonDeserialize(using = ResourceDeserializer.class)
@JsonSerialize(using = ResourceSerializer.class)
public class Resource {

	private String			uri			= null;

	/**
	 * ... linked hash set to keep the original order of the statements ...
	 */
	private Set<Statement>	statements	= new LinkedHashSet<Statement>();

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

	public Resource addStatement(final long id, final Node subject, final Predicate predicate, final Node object) {

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(id);

		statements.add(statement);

		return this;
	}

	public Resource addStatement(final long id, final String uuid, final Node subject, final Predicate predicate, final Node object) {

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(id);

		if (uuid != null) {

			statement.setUUID(uuid);
		}

		statements.add(statement);

		return this;
	}

	public Resource addStatement(final long id, final Node subject, final Predicate predicate, final Node object, final Long order) {

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(id);

		if (order != null) {

			statement.setOrder(order);
		}

		statements.add(statement);

		return this;
	}

	public Resource addStatement(final long id, final String uuid, final Node subject, final Predicate predicate, final Node object, final Long order) {

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(id);

		if (uuid != null) {

			statement.setUUID(uuid);
		}

		if (order != null) {

			statement.setOrder(order);
		}

		statements.add(statement);

		return this;
	}

	public Resource addStatement(final Node subject, final Predicate predicate, final Node object) {

		final Statement statement = new Statement(subject, predicate, object);

		statements.add(statement);

		return this;
	}

	public Resource addStatement(final String uuid, final Node subject, final Predicate predicate, final Node object) {

		final Statement statement = new Statement(subject, predicate, object);

		if (uuid != null) {

			statement.setUUID(uuid);
		}

		statements.add(statement);

		return this;
	}

	public Resource addStatement(final Node subject, final Predicate predicate, final Node object, final Long order) {

		final Statement statement = new Statement(subject, predicate, object);

		if (order != null) {

			statement.setOrder(order);
		}

		statements.add(statement);

		return this;
	}

	public Resource addStatement(final String uuid, final Node subject, final Predicate predicate, final Node object, final Long order) {

		final Statement statement = new Statement(subject, predicate, object);

		if (uuid != null) {

			statement.setUUID(uuid);
		}

		if (order != null) {

			statement.setOrder(order);
		}

		statements.add(statement);

		return this;
	}

	public int size() {

		return statements.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((statements == null) ? 0 : statements.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		Resource other = (Resource) obj;
		if (statements == null) {
			if (other.statements != null)
				return false;
		} else if (!statements.equals(other.statements))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
	
	public String toString(){
		return getUri() + ":Resource"; 
	}
	
	public String toLongString(){
		
		String s = "";
		s += getUri() + ":Resource";
		
		for (Statement statement : getStatements()) {
			s += statement;
		}
		
		return s;
	}
}
