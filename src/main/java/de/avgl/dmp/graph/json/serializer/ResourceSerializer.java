package de.avgl.dmp.graph.json.serializer;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import de.avgl.dmp.graph.json.Resource;
import de.avgl.dmp.graph.json.Statement;

/**
 * @author tgaengler
 */
public class ResourceSerializer extends JsonSerializer<Resource> {

	@Override
	public void serialize(final Resource value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException,
			JsonProcessingException {

		if (value != null && value.getUri() != null) {

			jgen.writeStartObject();
			jgen.writeFieldName(value.getUri());

			final Set<Statement> statements = value.getStatements();

			if (statements != null && !statements.isEmpty()) {

				jgen.writeStartArray();

				int i = 1;

				for (final Statement statement : statements) {

					jgen.writeObject(statement);

					if (i < statements.size()) {

						jgen.writeRaw(',');
					}

					i++;
				}

				jgen.writeEndArray();
			}

			jgen.writeEndObject();
		}
	}
}
