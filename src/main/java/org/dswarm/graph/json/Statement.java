/**
 * Copyright (C) 2013, 2014 SLUB Dresden & Avantgarde Labs GmbH (<code@dswarm.org>)
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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.dswarm.graph.json.deserializer.StatementDeserializer;

/**
 * @author tgaengler
 */
@JsonDeserialize(using = StatementDeserializer.class)
public class Statement {

	private Long		id			= null;

	private String		uuid			= null;

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

	private String evidence = null;

	private String confidence = null;

	public Statement(final Node subjectArg, final Predicate predicateArg, final Node objectArg) {

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

	public String getUUID() {

		return uuid;
	}

	public void setUUID(final String uuidArg) {

		uuid = uuidArg;
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

		if (orderArg != null && orderArg < 0) {

			throw new IllegalArgumentException("the order of a statement should be a non-negative number");
		}

		order = orderArg;
	}

	public String getEvidence() {

		return evidence;
	}

	public void setEvidence(final String evidenceArg) {

		evidence = evidenceArg;
	}

	public String getConfidence() {

		return confidence;
	}

	public void setConfidence(final String confidenceArg) {

		confidence = confidenceArg;
	}

	@Override
	public boolean equals(final Object o) {

		if (this == o) {

			return true;
		}


		if (!(o instanceof Statement)) {

			return false;
		}

		final Statement statement = (Statement) o;

		return !(confidence != null ? !confidence.equals(statement.confidence) : statement.confidence != null) && !(evidence != null ?
				!evidence.equals(statement.evidence) :
				statement.evidence != null) && !(id != null ? !id.equals(statement.id) : statement.id != null) && !(object != null ?
				!object.equals(statement.object) :
				statement.object != null) && !(order != null ? !order.equals(statement.order) : statement.order != null) && !(predicate != null ?
				!predicate.equals(statement.predicate) :
				statement.predicate != null) && !(subject != null ? !subject.equals(statement.subject) : statement.subject != null) && !(
				uuid != null ? !uuid.equals(statement.uuid) : statement.uuid != null);
	}

	@Override
	public int hashCode() {

		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
		result = 31 * result + (subject != null ? subject.hashCode() : 0);
		result = 31 * result + (predicate != null ? predicate.hashCode() : 0);
		result = 31 * result + (object != null ? object.hashCode() : 0);
		result = 31 * result + (order != null ? order.hashCode() : 0);
		result = 31 * result + (evidence != null ? evidence.hashCode() : 0);
		result = 31 * result + (confidence != null ? confidence.hashCode() : 0);

		return result;
	}
	
	public String toString(){
		return "("+ subject + "," + predicate + "," + object + ") Order: " + getOrder(); 
	}
}
