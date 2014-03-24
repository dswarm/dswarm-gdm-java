package de.avgl.dmp.graph.json.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import de.avgl.dmp.graph.json.Predicate;

/**
 * 
 * @author tgaengler
 *
 */
public class PredicateSerializer extends JsonSerializer<Predicate> {

	@Override
	public void serialize(final Predicate value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException,
			JsonProcessingException {

		if (value != null && value.getUri() != null) {

			jgen.writeString(value.getUri());
		}
	}
}
