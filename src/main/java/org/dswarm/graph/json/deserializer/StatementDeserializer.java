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
package org.dswarm.graph.json.deserializer;

import java.io.IOException;

import org.dswarm.graph.json.LiteralNode;
import org.dswarm.graph.json.Node;
import org.dswarm.graph.json.Predicate;
import org.dswarm.graph.json.ResourceNode;
import org.dswarm.graph.json.Statement;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

/**
 * @author tgaengler
 */
public class StatementDeserializer extends JsonDeserializer<Statement> {

	private static final String ID = "id";
	private static final String UUID = "uuid";
	private static final String SUBJECT = "s";
	private static final String URI = "uri";
	private static final String PREDICATE = "p";
	private static final String OBJECT = "o";
	private static final String VALUE = "v";
	private static final String ORDER = "order";
	private static final String EVIDENCE = "evidence";
	private static final String CONFIDENCE = "confidence";

	@Override
	public Statement deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {

		final ObjectCodec oc = jp.getCodec();

		if (oc == null) {

			return null;
		}

		final JsonNode node = oc.readTree(jp);

		if (node == null) {

			return null;
		}

		final JsonNode idNode = node.get(ID);

		Long id = null;

		if (idNode != null) {

			try {

				id = idNode.longValue();
			} catch (final Exception e) {

				id = null;
			}
		}

		final JsonNode uuidNode = node.get(UUID);

		String uuid = null;

		if (uuidNode != null && JsonNodeType.NULL != uuidNode.getNodeType()) {

			uuid = uuidNode.asText();
		}

		final JsonNode subjectNode = node.get(SUBJECT);

		if (subjectNode == null) {

			throw new JsonParseException("expected JSON node that represents the subject of a statement", jp.getCurrentLocation());
		}

		final Node subject;

		if (subjectNode.get(URI) != null) {

			// resource node
			subject = subjectNode.traverse(oc).readValueAs(ResourceNode.class);
		} else {

			// bnode
			subject = subjectNode.traverse(oc).readValueAs(Node.class);
		}

		final JsonNode predicateNode = node.get(PREDICATE);

		if (predicateNode == null) {

			throw new JsonParseException("expected JSON node that represents the predicate of a statement", jp.getCurrentLocation());
		}

		final Predicate predicate = predicateNode.traverse(oc).readValueAs(Predicate.class);

		final JsonNode objectNode = node.get(OBJECT);

		if (objectNode == null) {

			throw new JsonParseException("expected JSON node that represents the object of a statement", jp.getCurrentLocation());
		}

		final Node object;

		if (objectNode.get(URI) != null) {

			// resource node
			object = objectNode.traverse(oc).readValueAs(ResourceNode.class);
		} else if (objectNode.get(VALUE) != null) {

			// literal node
			object = objectNode.traverse(oc).readValueAs(LiteralNode.class);
		} else {

			// bnode
			object = objectNode.traverse(oc).readValueAs(Node.class);
		}

		final JsonNode orderNode = node.get(ORDER);

		Long order = null;

		if (orderNode != null) {

			try {

				order = orderNode.asLong();
			} catch (final Exception e) {

				order = null;
			}
		}

		final JsonNode evidenceNode = node.get(EVIDENCE);

		String evidence = null;

		if (evidenceNode != null) {

			evidence = evidenceNode.asText();
		}

		final JsonNode confidenceNode = node.get(CONFIDENCE);

		String confidence = null;

		if (confidenceNode != null) {

			confidence = confidenceNode.asText();
		}

		final Statement statement = new Statement(subject, predicate, object);

		if(id != null) {

			statement.setId(id);
		}

		if(uuid != null) {

			statement.setUUID(uuid);
		}

		if(order != null) {

			statement.setOrder(order);
		}

		if(evidence != null) {

			statement.setEvidence(evidence);
		}

		if(confidence != null) {

			statement.setConfidence(confidence);
		}

		return statement;
	}
}
