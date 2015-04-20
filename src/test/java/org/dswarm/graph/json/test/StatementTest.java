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
package org.dswarm.graph.json.test;

import java.io.IOException;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.Test;

import org.dswarm.graph.json.Node;
import org.dswarm.graph.json.Predicate;
import org.dswarm.graph.json.ResourceNode;
import org.dswarm.graph.json.Statement;
import org.dswarm.graph.json.test.util.TestUtil;
import org.dswarm.graph.json.util.Util;

public class StatementTest {

	@Test
	public void testSerializeStatement() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(1);
		final String statementJSONString = Util.getJSONObjectMapper().writeValueAsString(statement);

		final String expectedJSONString = TestUtil.getResourceAsString("statement.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, statementJSONString);
	}

	@Test
	public void testSerializeStatementWConfidence() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(1);
		statement.setConfidence("20");
		final String statementJSONString = Util.getJSONObjectMapper().writeValueAsString(statement);

		final String expectedJSONString = TestUtil.getResourceAsString("statement_w_confidence.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, statementJSONString);
	}

	@Test
	public void testSerializeStatementWDataModelResource() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new ResourceNode((long) 2, "http://example.com/dataresource", "http://example.com");

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(1);
		final String statementJSONString = Util.getJSONObjectMapper().writeValueAsString(statement);

		final String expectedJSONString = TestUtil.getResourceAsString("statement_w_data_model_resource.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, statementJSONString);
	}

	@Test
	public void testSerializeStatementWDataModelResourceAndEvidence() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new ResourceNode((long) 2, "http://example.com/dataresource", "http://example.com");

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(1);
		statement.setEvidence("0815");
		final String statementJSONString = Util.getJSONObjectMapper().writeValueAsString(statement);

		final String expectedJSONString = TestUtil.getResourceAsString("statement_w_data_model_resource_and_evidence.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, statementJSONString);
	}

	@Test
	public void testSerializeStatementWUUID() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(1);
		statement.setUUID("18d68601-0623-42b4-ad89-f8954cc25912");
		final String statementJSONString = Util.getJSONObjectMapper().writeValueAsString(statement);
		final ObjectNode statementJSON = Util.getJSONObjectMapper().readValue(statementJSONString, ObjectNode.class);

		final String expectedJSONString = TestUtil.getResourceAsString("statementwuuid.json");
		final ObjectNode expectedJSON = Util.getJSONObjectMapper().readValue(expectedJSONString, ObjectNode.class);

