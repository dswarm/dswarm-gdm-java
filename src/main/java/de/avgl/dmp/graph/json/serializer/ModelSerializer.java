package de.avgl.dmp.graph.json.serializer;

import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import de.avgl.dmp.graph.json.Model;
import de.avgl.dmp.graph.json.Resource;

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
