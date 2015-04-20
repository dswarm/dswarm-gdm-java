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
import java.io.InputStream;

import org.dswarm.graph.json.LiteralNode;
import org.dswarm.graph.json.Node;
import org.dswarm.graph.json.Predicate;
import org.dswarm.graph.json.Resource;
import org.dswarm.graph.json.ResourceNode;
import org.dswarm.graph.json.Statement;
import org.dswarm.graph.json.util.Util;

import rx.Observable;
import rx.Subscriber;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * @author tgaengler
 */
public class ModelParser {

	private static final String	ID_IDENTIFIER			= "id";
	private static final String	UUID_IDENTIFIER			= "uuid";

	private static final String	SUBJECT_IDENTIFIER		= "s";
	private static final String	PREDICATE_IDENTIFIER	= "p";
	private static final String	OBJECT_IDENTIFIER		= "o";

	private static final String	URI_IDENTIFIER			= "uri";
	private static final String	DATA_MODEL_IDENTIFIER	= "data_model";

	private static final String	VALUE_IDENTIFIER		= "v";

	private static final String	ORDER_IDENTIFIER		= "order";
	private static final String	EVIDENCE_IDENTIFIER		= "evidence";
	private static final String	CONFIDENCE_IDENTIFIER	= "confidence";

	private final InputStream	modelStream;
	private final JsonFactory	jsonFactory;

	public ModelParser(final InputStream modelStreamArg) {

		modelStream = modelStreamArg;
		jsonFactory = Util.getJSONObjectMapper().getFactory();
	}

	public Observable<Resource> parse() {

		return Observable.create(new Observable.OnSubscribe<Resource>() {

			@Override
			public void call(final Subscriber<? super Resource> subscriber) {

				subscriber.onStart();

				try {

					final JsonParser jp = jsonFactory.createParser(modelStream);

					jp.nextToken();

					if (!jp.hasCurrentToken()) {

						subscriber.onError(new JsonParseException("cannot parse Model JSON, stream is empty", jp.getCurrentLocation()));
					}

					if (jp.getCurrentToken() != JsonToken.START_ARRAY) {

						subscriber.onError(new JsonParseException(String.format("cannot parse Model JSON, couldn't find beginning, found '%s'",
								jp.getCurrentToken()), jp.getCurrentLocation()));
					}

					parseResources(subscriber, jp);

				} catch (final IOException e) {

					subscriber.onError(e);
				}
			}
		});
	}

	private void parseResources(final Subscriber<? super Resource> subscriber, final JsonParser jp) {

		try {

			while (jp.nextToken() != JsonToken.END_ARRAY && !subscriber.isUnsubscribed()) {

				subscriber.onNext(parseResource(jp));
			}

			subscriber.onCompleted();
			subscriber.unsubscribe();
		} catch (final IOException e) {

			subscriber.onError(e);
		}
	}

	private Resource parseResource(final JsonParser jp) throws IOException {

		if (!jp.hasCurrentToken()) {

			throw new JsonParseException("cannot parse Resource JSON, stream is empty", jp.getCurrentLocation());
		}

		if (jp.getCurrentToken() != JsonToken.START_OBJECT) {

			throw new JsonParseException(String.format("cannot parse Resource JSON, couldn't find beginning; expected '{' but found '%s'",
					jp.getCurrentToken()), jp.getCurrentLocation());
		}

		jp.nextToken();

		final String resourceURI = jp.getCurrentName();

		if (resourceURI == null || resourceURI.trim().isEmpty()) {

			throw new JsonParseException("cannot parse Resource JSON, resource URI is empty", jp.getCurrentLocation());
		}

		jp.nextToken();

		if (!jp.hasCurrentToken()) {

			throw new JsonParseException("cannot parse Resource JSON, stream is empty", jp.getCurrentLocation());
		}

		if (jp.getCurrentToken() != JsonToken.START_ARRAY) {

			throw new JsonParseException(String.format(
					"cannot parse Resource JSON, couldn't find statement array beginning; expected '[' but found '%s'", jp.getCurrentToken()),
					jp.getCurrentLocation());
		}

		final Resource resource = new Resource(resourceURI);

		while (jp.nextToken() != JsonToken.END_ARRAY) {

			final Statement statement = parseStatement(jp);

			resource.addStatement(statement);
		}

		if (jp.nextToken() != JsonToken.END_OBJECT) {

			throw new JsonParseException(String.format("cannot parse Resource JSON, couldn't find ending; expected '}' but found '%s'",
					jp.getCurrentToken()), jp.getCurrentLocation());
		}

		return resource;
	}

