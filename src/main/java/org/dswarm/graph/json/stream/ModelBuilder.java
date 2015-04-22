/**
 * Copyright (C) 2013 – 2015 SLUB Dresden & Avantgarde Labs GmbH (<code@dswarm.org>)
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
package org.dswarm.graph.json.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import org.dswarm.graph.json.LiteralNode;
import org.dswarm.graph.json.Node;
import org.dswarm.graph.json.NodeType;
import org.dswarm.graph.json.Predicate;
import org.dswarm.graph.json.Resource;
import org.dswarm.graph.json.ResourceNode;
import org.dswarm.graph.json.Statement;
import org.dswarm.graph.json.util.Util;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;

/**
 * @author tgaengler
 */
public class ModelBuilder {

	private final OutputStream	modelStream;
	private final JsonFactory	jsonFactory;
	private final JsonGenerator	jg;

	public ModelBuilder(final OutputStream modelStreamArg) throws IOException {

		modelStream = modelStreamArg;
		jsonFactory = Util.getJSONObjectMapper().getFactory();
		// TODO: maybe add buffered output stream here
		jg = jsonFactory.createGenerator(modelStream, JsonEncoding.UTF8);
		init();
	}

	private void init() throws IOException {

		jg.writeStartArray();
	}

	public void addResource(final Resource resource) throws IOException {

		if (resource == null) {

			throw new JsonGenerationException("couldn't write Resource JSON, because there is on Resource object");
		}

		jg.writeStartObject();

		final String resourceURI = resource.getUri();

		if (resourceURI == null) {

			throw new JsonGenerationException("couldn't write Resource JSON, because there is not Resource URI");
		}

		jg.writeFieldName(resourceURI);

		jg.writeStartArray();

		final Set<Statement> statements = resource.getStatements();

		for (final Statement statement : statements) {

			addStatement(statement);
		}

		jg.writeEndArray();

		jg.writeEndObject();
	}

	/**
	 * note: the output stream won't be closed by this method
	 *
	 * TODO: return output stream, if necessary
	 */
	public void build() throws IOException {

		jg.writeEndArray();
		jg.close();
	}

	private void addStatement(final Statement statement) throws IOException {

		jg.writeStartObject();

		final Long id = statement.getId();

		if (id != null) {

			jg.writeNumberField(ModelStatics.ID_IDENTIFIER, id);
		}

		final String uuid = statement.getUUID();

		if (uuid != null) {

			jg.writeStringField(ModelStatics.UUID_IDENTIFIER, uuid);
		}

		final Long order = statement.getOrder();

		if(order != null) {

			jg.writeNumberField(ModelStatics.ORDER_IDENTIFIER, order);
		}

		final String evidence = statement.getEvidence();

		if(evidence != null) {

			jg.writeStringField(ModelStatics.EVIDENCE_IDENTIFIER, evidence);
		}

		final String confidence = statement.getConfidence();

		if(confidence != null) {

			jg.writeStringField(ModelStatics.CONFIDENCE_IDENTIFIER, confidence);
		}

		final Node subject = statement.getSubject();
		addSubject(subject);

		final Predicate predicate = statement.getPredicate();
		addPredicate(predicate);

		final Node object = statement.getObject();
		addObject(object);

		jg.writeEndObject();
	}

	private void addSubject(final Node subject) throws IOException {

		if (subject == null) {

			throw new JsonGenerationException("couldn't write Statement JSON, because there is no subject");
		}

		jg.writeFieldName(ModelStatics.SUBJECT_IDENTIFIER);

		final NodeType nodeType = subject.getType();

		switch (nodeType) {

			case Resource:

				addResourceNode((ResourceNode) subject);

				break;
			case BNode:

				addBNode(subject);

				break;
			default:

				throw new JsonGenerationException(String.format("couldn't write Statement JSON, didn't expect '%s' node type at subject position",
						nodeType.getName()));
		}
	}

	private void addPredicate(final Predicate predicate) throws IOException {

		if (predicate == null) {

			throw new JsonGenerationException("couldn't write Statement JSON, because there is no predicate");
		}

		final String predicateURI = predicate.getUri();

		if (predicateURI == null) {

			throw new JsonGenerationException("couldn't write Statement JSON, because there is no predicate URI");
		}

		jg.writeStringField(ModelStatics.PREDICATE_IDENTIFIER, predicateURI);
	}

	private void addObject(final Node object) throws IOException {

		if (object == null) {

			throw new JsonGenerationException("couldn't write Statement JSON, because there is no object");
		}

		jg.writeFieldName(ModelStatics.OBJECT_IDENTIFIER);

		final NodeType nodeType = object.getType();

		switch (nodeType) {

			case Resource:

				addResourceNode((ResourceNode) object);

				break;
			case BNode:

				addBNode(object);

				break;
			case Literal:

				addLiteralNode((LiteralNode) object);

				break;
			default:

				throw new JsonGenerationException(String.format("couldn't write Statement JSON, didn't expect '%s' node type at subject position",
						nodeType.getName()));
		}
	}

	private void addResourceNode(final ResourceNode resourceNode) throws IOException {

		addNodeHead(resourceNode);

		final String resourceURI = resourceNode.getUri();

		if (resourceURI == null) {

			throw new JsonGenerationException("couldn't write Statement JSON, resource URI needs to be available for URI resource");
		}

		jg.writeStringField(ModelStatics.URI_IDENTIFIER, resourceURI);

		final String dataModelURI = resourceNode.getDataModel();

		if (dataModelURI != null) {

			jg.writeStringField(ModelStatics.DATA_MODEL_IDENTIFIER, dataModelURI);
		}

		jg.writeEndObject();
	}

	private void addBNode(final Node bnode) throws IOException {

		final Long id = bnode.getId();

		if (id == null) {

			throw new JsonGenerationException("couldn't write Statement JSON, bnode id needs to available");
		}

		addNodeHead(bnode);

		jg.writeEndObject();
	}

	private void addLiteralNode(final LiteralNode literalNode) throws IOException {

		addNodeHead(literalNode);

		final String value = literalNode.getValue();

		if(value == null) {

			throw new JsonGenerationException("couldn't write Statement JSON, literal value needs to be available");
		}

		jg.writeStringField(ModelStatics.VALUE_IDENTIFIER, value);

		jg.writeEndObject();
	}

	private void addNodeHead(final Node node) throws IOException {

		jg.writeStartObject();

		final Long id = node.getId();

		if (id != null) {

			jg.writeNumberField(ModelStatics.ID_IDENTIFIER, id);
		}
	}
}
