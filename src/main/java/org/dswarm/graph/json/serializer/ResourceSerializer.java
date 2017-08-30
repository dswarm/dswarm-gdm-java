/**
 * Copyright © 2013 – 2017 SLUB Dresden & Avantgarde Labs GmbH (<code@dswarm.org>)
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
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.dswarm.graph.json.Resource;
import org.dswarm.graph.json.Statement;

/**
 * @author tgaengler
 */
public class ResourceSerializer extends JsonSerializer<Resource> {

	@Override
	public void serialize(final Resource value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {

		if (value != null && value.getUri() != null) {

			final Set<Statement> statements = value.getStatements();

			if (statements != null && !statements.isEmpty()) {

				jgen.writeStartObject();
				jgen.writeFieldName(value.getUri());

				jgen.writeStartArray();

				for (final Statement statement : statements) {

					jgen.writeObject(statement);
				}

				jgen.writeEndArray();

				jgen.writeEndObject();
			}
		}
	}
}
