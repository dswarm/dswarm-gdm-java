package org.dswarm.graph.json.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import org.dswarm.graph.json.Model;
import org.dswarm.graph.json.Resource;

/**
 * @author tgaengler
 */
public class ModelDeserializer extends JsonDeserializer<Model> {

	@Override
	public Model deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

		final ObjectCodec oc = jp.getCodec();

		if (oc == null) {

			return null;
		}

		final JsonNode node = oc.readTree(jp);

		if (node == null) {

			return null;
		}

		if (!ArrayNode.class.isInstance(node)) {

			throw new JsonParseException("expected a JSON array full of resource objects of the model", jp.getCurrentLocation());
		}

		if (node.size() <= 0) {

			return null;
		}

		final Model model = new Model();

		for (final JsonNode resourceNode : node) {

			final Resource resource = resourceNode.traverse(oc).readValueAs(Resource.class);
			model.addResource(resource);
		}

		return model;
	}
}
