/**
 * Copyright © 2013 – 2016 SLUB Dresden & Avantgarde Labs GmbH (<code@dswarm.org>)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dswarm.graph.json.deserializer;

import java.io.IOException;

import org.dswarm.graph.json.Model;
import org.dswarm.graph.json.Resource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * @author tgaengler
 */
public class ModelDeserializer extends JsonDeserializer<Model> {

	@Override
	public Model deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {

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
