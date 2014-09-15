package org.dswarm.graph.json.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import org.dswarm.graph.json.Node;
import org.dswarm.graph.json.Predicate;
import org.dswarm.graph.json.Resource;
import org.dswarm.graph.json.ResourceNode;
import org.dswarm.graph.json.Statement;
import org.dswarm.graph.json.test.util.TestUtil;
import org.dswarm.graph.json.util.Util;

public class ResourceTest {

	@Test
	public void testSerializeResource() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(1);
		final Resource resource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		resource.addStatement(statement);
		final String resourceJSONString = Util.getJSONObjectMapper().writeValueAsString(resource);

		final String expectedJSONString = TestUtil.getResourceAsString("resource.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, resourceJSONString);
	}

	@Test
	public void testSerializeResourceWStatementWUUID() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);

		final Statement statement = new Statement(subject, predicate, object);
		statement.setId(1);
		statement.setUUID("18d68601-0623-42b4-ad89-f8954cc25912");
		final Resource resource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		resource.addStatement(statement);
		final String resourceJSONString = Util.getJSONObjectMapper().writeValueAsString(resource);

		final String expectedJSONString = TestUtil.getResourceAsString("resourcewstatementwuuid.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, resourceJSONString);
	}

	@Test
	public void testSerializeResourceWStatementWOrder() throws IOException {

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
		final String resourceJSONString = Util.getJSONObjectMapper().writeValueAsString(resource);

		final String expectedJSONString = TestUtil.getResourceAsString("resourcewstatementworder.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, resourceJSONString);
	}

	@Test
	public void testSerializeResourceWStatementWOrderAndUUID() throws IOException {

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
		final String resourceJSONString = Util.getJSONObjectMapper().writeValueAsString(resource);

		final String expectedJSONString = TestUtil.getResourceAsString("resourcewstatementworderanduuid.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, resourceJSONString);
	}

	@Test
	public void testDeserializeResource() throws IOException {

		final String resourceJSONString = TestUtil.getResourceAsString("resource.json");
		final Resource resource = Util.getJSONObjectMapper().readValue(resourceJSONString, Resource.class);

		Assert.assertNotNull("deserialized resource shouldn't be null", resource);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(1);

		final Resource expectedResource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		expectedResource.addStatement(expectedStatement);

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
	public void testDeserializeResourceWStatementWUUID() throws IOException {

		final String resourceJSONString = TestUtil.getResourceAsString("resourcewstatementwuuid.json");
		final Resource resource = Util.getJSONObjectMapper().readValue(resourceJSONString, Resource.class);

		Assert.assertNotNull("deserialized resource shouldn't be null", resource);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(1);
		expectedStatement.setUUID("18d68601-0623-42b4-ad89-f8954cc25912");

		final Resource expectedResource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		expectedResource.addStatement(expectedStatement);

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
	public void testDeserializeResourceWStatementWOrder() throws IOException {

		final String resourceJSONString = TestUtil.getResourceAsString("resourcewstatementworder.json");
		final Resource resource = Util.getJSONObjectMapper().readValue(resourceJSONString, Resource.class);

		Assert.assertNotNull("deserialized resource shouldn't be null", resource);

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
	public void testDeserializeResourceWStatementWOrderAndUUID() throws IOException {

		final String resourceJSONString = TestUtil.getResourceAsString("resourcewstatementworderanduuid.json");
		final Resource resource = Util.getJSONObjectMapper().readValue(resourceJSONString, Resource.class);

		Assert.assertNotNull("deserialized resource shouldn't be null", resource);

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
