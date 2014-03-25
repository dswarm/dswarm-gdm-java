package de.avgl.dmp.graph.json.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import de.avgl.dmp.graph.json.Node;
import de.avgl.dmp.graph.json.Predicate;
import de.avgl.dmp.graph.json.ResourceNode;
import de.avgl.dmp.graph.json.Statement;
import de.avgl.dmp.graph.json.test.util.TestUtil;
import de.avgl.dmp.graph.json.util.Util;

public class StatementTest {

	@Test
	public void testSerializeStatement() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);

		final Statement statement = new Statement(1, subject, predicate, object);
		final String statementJSONString = Util.getJSONObjectMapper().writeValueAsString(statement);

		final String expectedJSONString = TestUtil.getResourceAsString("statement.json");

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

		final Statement expectedStatement = new Statement(1, expectedSubject, expectedPredicate, expectedObject);

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
}