	private Statement parseStatement(final JsonParser jp) throws IOException {

		if (!jp.hasCurrentToken()) {

			throw new JsonParseException("cannot parse Statement JSON, stream is empty", jp.getCurrentLocation());
		}

		if (jp.getCurrentToken() != JsonToken.START_OBJECT) {

			throw new JsonParseException(String.format("cannot parse Statement JSON, couldn't find beginning; expected '{' but found '%s'",
					jp.getCurrentToken()), jp.getCurrentLocation());
		}

		Long id = null;
		String uuid = null;
		Node subject = null;
		Predicate predicate = null;
		Node object = null;
		Long order = null;
		String evidence = null;
		String confidence = null;

		while (jp.nextToken() != JsonToken.END_OBJECT) {

			if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

				final String nextField = jp.getCurrentName();

				switch (nextField) {

					case ID_IDENTIFIER:

						id = getLongValue(jp);

						break;
					case UUID_IDENTIFIER:

						uuid = getStringValue(jp);

						break;
					case SUBJECT_IDENTIFIER:

						subject = getSubjectNode(jp, nextField);

						break;
					case PREDICATE_IDENTIFIER:

						predicate = getPredicate(jp, nextField);

						break;
					case OBJECT_IDENTIFIER:

						object = getObjectNode(jp, nextField);

						break;
					case ORDER_IDENTIFIER:

						order = getLongValue(jp);

						break;
					case EVIDENCE_IDENTIFIER:

						evidence = getStringValue(jp);

						break;
					case CONFIDENCE_IDENTIFIER:

						confidence = getStringValue(jp);

						break;
					default:

						throw new JsonParseException(String.format(
								"unexpected JSON token; expected one of '%s','%s','%s','%s','%s','%s','%s','%s', for this statement, but found '%s'",
								ID_IDENTIFIER, UUID_IDENTIFIER, SUBJECT_IDENTIFIER, PREDICATE_IDENTIFIER, OBJECT_IDENTIFIER, ORDER_IDENTIFIER,
								EVIDENCE_IDENTIFIER, CONFIDENCE_IDENTIFIER, jp.getCurrentToken()), jp.getCurrentLocation());
				}
			}
		}

		if (jp.getCurrentToken() == JsonToken.END_OBJECT) {

			return createStatement(id, uuid, subject, predicate, object, order, evidence, confidence);
		}

