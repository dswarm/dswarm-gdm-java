package de.avgl.dmp.graph.json.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import de.avgl.dmp.graph.json.Predicate;

public class PredicateDeserializer extends JsonDeserializer<Predicate> {

	@Override
	public Predicate deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

		final String predicateUri = jp.getValueAsString();

		if (predicateUri == null) {

			return null;
		}

		return new Predicate(predicateUri);
	}
}