		Assert.assertEquals("wrong serialisation", expectedJSON, statementJSON);
	}

	@Test
	public void testSerializeStatementWUUIDWithoutId() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);

		final Statement statement = new Statement(subject, predicate, object);
		statement.setUUID("18d68601-0623-42b4-ad89-f8954cc25912");
		final String statementJSONString = Util.getJSONObjectMapper().writeValueAsString(statement);
		final ObjectNode statementJSON = Util.getJSONObjectMapper().readValue(statementJSONString, ObjectNode.class);

		final String expectedJSONString = TestUtil.getResourceAsString("statementwuuidwithoutid.json");
		final ObjectNode expectedJSON = Util.getJSONObjectMapper().readValue(expectedJSONString, ObjectNode.class);

		Assert.assertEquals("wrong serialisation", expectedJSON, statementJSON);
	}

	@Test
	public void testSerializeStatementwOrder() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);
		final long statementId = 1;
		final long order = 1;

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(statementId);
		statement.setOrder(order);
		final String statementJSONString = Util.getJSONObjectMapper().writeValueAsString(statement);

		final String expectedJSONString = TestUtil.getResourceAsString("statementworder.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, statementJSONString);
	}

	@Test
	public void testSerializeStatementwOrderAndUUID() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);
		final long statementId = 1;
		final long order = 1;

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(statementId);
		statement.setUUID("18d68601-0623-42b4-ad89-f8954cc25912");
		statement.setOrder(order);
		final String statementJSONString = Util.getJSONObjectMapper().writeValueAsString(statement);

		final String expectedJSONString = TestUtil.getResourceAsString("statementworderanduuid.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, statementJSONString);
	}

	@Test
	public void testSerializeStatementwOrderAndUUIDWithoutId() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);
		final long order = 1;

		final Statement statement = new Statement(subject, predicate, object);
		statement.setUUID("18d68601-0623-42b4-ad89-f8954cc25912");
		statement.setOrder(order);
		final String statementJSONString = Util.getJSONObjectMapper().writeValueAsString(statement);

		final String expectedJSONString = TestUtil.getResourceAsString("statementworderanduuidwithoutid.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, statementJSONString);
	}

	@Test
	public void testDeserializeStatement() throws IOException {

		final String statementJSONString = TestUtil.getResourceAsString("statement.json");
		final Statement statement = Util.getJSONObjectMapper().readValue(statementJSONString, Statement.class);

		Assert.assertNotNull("deserialized statement shouldn't be null", statement);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(1);

		Assert.assertEquals("ids of the statements should be equal", expectedStatement.getId(), statement.getId());
		Assert.assertNotNull("subject of the statement shouldn't be null", statement.getSubject());
		Assert.assertEquals("ids of the statements' subjects should be equal", expectedStatement.getSubject().getId(), statement.getSubject().getId());
		Assert.assertEquals("types of the statements' subjects should be equal", expectedStatement.getSubject().getType(), statement.getSubject()
				.getType());
		Assert.assertNotNull("predicate of the statement shouldn't be null", statement.getPredicate());
		Assert.assertEquals("ids of the statements' predicates should be equal", expectedStatement.getPredicate().getUri(), statement.getPredicate()
				.getUri());
		Assert.assertNotNull("object of the statement shouldn't be null", statement.getObject());
		Assert.assertEquals("ids of the statements' objects should be equal", expectedStatement.getObject().getId(), statement.getObject().getId());
		Assert.assertEquals("types of the statements' objects should be equal", expectedStatement.getObject().getType(), statement.getObject()
				.getType());
		Assert.assertEquals("uris of the statements' subjects should be equal", ((ResourceNode) expectedStatement.getSubject()).getUri(),
				((ResourceNode) statement.getSubject()).getUri());
	}

	@Test
	public void testDeserializeStatementWConfidence() throws IOException {

		final String statementJSONString = TestUtil.getResourceAsString("statement_w_confidence.json");
		final Statement statement = Util.getJSONObjectMapper().readValue(statementJSONString, Statement.class);

		Assert.assertNotNull("deserialized statement shouldn't be null", statement);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(1);
		expectedStatement.setConfidence("20");

		Assert.assertEquals("ids of the statements should be equal", expectedStatement.getId(), statement.getId());
		Assert.assertNotNull("subject of the statement shouldn't be null", statement.getSubject());
		Assert.assertEquals("ids of the statements' subjects should be equal", expectedStatement.getSubject().getId(), statement.getSubject().getId());
		Assert.assertEquals("types of the statements' subjects should be equal", expectedStatement.getSubject().getType(), statement.getSubject()
				.getType());
		Assert.assertNotNull("predicate of the statement shouldn't be null", statement.getPredicate());
		Assert.assertEquals("ids of the statements' predicates should be equal", expectedStatement.getPredicate().getUri(), statement.getPredicate()
				.getUri());
		Assert.assertNotNull("object of the statement shouldn't be null", statement.getObject());
		Assert.assertEquals("ids of the statements' objects should be equal", expectedStatement.getObject().getId(), statement.getObject().getId());
		Assert.assertEquals("types of the statements' objects should be equal", expectedStatement.getObject().getType(), statement.getObject()
				.getType());
		Assert.assertEquals("uris of the statements' subjects should be equal", ((ResourceNode) expectedStatement.getSubject()).getUri(),
				((ResourceNode) statement.getSubject()).getUri());
		Assert.assertNotNull("subject of the confidence shouldn't be null", statement.getConfidence());
		Assert.assertEquals("confidences of the statements should be equal", expectedStatement.getConfidence(), statement.getConfidence());
	}

	@Test
	public void testDeserializeStatementWDataModelResource() throws IOException {

		final String statementJSONString = TestUtil.getResourceAsString("statement_w_data_model_resource.json");
		final Statement statement = Util.getJSONObjectMapper().readValue(statementJSONString, Statement.class);

		Assert.assertNotNull("deserialized statement shouldn't be null", statement);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new ResourceNode((long) 2, "http://example.com/dataresource", "http://example.com");

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(1);

		Assert.assertEquals("ids of the statements should be equal", expectedStatement.getId(), statement.getId());
		Assert.assertNotNull("subject of the statement shouldn't be null", statement.getSubject());
		Assert.assertEquals("ids of the statements' subjects should be equal", expectedStatement.getSubject().getId(), statement.getSubject().getId());
		Assert.assertEquals("types of the statements' subjects should be equal", expectedStatement.getSubject().getType(), statement.getSubject()
				.getType());
		Assert.assertNotNull("predicate of the statement shouldn't be null", statement.getPredicate());
		Assert.assertEquals("ids of the statements' predicates should be equal", expectedStatement.getPredicate().getUri(), statement.getPredicate()
				.getUri());
		Assert.assertNotNull("object of the statement shouldn't be null", statement.getObject());
		Assert.assertEquals("ids of the statements' objects should be equal", expectedStatement.getObject().getId(), statement.getObject().getId());
		Assert.assertEquals("types of the statements' objects should be equal", expectedStatement.getObject().getType(), statement.getObject()
				.getType());
		Assert.assertNotNull(((ResourceNode) expectedStatement.getObject()).getDataModel());
		Assert.assertEquals("data models of the statements' objects should be equal", ((ResourceNode) expectedStatement.getObject()).getDataModel(), ((ResourceNode) statement.getObject()).getDataModel());
		Assert.assertEquals("uris of the statements' subjects should be equal", ((ResourceNode) expectedStatement.getSubject()).getUri(),
				((ResourceNode) statement.getSubject()).getUri());
	}

	@Test
	public void testDeserializeStatementWDataModelResourceAndEvidence() throws IOException {

		final String statementJSONString = TestUtil.getResourceAsString("statement_w_data_model_resource_and_evidence.json");
		final Statement statement = Util.getJSONObjectMapper().readValue(statementJSONString, Statement.class);

		Assert.assertNotNull("deserialized statement shouldn't be null", statement);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new ResourceNode((long) 2, "http://example.com/dataresource", "http://example.com");

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(1);
		expectedStatement.setEvidence("0815");

		Assert.assertEquals("ids of the statements should be equal", expectedStatement.getId(), statement.getId());
		Assert.assertNotNull("subject of the statement shouldn't be null", statement.getSubject());
		Assert.assertEquals("ids of the statements' subjects should be equal", expectedStatement.getSubject().getId(), statement.getSubject().getId());
		Assert.assertEquals("types of the statements' subjects should be equal", expectedStatement.getSubject().getType(), statement.getSubject()
				.getType());
		Assert.assertNotNull("predicate of the statement shouldn't be null", statement.getPredicate());
		Assert.assertEquals("ids of the statements' predicates should be equal", expectedStatement.getPredicate().getUri(), statement.getPredicate()
				.getUri());
		Assert.assertNotNull("object of the statement shouldn't be null", statement.getObject());
		Assert.assertEquals("ids of the statements' objects should be equal", expectedStatement.getObject().getId(), statement.getObject().getId());
		Assert.assertEquals("types of the statements' objects should be equal", expectedStatement.getObject().getType(), statement.getObject()
				.getType());
		Assert.assertNotNull(((ResourceNode) expectedStatement.getObject()).getDataModel());
		Assert.assertEquals("data models of the statements' objects should be equal", ((ResourceNode) expectedStatement.getObject()).getDataModel(), ((ResourceNode) statement.getObject()).getDataModel());
		Assert.assertEquals("uris of the statements' subjects should be equal", ((ResourceNode) expectedStatement.getSubject()).getUri(),
				((ResourceNode) statement.getSubject()).getUri());
		Assert.assertNotNull("evidence of the statement shouldn't be null", statement.getEvidence());
		Assert.assertEquals("evidences of the statements should be equal", expectedStatement.getEvidence(), statement.getEvidence());
	}

	@Test
	public void testDeserializeStatementWUUID() throws IOException {

		final String statementJSONString = TestUtil.getResourceAsString("statementwuuid.json");
		final Statement statement = Util.getJSONObjectMapper().readValue(statementJSONString, Statement.class);

		Assert.assertNotNull("deserialized statement shouldn't be null", statement);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate,
				expectedObject);
		expectedStatement.setId(1);
		expectedStatement.setUUID("18d68601-0623-42b4-ad89-f8954cc25912");

		Assert.assertEquals("ids of the statements should be equal", expectedStatement.getId(), statement.getId());
		Assert.assertNotNull("uuid of the statement shouldn't be null", statement.getUUID());
		Assert.assertEquals("uuids of the statements should be equal", expectedStatement.getUUID(), statement.getUUID());
		Assert.assertNotNull("subject of the statement shouldn't be null", statement.getSubject());
		Assert.assertEquals("ids of the statements' subjects should be equal", expectedStatement.getSubject().getId(), statement.getSubject().getId());
		Assert.assertEquals("types of the statements' subjects should be equal", expectedStatement.getSubject().getType(), statement.getSubject()
				.getType());
		Assert.assertNotNull("predicate of the statement shouldn't be null", statement.getPredicate());
		Assert.assertEquals("ids of the statements' predicates should be equal", expectedStatement.getPredicate().getUri(), statement.getPredicate()
				.getUri());
		Assert.assertNotNull("object of the statement shouldn't be null", statement.getObject());
		Assert.assertEquals("ids of the statements' objects should be equal", expectedStatement.getObject().getId(), statement.getObject().getId());
		Assert.assertEquals("types of the statements' objects should be equal", expectedStatement.getObject().getType(), statement.getObject()
				.getType());
		Assert.assertEquals("uris of the statements' subjects should be equal", ((ResourceNode) expectedStatement.getSubject()).getUri(),
				((ResourceNode) statement.getSubject()).getUri());
	}

	@Test
	public void testDeserializeStatementWUUIDWithoutId() throws IOException {

		final String statementJSONString = TestUtil.getResourceAsString("statementwuuidwithoutid.json");
		final Statement statement = Util.getJSONObjectMapper().readValue(statementJSONString, Statement.class);

		Assert.assertNotNull("deserialized statement shouldn't be null", statement);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setUUID("18d68601-0623-42b4-ad89-f8954cc25912");

		Assert.assertNull("id of the statement should be null", statement.getId());
		Assert.assertEquals("ids of the statements should be equal", expectedStatement.getId(), statement.getId());
		Assert.assertNotNull("uuid of the statement shouldn't be null", statement.getUUID());
		Assert.assertEquals("uuids of the statements should be equal", expectedStatement.getUUID(), statement.getUUID());
		Assert.assertNotNull("subject of the statement shouldn't be null", statement.getSubject());
		Assert.assertEquals("ids of the statements' subjects should be equal", expectedStatement.getSubject().getId(), statement.getSubject().getId());
		Assert.assertEquals("types of the statements' subjects should be equal", expectedStatement.getSubject().getType(), statement.getSubject()
				.getType());
		Assert.assertNotNull("predicate of the statement shouldn't be null", statement.getPredicate());
		Assert.assertEquals("ids of the statements' predicates should be equal", expectedStatement.getPredicate().getUri(), statement.getPredicate()
				.getUri());
		Assert.assertNotNull("object of the statement shouldn't be null", statement.getObject());
		Assert.assertEquals("ids of the statements' objects should be equal", expectedStatement.getObject().getId(), statement.getObject().getId());
		Assert.assertEquals("types of the statements' objects should be equal", expectedStatement.getObject().getType(), statement.getObject()
				.getType());
		Assert.assertEquals("uris of the statements' subjects should be equal", ((ResourceNode) expectedStatement.getSubject()).getUri(),
				((ResourceNode) statement.getSubject()).getUri());
	}

	@Test
	public void testDeserializeStatementWOrder() throws IOException {

		final String statementJSONString = TestUtil.getResourceAsString("statementworder.json");
		final Statement statement = Util.getJSONObjectMapper().readValue(statementJSONString, Statement.class);

		Assert.assertNotNull("deserialized statement shouldn't be null", statement);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);
		final long statementId = 1;
		final long order = 1;

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(statementId);
		expectedStatement.setOrder(order);

		Assert.assertEquals("ids of the statements should be equal", expectedStatement.getId(), statement.getId());
		Assert.assertNotNull("subject of the statement shouldn't be null", statement.getSubject());
		Assert.assertEquals("ids of the statements' subjects should be equal", expectedStatement.getSubject().getId(), statement.getSubject().getId());
		Assert.assertEquals("types of the statements' subjects should be equal", expectedStatement.getSubject().getType(), statement.getSubject()
				.getType());
		Assert.assertNotNull("predicate of the statement shouldn't be null", statement.getPredicate());
		Assert.assertEquals("ids of the statements' predicates should be equal", expectedStatement.getPredicate().getUri(), statement.getPredicate()
				.getUri());
		Assert.assertNotNull("object of the statement shouldn't be null", statement.getObject());
		Assert.assertEquals("ids of the statements' objects should be equal", expectedStatement.getObject().getId(), statement.getObject().getId());
		Assert.assertEquals("types of the statements' objects should be equal", expectedStatement.getObject().getType(), statement.getObject()
				.getType());
		Assert.assertEquals("uris of the statements' subjects should be equal", ((ResourceNode) expectedStatement.getSubject()).getUri(),
				((ResourceNode) statement.getSubject()).getUri());
		Assert.assertNotNull("order of the statement shouldn't be null", statement.getOrder());
		Assert.assertEquals("orders of the statements should be equal", expectedStatement.getOrder(), statement.getOrder());
	}

	@Test
	public void testDeserializeStatementWOrderAndUUID() throws IOException {

		final String statementJSONString = TestUtil.getResourceAsString("statementworderanduuid.json");
		final Statement statement = Util.getJSONObjectMapper().readValue(statementJSONString, Statement.class);

		Assert.assertNotNull("deserialized statement shouldn't be null", statement);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);
		final long statementId = 1;
		final long order = 1;
		final String uuid = "18d68601-0623-42b4-ad89-f8954cc25912";

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(statementId);
		expectedStatement.setUUID(uuid);
		expectedStatement.setOrder(order);

		Assert.assertEquals("ids of the statements should be equal", expectedStatement.getId(), statement.getId());
		Assert.assertNotNull("uuid of the statement shouldn't be null", statement.getUUID());
		Assert.assertEquals("uuids of the statements should be equal", expectedStatement.getUUID(), statement.getUUID());
		Assert.assertNotNull("subject of the statement shouldn't be null", statement.getSubject());
		Assert.assertEquals("ids of the statements' subjects should be equal", expectedStatement.getSubject().getId(), statement.getSubject().getId());
		Assert.assertEquals("types of the statements' subjects should be equal", expectedStatement.getSubject().getType(), statement.getSubject()
				.getType());
		Assert.assertNotNull("predicate of the statement shouldn't be null", statement.getPredicate());
		Assert.assertEquals("ids of the statements' predicates should be equal", expectedStatement.getPredicate().getUri(), statement.getPredicate()
				.getUri());
		Assert.assertNotNull("object of the statement shouldn't be null", statement.getObject());
		Assert.assertEquals("ids of the statements' objects should be equal", expectedStatement.getObject().getId(), statement.getObject().getId());
		Assert.assertEquals("types of the statements' objects should be equal", expectedStatement.getObject().getType(), statement.getObject()
				.getType());
		Assert.assertEquals("uris of the statements' subjects should be equal", ((ResourceNode) expectedStatement.getSubject()).getUri(),
				((ResourceNode) statement.getSubject()).getUri());
		Assert.assertNotNull("order of the statement shouldn't be null", statement.getOrder());
		Assert.assertEquals("orders of the statements should be equal", expectedStatement.getOrder(), statement.getOrder());
	}

	@Test
	public void testDeserializeStatementWOrderAndUUIDWithoutId() throws IOException {

		final String statementJSONString = TestUtil.getResourceAsString("statementworderanduuidwithoutid.json");
		final Statement statement = Util.getJSONObjectMapper().readValue(statementJSONString, Statement.class);

		Assert.assertNotNull("deserialized statement shouldn't be null", statement);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);
		final long order = 1;
		final String uuid = "18d68601-0623-42b4-ad89-f8954cc25912";

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setUUID(uuid);
		expectedStatement.setOrder(order);

		Assert.assertNull("id of the statement should be null", statement.getId());
		Assert.assertEquals("ids of the statements should be equal", expectedStatement.getId(), statement.getId());
		Assert.assertNotNull("uuid of the statement shouldn't be null", statement.getUUID());
		Assert.assertEquals("uuids of the statements should be equal", expectedStatement.getUUID(), statement.getUUID());
		Assert.assertNotNull("subject of the statement shouldn't be null", statement.getSubject());
		Assert.assertEquals("ids of the statements' subjects should be equal", expectedStatement.getSubject().getId(), statement.getSubject().getId());
		Assert.assertEquals("types of the statements' subjects should be equal", expectedStatement.getSubject().getType(), statement.getSubject()
				.getType());
		Assert.assertNotNull("predicate of the statement shouldn't be null", statement.getPredicate());
		Assert.assertEquals("ids of the statements' predicates should be equal", expectedStatement.getPredicate().getUri(), statement.getPredicate()
				.getUri());
		Assert.assertNotNull("object of the statement shouldn't be null", statement.getObject());
		Assert.assertEquals("ids of the statements' objects should be equal", expectedStatement.getObject().getId(), statement.getObject().getId());
		Assert.assertEquals("types of the statements' objects should be equal", expectedStatement.getObject().getType(), statement.getObject()
				.getType());
		Assert.assertEquals("uris of the statements' subjects should be equal", ((ResourceNode) expectedStatement.getSubject()).getUri(),
				((ResourceNode) statement.getSubject()).getUri());
		Assert.assertNotNull("order of the statement shouldn't be null", statement.getOrder());
		Assert.assertEquals("orders of the statements should be equal", expectedStatement.getOrder(), statement.getOrder());
	}
}
