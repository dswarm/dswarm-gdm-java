/**
 * Copyright (C) 2013, 2014 SLUB Dresden & Avantgarde Labs GmbH (<code@dswarm.org>)
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

import org.junit.Assert;
import org.junit.Test;

import org.dswarm.graph.json.Model;
import org.dswarm.graph.json.Node;
import org.dswarm.graph.json.Predicate;
import org.dswarm.graph.json.Resource;
import org.dswarm.graph.json.ResourceNode;
import org.dswarm.graph.json.Statement;
import org.dswarm.graph.json.test.util.TestUtil;
import org.dswarm.graph.json.util.Util;

public class ModelTest {

	@Test
	public void testSerializeModel() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(1);
		final Resource resource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		resource.addStatement(statement);
		final Model model = new Model();
		model.addResource(resource);
		final String modelJSONString = Util.getJSONObjectMapper().writeValueAsString(model);

		final String expectedJSONString = TestUtil.getResourceAsString("model.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, modelJSONString);
	}

	@Test
	public void testSerializeModelWResourceWStatementWUUID() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(1);
		statement.setUUID("18d68601-0623-42b4-ad89-f8954cc25912");
		final Resource resource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		resource.addStatement(statement);
		final Model model = new Model();
		model.addResource(resource);
		final String modelJSONString = Util.getJSONObjectMapper().writeValueAsString(model);

		final String expectedJSONString = TestUtil.getResourceAsString("modelwresourcewstatementwuuid.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, modelJSONString);
	}

	@Test
	public void testSerializeModelWResourceWStatementWOrder() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);
		final long statementId = 1;
		final long order = 1;

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(statementId);
		statement.setOrder(order);
		final Resource resource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		resource.addStatement(statement);
		final Model model = new Model();
		model.addResource(resource);
		final String modelJSONString = Util.getJSONObjectMapper().writeValueAsString(model);

		final String expectedJSONString = TestUtil.getResourceAsString("modelwresourcewstatementworder.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, modelJSONString);
	}

	@Test
	public void testSerializeModelWResourceWStatementWOrderAndUUID() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);
		final long statementId = 1;
		final long order = 1;

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(statementId);
		statement.setUUID("18d68601-0623-42b4-ad89-f8954cc25912");
		statement.setOrder(order);
		final Resource resource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		resource.addStatement(statement);
		final Model model = new Model();
		model.addResource(resource);
		final String modelJSONString = Util.getJSONObjectMapper().writeValueAsString(model);

		final String expectedJSONString = TestUtil.getResourceAsString("modelwresourcewstatementworderanduuid.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, modelJSONString);
	}

	@Test
	public void testDeserializeModel() throws IOException {

		final String modelJSONString = TestUtil.getResourceAsString("model.json");
		final Model model = Util.getJSONObjectMapper().readValue(modelJSONString, Model.class);

		Assert.assertNotNull("deserialized model shouldn't be null", model);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(1);

		final Resource expectedResource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		expectedResource.addStatement(expectedStatement);
		final Model expectedModel = new Model();
		expectedModel.addResource(expectedResource);

		Assert.assertNotNull("model shouldn't be null", model);
		Assert.assertNotNull("model resources shouldn't be null", model.getResources());
		Assert.assertEquals("resource size of the model should be equal", expectedModel.getResources().size(), model.getResources().size());

		final Resource resource = model.getResources().iterator().next();

		Assert.assertEquals("uris of the resource should be equal", expectedResource.getUri(), resource.getUri());
		Assert.assertNotNull("there should be some statements for the resource", resource.getStatements());
		Assert.assertEquals("statement size of the resource should be equal", expectedResource.getStatements().size(), resource.getStatements()
				.size());
		Assert.assertEquals("ids of the statements should be equal", expectedStatement.getId(), resource.getStatements().iterator().next().getId());
		Assert.assertNotNull("subject of the statement shouldn't be null", resource.getStatements().iterator().next().getSubject());
		Assert.assertEquals("ids of the statements' subjects should be equal", expectedStatement.getSubject().getId(), resource.getStatements()
				.iterator().next().getSubject().getId());
		Assert.assertEquals("types of the statements' subjects should be equal", expectedStatement.getSubject().getType(), resource.getStatements()
				.iterator().next().getSubject().getType());
		Assert.assertNotNull("predicate of the statement shouldn't be null", resource.getStatements().iterator().next().getPredicate());
		Assert.assertEquals("ids of the statements' predicates should be equal", expectedStatement.getPredicate().getUri(), resource.getStatements()
				.iterator().next().getPredicate().getUri());
		Assert.assertNotNull("object of the statement shouldn't be null", resource.getStatements().iterator().next().getObject());
		Assert.assertEquals("ids of the statements' objects should be equal", expectedStatement.getObject().getId(), resource.getStatements()
				.iterator().next().getObject().getId());
		Assert.assertEquals("types of the statements' objects should be equal", expectedStatement.getObject().getType(), resource.getStatements()
				.iterator().next().getObject().getType());
		Assert.assertEquals("uris of the statements' subjects should be equal", ((ResourceNode) expectedStatement.getSubject()).getUri(),
				((ResourceNode) resource.getStatements().iterator().next().getSubject()).getUri());
	}

	@Test
	public void testDeserializeModelWResourceWStatementWUUID() throws IOException {

		final String modelJSONString = TestUtil.getResourceAsString("modelwresourcewstatementwuuid.json");
		final Model model = Util.getJSONObjectMapper().readValue(modelJSONString, Model.class);

		Assert.assertNotNull("deserialized model shouldn't be null", model);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(1);
		expectedStatement.setUUID("18d68601-0623-42b4-ad89-f8954cc25912");

		final Resource expectedResource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		expectedResource.addStatement(expectedStatement);
		final Model expectedModel = new Model();
		expectedModel.addResource(expectedResource);

		Assert.assertNotNull("model shouldn't be null", model);
		Assert.assertNotNull("model resources shouldn't be null", model.getResources());
		Assert.assertEquals("resource size of the model should be equal", expectedModel.getResources().size(), model.getResources().size());

		final Resource resource = model.getResources().iterator().next();

		Assert.assertEquals("uris of the resource should be equal", expectedResource.getUri(), resource.getUri());
		Assert.assertNotNull("there should be some statements for the resource", resource.getStatements());
		Assert.assertEquals("statement size of the resource should be equal", expectedResource.getStatements().size(), resource.getStatements()
				.size());
		Assert.assertEquals("ids of the statements should be equal", expectedStatement.getId(), resource.getStatements().iterator().next().getId());
		Assert.assertNotNull("the uuid shouldn't be null", resource.getStatements().iterator().next().getUUID());
		Assert.assertEquals("uuids of the statements' subjects should be equal", expectedStatement.getUUID(), resource.getStatements().iterator()
				.next().getUUID());
		Assert.assertNotNull("subject of the statement shouldn't be null", resource.getStatements().iterator().next().getSubject());
		Assert.assertEquals("ids of the statements' subjects should be equal", expectedStatement.getSubject().getId(), resource.getStatements()
				.iterator().next().getSubject().getId());
		Assert.assertEquals("types of the statements' subjects should be equal", expectedStatement.getSubject().getType(), resource.getStatements()
				.iterator().next().getSubject().getType());
		Assert.assertNotNull("predicate of the statement shouldn't be null", resource.getStatements().iterator().next().getPredicate());
		Assert.assertEquals("ids of the statements' predicates should be equal", expectedStatement.getPredicate().getUri(), resource.getStatements()
				.iterator().next().getPredicate().getUri());
		Assert.assertNotNull("object of the statement shouldn't be null", resource.getStatements().iterator().next().getObject());
		Assert.assertEquals("ids of the statements' objects should be equal", expectedStatement.getObject().getId(), resource.getStatements()
				.iterator().next().getObject().getId());
		Assert.assertEquals("types of the statements' objects should be equal", expectedStatement.getObject().getType(), resource.getStatements()
				.iterator().next().getObject().getType());
		Assert.assertEquals("uris of the statements' subjects should be equal", ((ResourceNode) expectedStatement.getSubject()).getUri(),
				((ResourceNode) resource.getStatements().iterator().next().getSubject()).getUri());
	}

	@Test
	public void testDeserializeModelWResourceWStatementWOrder() throws IOException {

		final String modelJSONString = TestUtil.getResourceAsString("modelwresourcewstatementworder.json");
		final Model model = Util.getJSONObjectMapper().readValue(modelJSONString, Model.class);

		Assert.assertNotNull("deserialized model shouldn't be null", model);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);
		final long statementId = 1;
		final long order = 1;

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(statementId);
		expectedStatement.setOrder(order);

		final Resource expectedResource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		expectedResource.addStatement(expectedStatement);
		final Model expectedModel = new Model();
		expectedModel.addResource(expectedResource);

		Assert.assertNotNull("model shouldn't be null", model);
		Assert.assertNotNull("model resources shouldn't be null", model.getResources());
		Assert.assertEquals("resource size of the model should be equal", expectedModel.getResources().size(), model.getResources().size());

		final Resource resource = model.getResources().iterator().next();

		Assert.assertEquals("uris of the resource should be equal", expectedResource.getUri(), resource.getUri());
		Assert.assertNotNull("there should be some statements for the resource", resource.getStatements());
		Assert.assertEquals("statement size of the resource should be equal", expectedResource.getStatements().size(), resource.getStatements()
				.size());
		Assert.assertEquals("ids of the statements should be equal", expectedStatement.getId(), resource.getStatements().iterator().next().getId());
		Assert.assertNotNull("subject of the statement shouldn't be null", resource.getStatements().iterator().next().getSubject());
		Assert.assertEquals("ids of the statements' subjects should be equal", expectedStatement.getSubject().getId(), resource.getStatements()
				.iterator().next().getSubject().getId());
		Assert.assertEquals("types of the statements' subjects should be equal", expectedStatement.getSubject().getType(), resource.getStatements()
				.iterator().next().getSubject().getType());
		Assert.assertNotNull("predicate of the statement shouldn't be null", resource.getStatements().iterator().next().getPredicate());
		Assert.assertEquals("ids of the statements' predicates should be equal", expectedStatement.getPredicate().getUri(), resource.getStatements()
				.iterator().next().getPredicate().getUri());
		Assert.assertNotNull("object of the statement shouldn't be null", resource.getStatements().iterator().next().getObject());
		Assert.assertEquals("ids of the statements' objects should be equal", expectedStatement.getObject().getId(), resource.getStatements()
				.iterator().next().getObject().getId());
		Assert.assertEquals("types of the statements' objects should be equal", expectedStatement.getObject().getType(), resource.getStatements()
				.iterator().next().getObject().getType());
		Assert.assertEquals("uris of the statements' subjects should be equal", ((ResourceNode) expectedStatement.getSubject()).getUri(),
				((ResourceNode) resource.getStatements().iterator().next().getSubject()).getUri());
		Assert.assertNotNull("order of the statement shouldn't be null", resource.getStatements().iterator().next().getOrder());
		Assert.assertEquals("statements' orders should be equal", expectedStatement.getOrder(), resource.getStatements().iterator().next().getOrder());
	}

	@Test
	public void testDeserializeModelWResourceWStatementWOrderAndUUID() throws IOException {

		final String modelJSONString = TestUtil.getResourceAsString("modelwresourcewstatementworderanduuid.json");
		final Model model = Util.getJSONObjectMapper().readValue(modelJSONString, Model.class);

		Assert.assertNotNull("deserialized model shouldn't be null", model);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);
		final long statementId = 1;
		final long order = 1;

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(statementId);
		expectedStatement.setUUID("18d68601-0623-42b4-ad89-f8954cc25912");
		expectedStatement.setOrder(order);

		final Resource expectedResource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		expectedResource.addStatement(expectedStatement);
		final Model expectedModel = new Model();
		expectedModel.addResource(expectedResource);

		Assert.assertNotNull("model shouldn't be null", model);
		Assert.assertNotNull("model resources shouldn't be null", model.getResources());
		Assert.assertEquals("resource size of the model should be equal", expectedModel.getResources().size(), model.getResources().size());

		final Resource resource = model.getResources().iterator().next();

		Assert.assertEquals("uris of the resource should be equal", expectedResource.getUri(), resource.getUri());
		Assert.assertNotNull("there should be some statements for the resource", resource.getStatements());
		Assert.assertEquals("statement size of the resource should be equal", expectedResource.getStatements().size(), resource.getStatements()
				.size());
		Assert.assertEquals("ids of the statements should be equal", expectedStatement.getId(), resource.getStatements().iterator().next().getId());
		Assert.assertNotNull("the uuid shouldn't be null", resource.getStatements().iterator().next().getUUID());
		Assert.assertEquals("uuids of the statements' subjects should be equal", expectedStatement.getUUID(), resource.getStatements().iterator()
				.next().getUUID());
		Assert.assertNotNull("subject of the statement shouldn't be null", resource.getStatements().iterator().next().getSubject());
		Assert.assertEquals("ids of the statements' subjects should be equal", expectedStatement.getSubject().getId(), resource.getStatements()
				.iterator().next().getSubject().getId());
		Assert.assertEquals("types of the statements' subjects should be equal", expectedStatement.getSubject().getType(), resource.getStatements()
				.iterator().next().getSubject().getType());
		Assert.assertNotNull("predicate of the statement shouldn't be null", resource.getStatements().iterator().next().getPredicate());
		Assert.assertEquals("ids of the statements' predicates should be equal", expectedStatement.getPredicate().getUri(), resource.getStatements()
				.iterator().next().getPredicate().getUri());
		Assert.assertNotNull("object of the statement shouldn't be null", resource.getStatements().iterator().next().getObject());
		Assert.assertEquals("ids of the statements' objects should be equal", expectedStatement.getObject().getId(), resource.getStatements()
				.iterator().next().getObject().getId());
		Assert.assertEquals("types of the statements' objects should be equal", expectedStatement.getObject().getType(), resource.getStatements()
				.iterator().next().getObject().getType());
		Assert.assertEquals("uris of the statements' subjects should be equal", ((ResourceNode) expectedStatement.getSubject()).getUri(),
				((ResourceNode) resource.getStatements().iterator().next().getSubject()).getUri());
		Assert.assertNotNull("order of the statement shouldn't be null", resource.getStatements().iterator().next().getOrder());
		Assert.assertEquals("statements' orders should be equal", expectedStatement.getOrder(), resource.getStatements().iterator().next().getOrder());
	}
}
