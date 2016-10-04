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
package org.dswarm.graph.json.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dswarm.common.web.URI;
import org.dswarm.graph.json.LiteralNode;
import org.dswarm.graph.json.Model;
import org.dswarm.graph.json.Node;
import org.dswarm.graph.json.Resource;
import org.dswarm.graph.json.ResourceNode;
import org.dswarm.graph.json.Statement;
import org.dswarm.graph.json.util.helper.ConverterHelper;
import org.dswarm.graph.json.util.helper.ConverterHelper2;
import org.dswarm.graph.json.util.helper.ConverterHelperGDMHelper;
import org.dswarm.graph.json.util.helper.ConverterHelperGDMHelper2;

/**
 * @author tgaengler
 */
public class Util {

	private static final Logger LOG = LoggerFactory.getLogger(Util.class);

	public static final String RECORD_ID = "__record_id";

	/**
	 * The JSON node factory that can be utilised to create new JSON nodes (objects or arrays).
	 */
	private static final JsonNodeFactory FACTORY;

	/**
	 * The object mapper that can be utilised to de-/serialise JSON nodes.
	 */
	private static final ObjectMapper MAPPER;

	static {
		final ObjectMapper objectMapper = new ObjectMapper();
		MAPPER = objectMapper.setSerializationInclusion(Include.NON_NULL);

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

	public static JsonNode toGDMCompactJSON(final Model model,
	                                        final Set<String> recordURIs) {

		if (model == null) {

			Util.LOG.debug("model is null, can't convert model to GDM Compact JSON");

			return null;
		}

		if (recordURIs == null) {

			Util.LOG.debug("resource URI is null, can't convert model to GDM Compact JSON");

			return null;
		}

		final Iterator<String> iter = recordURIs.iterator();

		if (!iter.hasNext()) {

			// no entries

			return null;
		}

		final ArrayNode jsonArray = Util.getJSONObjectMapper().createArrayNode();

		while (iter.hasNext()) {

			final String resourceURI = iter.next();
			final Resource recordResource = model.getResource(resourceURI);

			if (recordResource == null) {

				Util.LOG.debug("couldn't find record resource for record  uri '{}' in model", resourceURI);

				return null;
			}

			final ArrayNode json = Util.getJSONObjectMapper().createArrayNode();

			// determine record resource node from statements of the record resource
			final ResourceNode recordResourceNode = Util.getResourceNode(resourceURI, recordResource);

			if (recordResourceNode == null) {

				Util.LOG.debug("couldn't find record resource node for record  uri '{}' in model", resourceURI);

				return null;
			}

			convertToGDMCompactJSON(recordResource, recordResourceNode, json, model);

			if (json == null) {

				// TODO: maybe log something here

				continue;
			}

			final ObjectNode resourceJson = Util.getJSONObjectMapper().createObjectNode();

			resourceJson.set(resourceURI, json);
			jsonArray.add(resourceJson);
		}

		return jsonArray;
	}

	public static JsonNode convertToGDMCompactJSON(final Resource recordResource,
	                                               final Node resourceNode,
	                                               final ArrayNode json,
	                                               final Model model) {

		final Map<String, ConverterHelper> converterHelpers = new LinkedHashMap<>();

		// filter record resource statements to statements for subject uri/id (resource node))
		final Set<Statement> statements = Util.getResourceStatement(resourceNode, recordResource);

		for (final Statement statement : statements) {

			final String propertyURI = statement.getPredicate().getUri();
			final Node gdmNode = statement.getObject();

			if (gdmNode instanceof LiteralNode) {

				ConverterHelperGDMHelper.addLiteralToConverterHelper(converterHelpers, propertyURI, gdmNode);

				continue;
			}

			if (gdmNode instanceof ResourceNode) {

				final ResourceNode object = (ResourceNode) gdmNode;

				final Resource objectResource;

				if (model.getResource(object.getUri()) != null) {

					objectResource = model.getResource(object.getUri());
				} else {

					objectResource = recordResource;
				}

				// TODO: define stop criteria to avoid running in endless loops

				// filter record resource statements to statements for object uri (object node))
				final Set<Statement> objectStatements = Util.getResourceStatement(object, objectResource);

				if (objectStatements == null || objectStatements.isEmpty()) {

					ConverterHelperGDMHelper.addURIResourceToConverterHelper(converterHelpers, propertyURI, gdmNode);

					continue;
				}

				// resource has an uri, but is deeper in the hierarchy => record_id will be attached inline

				final ArrayNode objectNode = Util.getJSONObjectMapper().createArrayNode();

				final JsonNode jsonNode = convertToGDMCompactJSON(objectResource, object, objectNode, model);

				final ObjectNode recordIdNode = Util.getJSONObjectMapper().createObjectNode();
				recordIdNode.put(Util.RECORD_ID, object.getUri());

				objectNode.add(recordIdNode);

				ConverterHelperGDMHelper.addJSONNodeToConverterHelper(converterHelpers, propertyURI, jsonNode);

				continue;
			}

			// node is (/must be) a blank node

			final ArrayNode objectNode = Util.getJSONObjectMapper().createArrayNode();

			final JsonNode jsonNode = convertToGDMCompactJSON(recordResource, gdmNode, objectNode, model);

			ConverterHelperGDMHelper.addJSONNodeToConverterHelper(converterHelpers, propertyURI, jsonNode);
		}

		for (final Map.Entry<String, ConverterHelper> converterHelperEntry : converterHelpers.entrySet()) {

			converterHelperEntry.getValue().build(json);
		}

		return json;
	}

	public static JsonNode toGDMSimpleJSON(final Model model, final Set<String> recordURIs) {

		if (model == null) {

			LOG.debug("model is null, can't convert model to GDM simple JSON");

			return null;
		}

		if (recordURIs == null) {

			LOG.debug("resource URI is null, can't convert model to GDM simple JSON");

			return null;
		}

		final Iterator<String> iter = recordURIs.iterator();

		if (!iter.hasNext()) {

			// no entries

			return null;
		}

		final ArrayNode jsonArray = Util.getJSONObjectMapper().createArrayNode();

		while (iter.hasNext()) {

			final String resourceURI = iter.next();
			final Resource recordResource = model.getResource(resourceURI);

			if (recordResource == null) {

				LOG.debug("couldn't find record resource for record  uri '{}' in model", resourceURI);

				return null;
			}

			final ObjectNode json = Util.getJSONObjectMapper().createObjectNode();

			// determine record resource node from statements of the record resource
			final ResourceNode recordResourceNode = Util.getResourceNode(resourceURI, recordResource);

			if (recordResourceNode == null) {

				LOG.debug("couldn't find record resource node for record  uri '{}' in model", resourceURI);

				return null;
			}

			convertToGDMSimpleJSON(recordResource, recordResourceNode, json, json);

			if (json == null) {

				// TODO: maybe log something here

				continue;
			}

			final ObjectNode resourceJson = Util.getJSONObjectMapper().createObjectNode();

			resourceJson.set(resourceURI, json);
			jsonArray.add(resourceJson);
		}

		return jsonArray;
	}

	public static JsonNode toGDMSimpleShortJSON(final Model model, final Set<String> recordURIs) {

		if (model == null) {

			LOG.debug("model is null, can't convert model to GDM simple short JSON");

			return null;
		}

		if (recordURIs == null) {

			LOG.debug("resource URI is null, can't convert model to GDM simple short JSON");

			return null;
		}

		final Iterator<String> iter = recordURIs.iterator();

		if (!iter.hasNext()) {

			// no entries

			return null;
		}

		final ArrayNode jsonArray = Util.getJSONObjectMapper().createArrayNode();
		final Map<String, URI> uriMap = new HashMap<>();

		while (iter.hasNext()) {

			final String resourceURI = iter.next();
			final Resource recordResource = model.getResource(resourceURI);

			if (recordResource == null) {

				LOG.debug("couldn't find record resource for record  uri '{}' in model", resourceURI);

				return null;
			}

			final ObjectNode json = Util.getJSONObjectMapper().createObjectNode();

			// determine record resource node from statements of the record resource
			final ResourceNode recordResourceNode = Util.getResourceNode(resourceURI, recordResource);

			if (recordResourceNode == null) {

				LOG.debug("couldn't find record resource node for record  uri '{}' in model", resourceURI);

				return null;
			}

			convertToGDMSimpleShortJSON(recordResource, recordResourceNode, json, json, uriMap);

			if (json == null) {

				// TODO: maybe log something here

				continue;
			}

			final ObjectNode resourceJson = Util.getJSONObjectMapper().createObjectNode();

			resourceJson.set(resourceURI, json);
			jsonArray.add(resourceJson);
		}

		return jsonArray;
	}

	public static JsonNode toJSON(final Model model, final Set<String> recordURIs) {

		if (model == null) {

			LOG.debug("model is null, can't convert model to JSON");

			return null;
		}

		if (recordURIs == null) {

			LOG.debug("resource URI is null, can't convert model to JSON");

			return null;
		}

		final Iterator<String> iter = recordURIs.iterator();

		if (!iter.hasNext()) {

			// no entries

			return null;
		}

		final ArrayNode jsonArray = Util.getJSONObjectMapper().createArrayNode();
		final Map<String, URI> uriMap = new HashMap<>();

		while (iter.hasNext()) {

			final String resourceURI = iter.next();
			final Resource recordResource = model.getResource(resourceURI);

			if (recordResource == null) {

				LOG.debug("couldn't find record resource for record  uri '{}' in model", resourceURI);

				return null;
			}

			final ObjectNode json = Util.getJSONObjectMapper().createObjectNode();

			// determine record resource node from statements of the record resource
			final ResourceNode recordResourceNode = Util.getResourceNode(resourceURI, recordResource);

			if (recordResourceNode == null) {

				LOG.debug("couldn't find record resource node for record  uri '{}' in model", resourceURI);

				return null;
			}

			convertToGDMSimpleShortJSON(recordResource, recordResourceNode, json, json, uriMap);

			if (json == null) {

				// TODO: maybe log something here

				continue;
			}

			jsonArray.add(json);
		}

		return jsonArray;
	}

	private static JsonNode convertToGDMSimpleJSON(final Resource recordResource,
	                                               final Node resourceNode,
	                                               final ObjectNode rootJson,
	                                               final ObjectNode json) {

		final Map<String, ConverterHelper2> converterHelpers = new LinkedHashMap<>();

		// filter record resource statements to statements for subject uri/id (resource node))
		final Set<Statement> statements = Util.getResourceStatement(resourceNode, recordResource);

		for (final Statement statement : statements) {

			final String propertyURI = statement.getPredicate().getUri();
			final Node gdmNode = statement.getObject();

			if (gdmNode instanceof LiteralNode) {

				ConverterHelperGDMHelper2.addLiteralToConverterHelper(converterHelpers, propertyURI, gdmNode);

				continue;
			}

			if (gdmNode instanceof ResourceNode) {

				final ResourceNode object = (ResourceNode) gdmNode;

				// filter record resource statements to statements for object uri (object node))
				final Set<Statement> objectStatements = Util.getResourceStatement(object, recordResource);

				if (objectStatements == null || objectStatements.isEmpty()) {

					ConverterHelperGDMHelper2.addURIResourceToConverterHelper(converterHelpers, propertyURI, gdmNode);

					continue;
				}

				// resource has an uri, but is deeper in the hierarchy -> it will be attached to the root json node as separate
				// entry

				final ObjectNode objectNode = Util.getJSONObjectMapper().createObjectNode();

				final JsonNode jsonNode = convertToGDMSimpleJSON(recordResource, object, rootJson, objectNode);

				rootJson.set(object.getUri(), jsonNode);

				continue;
			}

			// node is (/must be) a blank node

			final ObjectNode objectNode = Util.getJSONObjectMapper().createObjectNode();

			final JsonNode jsonNode = convertToGDMSimpleJSON(recordResource, gdmNode, rootJson, objectNode);

			ConverterHelperGDMHelper2.addJSONNodeToConverterHelper(converterHelpers, propertyURI, jsonNode);
		}

		for (final Map.Entry<String, ConverterHelper2> converterHelperEntry : converterHelpers.entrySet()) {

			converterHelperEntry.getValue().build(json);
		}

		return json;
	}

	private static JsonNode convertToGDMSimpleShortJSON(final Resource recordResource,
	                                                    final Node resourceNode,
	                                                    final ObjectNode rootJson,
	                                                    final ObjectNode json,
	                                                    final Map<String, URI> uriMap) {

		final Map<String, ConverterHelper2> converterHelpers = new LinkedHashMap<>();

		// filter record resource statements to statements for subject uri/id (resource node))
		final Set<Statement> statements = Util.getResourceStatement(resourceNode, recordResource);

		for (final Statement statement : statements) {

			final String propertyURI = statement.getPredicate().getUri();
			final String localName = getOrAddLocalName(propertyURI, uriMap);
			final Node gdmNode = statement.getObject();

			if (gdmNode instanceof LiteralNode) {

				ConverterHelperGDMHelper2.addLiteralToConverterHelper(converterHelpers, localName, gdmNode);

				continue;
			}

			if (gdmNode instanceof ResourceNode) {

				final ResourceNode object = (ResourceNode) gdmNode;

				// filter record resource statements to statements for object uri (object node))
				final Set<Statement> objectStatements = Util.getResourceStatement(object, recordResource);

				if (objectStatements == null || objectStatements.isEmpty()) {

					ConverterHelperGDMHelper2.addURIResourceToConverterHelper(converterHelpers, localName, gdmNode);

					continue;
				}

				// resource has an uri, but is deeper in the hierarchy -> it will be attached to the root json node as separate
				// entry

				final ObjectNode objectNode = Util.getJSONObjectMapper().createObjectNode();

				final JsonNode jsonNode = convertToGDMSimpleShortJSON(recordResource, object, rootJson, objectNode, uriMap);

				rootJson.set(object.getUri(), jsonNode);

				continue;
			}

			// node is (/must be) a blank node

			final ObjectNode objectNode = Util.getJSONObjectMapper().createObjectNode();

			final JsonNode jsonNode = convertToGDMSimpleShortJSON(recordResource, gdmNode, rootJson, objectNode, uriMap);

			ConverterHelperGDMHelper2.addJSONNodeToConverterHelper(converterHelpers, localName, jsonNode);
		}

		for (final Map.Entry<String, ConverterHelper2> converterHelperEntry : converterHelpers.entrySet()) {

			converterHelperEntry.getValue().build(json);
		}

		return json;
	}


	/**
	 * Gets resource node for the given resource identifier in the given record resource.
	 *
	 * @param resourceURI    the resource identifier
	 * @param recordResource the GDM record resource
	 * @return
	 */
	public static ResourceNode getResourceNode(final String resourceURI,
	                                           final Resource recordResource) {

		if (resourceURI == null || recordResource == null) {

			Util.LOG.debug("resource URI or record resource is null");

			return null;
		}

		final Collection<Statement> statements = recordResource.getStatements();

		if (statements == null || statements.isEmpty()) {

			Util.LOG.debug("record resource contains no statements");

			return null;
		}

		for (final Statement statement : statements) {

			final Node subjectNode = statement.getSubject();

			if (subjectNode == null) {

				// this should never be the case

				continue;
			}

			if (!(subjectNode instanceof ResourceNode)) {

				// only resource nodes are relevant in this game

				continue;
			}

			final ResourceNode subjectResourceNode = (ResourceNode) subjectNode;

			if (resourceURI.equals(subjectResourceNode.getUri())) {

				// match !!!

				return subjectResourceNode;
			}
		}

		return null;
	}

	/**
	 * Gets all statements for the given resource node in the given record resource.
	 *
	 * @param resourceNode   the node that should contain the resource identifier
	 * @param recordResource the GDM record Resource
	 * @return
	 */
	public static Set<Statement> getResourceStatement(final Node resourceNode,
	                                                  final Resource recordResource) {

		if (resourceNode == null || recordResource == null) {

			Util.LOG.debug("resource Node or record resource is null");

			return null;
		}

		if (resourceNode instanceof ResourceNode) {

			final ResourceNode castedResourceNode = (ResourceNode) resourceNode;
			final String resourceURI = castedResourceNode.getUri();

			return Util.getResourceStatement(resourceURI, recordResource);
		}

		final Long resourceId = resourceNode.getId();

		return Util.getResourceStatement(resourceId, recordResource);
	}

	/**
	 * Gets all statements for the given resource identifier in the given record resource.
	 *
	 * @param resourceURI    the resource identifier
	 * @param recordResource the GDM recourd resource
	 * @return
	 */
	public static Set<Statement> getResourceStatement(final String resourceURI,
	                                                  final Resource recordResource) {

		if (resourceURI == null || recordResource == null) {

			Util.LOG.debug("resource URI or record resource is null");

			return null;
		}

		final Collection<Statement> statements = recordResource.getStatements();

		if (statements == null || statements.isEmpty()) {

			Util.LOG.debug("record resource contains no statements");

			return null;
		}

		final Set<Statement> resourceStatements = new LinkedHashSet<>();

		for (final Statement statement : statements) {

			final Node subjectNode = statement.getSubject();

			if (!(subjectNode instanceof ResourceNode)) {

				// only resource nodes are relevant here ..

				continue;
			}

			final ResourceNode subjectResourceNode = (ResourceNode) subjectNode;

			if (!resourceURI.equals(subjectResourceNode.getUri())) {

				// only resource nodes that matches the resource uri are relevant here ..

				continue;
			}

			resourceStatements.add(statement);
		}

		return resourceStatements;
	}

	/**
	 * Gets all statements for the given resource identifier in the given record resource.
	 *
	 * @param resourceId     the resource identifier
	 * @param recordResource the GDM record resource
	 * @return
	 */
	public static Set<Statement> getResourceStatement(final Long resourceId,
	                                                  final Resource recordResource) {

		if (resourceId == null || recordResource == null) {

			Util.LOG.debug("resource id or record resource is null");

			return null;
		}

		final Collection<Statement> statements = recordResource.getStatements();

		if (statements == null || statements.isEmpty()) {

			Util.LOG.debug("record resource contains no statements");

			return null;
		}

		final Set<Statement> resourceStatements = new LinkedHashSet<>();

		for (final Statement statement : statements) {

			final Node subjectNode = statement.getSubject();

			if (subjectNode.getId() == null) {

				// only nodes with id are relevant here ..

				continue;
			}

			if (!resourceId.equals(subjectNode.getId())) {

				// only nodes that matches the resource id are relevant here ..

				continue;
			}

			resourceStatements.add(statement);
		}

		return resourceStatements;
	}

	private static String getOrAddLocalName(final String uri, final Map<String, URI> uriMap) {

		return uriMap.computeIfAbsent(uri, uri1 -> new URI(uri)).getLocalName();
	}
}