		throw new JsonParseException(String.format("unexpected JSON token; expected end of object for this statement, but found '%s'",
				jp.getCurrentToken()), jp.getCurrentLocation());
	}

	private Statement createStatement(final Long id, final String uuid, final Node subject, final Predicate predicate, final Node object,
			final Long order, final String evidence, final String confidence) {

		final Statement st = new Statement(subject, predicate, object);

		if (id != null) {

			st.setId(id);
		}

		st.setUUID(uuid);
		st.setOrder(order);
		st.setEvidence(evidence);
		st.setConfidence(confidence);

		return st;
	}

	private Long getId(final JsonParser jp, final String nextField) throws IOException {

		return getLongValue(jp, nextField, ID_IDENTIFIER);
	}

	private Node getSubjectNode(final JsonParser jp, final String nextField) throws IOException {

		if (!SUBJECT_IDENTIFIER.equals(nextField)) {

			throw new JsonParseException(String.format("unexpected JSON token; expected subject node, but found '%s'", jp.getCurrentToken()),
					jp.getCurrentLocation());
		}

		jp.nextToken();

		if (!jp.hasCurrentToken()) {

			throw new JsonParseException("cannot parse subject node JSON, stream is empty", jp.getCurrentLocation());
		}

		if (jp.getCurrentToken() != JsonToken.START_OBJECT) {

			throw new JsonParseException(String.format("cannot parse subject node JSON, couldn't find beginning; expected '{' but found '%s'",
					jp.getCurrentToken()), jp.getCurrentLocation());
		}

		String subjectNextField = getNextField(jp);

		final Long id = getId(jp, subjectNextField);

		if (id != null && jp.nextToken() != JsonToken.END_OBJECT) {

			subjectNextField = jp.getCurrentName();
		} else if (id != null) {

			// bnode
			return new Node(id);
		}

		final String resourceURI = getURI(jp, subjectNextField);

		if (jp.nextToken() == JsonToken.END_OBJECT) {

			if (id != null) {

				return new ResourceNode(id, resourceURI);
			} else {

				return new ResourceNode(resourceURI);
			}
		}

		subjectNextField = jp.getCurrentName();

		final String dataModelURI = getDataModel(jp, subjectNextField);

		if (jp.nextToken() == JsonToken.END_OBJECT) {

			if (id != null) {

				return new ResourceNode(id, resourceURI, dataModelURI);
			} else {

				return new ResourceNode(resourceURI, dataModelURI);
			}
		}

		throw new JsonParseException(String.format("unexpected JSON token; expected end of object for this subject node, but found '%s'",
				jp.getCurrentToken()), jp.getCurrentLocation());
	}

	private Predicate getPredicate(final JsonParser jp, final String nextField) throws IOException {

		final String predicateURI = getStringValue(jp, nextField, PREDICATE_IDENTIFIER);

		return new Predicate(predicateURI);
	}

	private Node getObjectNode(final JsonParser jp, final String nextField) throws IOException {

		if (!OBJECT_IDENTIFIER.equals(nextField)) {

			throw new JsonParseException(String.format("unexpected JSON token; expected object node, but found '%s'", jp.getCurrentToken()),
					jp.getCurrentLocation());
		}

		jp.nextToken();

		if (!jp.hasCurrentToken()) {

			throw new JsonParseException("cannot parse object node JSON, stream is empty", jp.getCurrentLocation());
		}

		if (jp.getCurrentToken() != JsonToken.START_OBJECT) {

			throw new JsonParseException(String.format("cannot parse object node JSON, couldn't find beginning; expected '{' but found '%s'",
					jp.getCurrentToken()), jp.getCurrentLocation());
		}

		String objectNextField = getNextField(jp);

		final Long id = getId(jp, objectNextField);

		if (id != null && jp.nextToken() != JsonToken.END_OBJECT) {

			objectNextField = jp.getCurrentName();
		} else if (id != null) {

			// bnode
			return new Node(id);
		}

		final String resourceURI = getURI(jp, objectNextField);

		if (resourceURI != null && jp.nextToken() == JsonToken.END_OBJECT) {

			if (id != null) {

				return new ResourceNode(id, resourceURI);
			} else {

				return new ResourceNode(resourceURI);
			}
		} else if (resourceURI == null) {

			// literal
			final String literalValue = getValue(jp, objectNextField);

			if (jp.nextToken() == JsonToken.END_OBJECT) {

				if (id != null) {

					return new LiteralNode(id, literalValue);
				} else {

					return new LiteralNode(literalValue);
				}
			}

			throw new JsonParseException(String.format("unexpected JSON token; expected end of object for this object literal node, but found '%s'",
					jp.getCurrentToken()), jp.getCurrentLocation());
		}

		objectNextField = jp.getCurrentName();

		final String dataModelURI = getDataModel(jp, objectNextField);

		if (jp.nextToken() == JsonToken.END_OBJECT) {

			if (id != null) {

				return new ResourceNode(id, resourceURI, dataModelURI);
			} else {

				return new ResourceNode(resourceURI, dataModelURI);
			}
		}

		throw new JsonParseException(String.format("unexpected JSON token; expected end of object for this object resource node, but found '%s'",
				jp.getCurrentToken()), jp.getCurrentLocation());
	}

	private Long getLongValue(final JsonParser jp, final String nextField, final String identifier) throws IOException {

		if (identifier.equals(nextField)) {

			jp.nextToken();

			return jp.getValueAsLong();
		}

		return null;
	}

	private Long getLongValue(final JsonParser jp) throws IOException {

		jp.nextToken();
		return jp.getValueAsLong();
	}

	private String getURI(final JsonParser jp, final String nextField) throws IOException {

		return getStringValue(jp, nextField, URI_IDENTIFIER);
	}

	private String getDataModel(final JsonParser jp, final String nextField) throws IOException {

		return getStringValue(jp, nextField, DATA_MODEL_IDENTIFIER);
	}

	private String getValue(final JsonParser jp, final String nextField) throws IOException {

		return getStringValue(jp, nextField, VALUE_IDENTIFIER);
	}

	private String getStringValue(final JsonParser jp, final String nextField, final String identifier) throws IOException {

		if (identifier.equals(nextField)) {

			jp.nextToken();

			return jp.getText();
		}

		return null;
	}

	private String getStringValue(final JsonParser jp) throws IOException {

		jp.nextToken();
		return jp.getText();
	}

	private String getNextField(final JsonParser jp) throws IOException {

		jp.nextToken();
		return jp.getCurrentName();
	}

}
