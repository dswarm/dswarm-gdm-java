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
package org.dswarm.graph.json.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;

import org.dswarm.graph.json.LiteralNode;
import org.dswarm.graph.json.Node;
import org.dswarm.graph.json.NodeType;
import org.dswarm.graph.json.Predicate;
import org.dswarm.graph.json.Resource;
import org.dswarm.graph.json.ResourceNode;
import org.dswarm.graph.json.Statement;
import org.dswarm.graph.json.util.Util;

/**
 * @author tgaengler
 */
public class ModelBuilder {

	private final JsonGenerator jg;

	public ModelBuilder(final OutputStream modelStreamArg) throws IOException {

		final JsonFactory jsonFactory = Util.getJSONObjectMapper().getFactory();
		// TODO: maybe add buffered output stream here
		jg = jsonFactory.createGenerator(modelStreamArg, JsonEncoding.UTF8);
		init();
	}

	private void checkNotNull(final Object object,
	                          final String message) throws JsonGenerationException {

		if (object == null) {

			throw new JsonGenerationException(message, jg);
		}
	}

	private void init() throws IOException {

		jg.writeStartArray();
	}

	public void addResource(final Resource resource) throws IOException {

		checkNotNull(resource, "couldn't write Resource JSON, because there is no Resource object");
		checkNotNull(resource.getUri(), "couldn't write Resource JSON, because there is no Resource URI");

		jg.writeStartObject();
		jg.writeFieldName(resource.getUri());
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

		if (order != null) {

			jg.writeNumberField(ModelStatics.ORDER_IDENTIFIER, order);
		}

		final String evidence = statement.getEvidence();

		if (evidence != null) {

			jg.writeStringField(ModelStatics.EVIDENCE_IDENTIFIER, evidence);
		}

		final String confidence = statement.getConfidence();

		if (confidence != null) {

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

		checkNotNull(subject, "couldn't write Statement JSON, because there is no subject");

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
						nodeType.getName()), jg);
		}
	}

	private void addPredicate(final Predicate predicate) throws IOException {

		checkNotNull(predicate, "couldn't write Statement JSON, because there is no predicate");
		checkNotNull(predicate.getUri(), "couldn't write Statement JSON, because there is no predicate URI");

		jg.writeStringField(ModelStatics.PREDICATE_IDENTIFIER, predicate.getUri());
	}

	private void addObject(final Node object) throws IOException {

		checkNotNull(object, "couldn't write Statement JSON, because there is no object");

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
						nodeType.getName()), jg);
		}
	}

	private void addResourceNode(final ResourceNode resourceNode) throws IOException {

		checkNotNull(resourceNode.getUri(), "couldn't write Statement JSON, resource URI needs to be available for URI resource");

		addNodeHead(resourceNode);

		jg.writeStringField(ModelStatics.URI_IDENTIFIER, resourceNode.getUri());

		final String dataModelURI = resourceNode.getDataModel();

		if (dataModelURI != null) {

			jg.writeStringField(ModelStatics.DATA_MODEL_IDENTIFIER, dataModelURI);
		}

		jg.writeEndObject();
	}

	private void addBNode(final Node bnode) throws IOException {

		checkNotNull(bnode.getId(), "couldn't write Statement JSON, bnode id needs to available");

		addNodeHead(bnode);

		jg.writeEndObject();
	}

	private void addLiteralNode(final LiteralNode literalNode) throws IOException {

		checkNotNull(literalNode.getValue(), "couldn't write Statement JSON, literal value needs to be available");

		addNodeHead(literalNode);

		jg.writeStringField(ModelStatics.VALUE_IDENTIFIER, literalNode.getValue());

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
