package de.avgl.dmp.graph.json.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import de.avgl.dmp.graph.json.Model;
import de.avgl.dmp.graph.json.Node;
import de.avgl.dmp.graph.json.Predicate;
import de.avgl.dmp.graph.json.Resource;
import de.avgl.dmp.graph.json.ResourceNode;
import de.avgl.dmp.graph.json.Statement;
import de.avgl.dmp.graph.json.test.util.TestUtil;
import de.avgl.dmp.graph.json.util.Util;

public class ModelTest {

	@Test
	public void testSerializeModel() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);

		final Statement statement = new Statement(1, subject, predicate, object);
		final Resource resource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		resource.addStatement(statement);
		final Model model = new Model();
		model.addResource(resource);
		final String modelJSONString = Util.getJSONObjectMapper().writeValueAsString(model);

		final String expectedJSONString = TestUtil.getResourceAsString("model.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, modelJSONString);
	}

	@Test
	public void testSerializeModelWResourceWStatementWOrder() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);
		final long statementId = 1;
		final long order = 1;

		final Statement statement = new Statement(statementId, subject, predicate, object, order);
		final Resource resource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		resource.addStatement(statement);
		final Model model = new Model();
		model.addResource(resource);
		final String modelJSONString = Util.getJSONObjectMapper().writeValueAsString(model);

		final String expectedJSONString = TestUtil.getResourceAsString("modelwresourcewstatementworder.json");

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

		final Statement expectedStatement = new Statement(1, expectedSubject, expectedPredicate, expectedObject);

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

		final Statement expectedStatement = new Statement(statementId, expectedSubject, expectedPredicate, expectedObject, order);

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
		Assert.assertEquals("statements' orders should be equal", expectedStatement.getOrder(), resource.getStatements()
				.iterator().next().getOrder());
	}
}
