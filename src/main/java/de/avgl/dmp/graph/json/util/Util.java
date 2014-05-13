package de.avgl.dmp.graph.json.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

/**
 * 
 * @author tgaengler
 *
 */
public class Util {

	/**
	 * The JSON node factory that can be utilised to create new JSON nodes (objects or arrays).
	 */
	private static final JsonNodeFactory	FACTORY;

	/**
	 * The object mapper that can be utilised to de-/serialise JSON nodes.
	 */
	private static final ObjectMapper		MAPPER;

	static {
		MAPPER = new ObjectMapper();
		MAPPER.setSerializationInclusion(Include.NON_NULL);

				// enable this, if it will be required again
				//.setSerializationInclusion(Include.NON_EMPTY);

		FACTORY = MAPPER.getNodeFactory();
	}

	/**
	 * Gets the object mapper that can be utilised to de-/serialise JSON nodes.
	 * 
	 * @return the object mapper that can be utilised to de-/serialise JSON nodes
	 */
	public static ObjectMapper getJSONObjectMapper() {

		return MAPPER;
	}

	/**
	 * Gets the JSON node factory that can be utilised to create new JSON nodes (objects or arrays)
	 * 
	 * @return JSON node factory that can be utilised to create new JSON nodes (objects or arrays)
	 */
	public static JsonNodeFactory getJSONFactory() {

		return FACTORY;
	}
}
