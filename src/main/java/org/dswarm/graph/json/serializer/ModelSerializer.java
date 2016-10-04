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
package org.dswarm.graph.json.serializer;

import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.dswarm.graph.json.Model;
import org.dswarm.graph.json.Resource;

/**
 * @author tgaengler
 */
public class ModelSerializer extends JsonSerializer<Model> {

	@Override
	public void serialize(final Model value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonProcessingException {

		final Collection<Resource> resources = value.getResources();

		if (resources != null && !resources.isEmpty()) {

			jgen.writeStartArray();

			for (final Resource resource : resources) {

				jgen.writeObject(resource);
			}

			jgen.writeEndArray();
		}
	}
}
