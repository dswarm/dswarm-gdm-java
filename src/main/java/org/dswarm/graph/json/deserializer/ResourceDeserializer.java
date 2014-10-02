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
package org.dswarm.graph.json.deserializer;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import org.dswarm.graph.json.Resource;
import org.dswarm.graph.json.Statement;

/**
 *
 * @author tgaengler
 *
 */
public class ResourceDeserializer extends JsonDeserializer<Resource> {

	@Override
	public Resource deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

		final ObjectCodec oc = jp.getCodec();

		if (oc == null) {

			return null;
		}

		final JsonNode node = oc.readTree(jp);

		if (node == null) {

			return null;
		}

		final Iterator<String> resourceUriFieldNames = node.fieldNames();

		if(resourceUriFieldNames == null || !resourceUriFieldNames.hasNext()) {

			return null;
		}

		final String resourceUri = resourceUriFieldNames.next();

		if(resourceUri == null) {

			return null;
		}

		final JsonNode resourceNode = node.get(resourceUri);

		if(resourceNode == null) {

			return null;
		}

		if(!ArrayNode.class.isInstance(resourceNode)) {

			throw new JsonParseException("expected a JSON array full of statement objects of the resource", jp.getCurrentLocation());
		}

		final Resource resource = new Resource(resourceUri);

		if(resourceNode.size() <= 0) {

			return resource;
		}

		for(final JsonNode statementNode : resourceNode) {

			final Statement statement = statementNode.traverse(oc).readValueAs(Statement.class);
			resource.addStatement(statement);
		}

		return resource;
	}
}
