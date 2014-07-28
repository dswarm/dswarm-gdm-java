package org.dswarm.graph.json.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.dswarm.graph.json.LiteralNode;
import org.dswarm.graph.json.Node;
import org.dswarm.graph.json.Predicate;
import org.dswarm.graph.json.ResourceNode;
import org.dswarm.graph.json.Statement;

/**
 * @author tgaengler
 */
public class StatementDeserializer extends JsonDeserializer<Statement> {

	@Override
	public Statement deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

		final ObjectCodec oc = jp.getCodec();

		if (oc == null) {

			return null;
		}

		final JsonNode node = oc.readTree(jp);

		if (node == null) {

			return null;
		}

		final JsonNode idNode = node.get("id");

		Long id = null;

		if (idNode != null) {

			try {

				id = Long.valueOf(idNode.asLong());
			} catch (final Exception e) {

				id = null;
			}
		}

		final JsonNode uuidNode = node.get("uuid");

		String uuid = null;

		if (uuidNode != null) {

			uuid = uuidNode.asText();
		}

		final JsonNode subjectNode = node.get("s");

		if (subjectNode == null) {

			throw new JsonParseException("expected JSON node that represents the subject of a statement", jp.getCurrentLocation());
		}

		final Node subject;

		if (subjectNode.get("uri") != null) {

			// resource node
			subject = subjectNode.traverse(oc).readValueAs(ResourceNode.class);
		} else {

			// bnode
			subject = subjectNode.traverse(oc).readValueAs(Node.class);
		}

		final JsonNode predicateNode = node.get("p");

		if (predicateNode == null) {

			throw new JsonParseException("expected JSON node that represents the predicate of a statement", jp.getCurrentLocation());
		}

		final Predicate predicate = predicateNode.traverse(oc).readValueAs(Predicate.class);

		final JsonNode objectNode = node.get("o");

		if (objectNode == null) {

			throw new JsonParseException("expected JSON node that represents the object of a statement", jp.getCurrentLocation());
		}

		final Node object;

		if (objectNode.get("uri") != null) {

			// resource node
			object = objectNode.traverse(oc).readValueAs(ResourceNode.class);
		} else if (objectNode.get("v") != null) {

			// literal node
			object = objectNode.traverse(oc).readValueAs(LiteralNode.class);
		} else {

			// bnode
			object = objectNode.traverse(oc).readValueAs(Node.class);
		}

		final JsonNode orderNode = node.get("order");

		Long order = null;

		if (orderNode != null) {

			try {

				order = Long.valueOf(orderNode.asLong());
			} catch (final Exception e) {

				order = null;
			}
		}

		if (id == null && uuid == null && order == null) {

			return new Statement(subject, predicate, object);
		}

		if (id == null && uuid != null && order == null) {

			return new Statement(uuid, subject, predicate, object);
		}

		if (id == null && uuid != null && order != null) {

			return new Statement(uuid, subject, predicate, object, order);
		}

		if (id == null && uuid == null && order != null) {

			return new Statement(subject, predicate, object, order);
		}

		if (id != null && uuid != null && order == null) {

			return new Statement(id, uuid, subject, predicate, object);
		}

		if (id != null && uuid == null && order == null) {

			return new Statement(id, subject, predicate, object);
		}

		if (id != null && uuid == null && order != null) {

			return new Statement(id, subject, predicate, object, order);
		}

		return new Statement(id, uuid, subject, predicate, object, order);
	}
}