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

		final Statement statement = new Statement(1, subject, predicate, object);
		final String statementJSONString = Util.getJSONObjectMapper().writeValueAsString(statement);

		final String expectedJSONString = TestUtil.getResourceAsString("statement.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, statementJSONString);
	}

	@Test
	public void testSerializeStatementWProvenanceResource() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new ResourceNode(2, "http://example.com/dataresource", "http://example.com");

		final Statement statement = new Statement(1, subject, predicate, object);
		final String statementJSONString = Util.getJSONObjectMapper().writeValueAsString(statement);

		final String expectedJSONString = TestUtil.getResourceAsString("statement_w_provenance_resource.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, statementJSONString);
	}

	@Test
	public void testSerializeStatementWProvenanceResourceAndEvidence() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new ResourceNode(2, "http://example.com/dataresource", "http://example.com");

		final Statement statement = new Statement(1, subject, predicate, object, "0815");
		final String statementJSONString = Util.getJSONObjectMapper().writeValueAsString(statement);

		final String expectedJSONString = TestUtil.getResourceAsString("statement_w_provenance_resource_and_evidence.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, statementJSONString);
	}

	@Test
	public void testSerializeStatementWUUID() throws IOException {

		final ResourceNode subject = new ResourceNode(1, "http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate predicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node object = new Node(2);

		final Statement statement = new Statement(1, "18d68601-0623-42b4-ad89-f8954cc25912", subject, predicate, object);
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

		final Statement statement = new Statement("18d68601-0623-42b4-ad89-f8954cc25912", subject, predicate, object);
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

		final Statement statement = new Statement(statementId, subject, predicate, object, order);
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

		final Statement statement = new Statement(statementId, "18d68601-0623-42b4-ad89-f8954cc25912", subject, predicate, object, order);
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

		final Statement statement = new Statement("18d68601-0623-42b4-ad89-f8954cc25912", subject, predicate, object, order);
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

	@Test
	public void testDeserializeStatementWProvenanceResource() throws IOException {

		final String statementJSONString = TestUtil.getResourceAsString("statement_w_provenance_resource.json");
		final Statement statement = Util.getJSONObjectMapper().readValue(statementJSONString, Statement.class);

		Assert.assertNotNull("deserialized statement shouldn't be null", statement);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new ResourceNode(2, "http://example.com/dataresource", "http://example.com");

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
		Assert.assertNotNull(((ResourceNode) expectedStatement.getObject()).getProvenance());
		Assert.assertEquals("provenances of the statements' objects should be equal", ((ResourceNode) expectedStatement.getObject()).getProvenance(), ((ResourceNode) statement.getObject()).getProvenance());
		Assert.assertEquals("uris of the statements' subjects should be equal", ((ResourceNode) expectedStatement.getSubject()).getUri(),
				((ResourceNode) statement.getSubject()).getUri());
	}

	@Test
	public void testDeserializeStatementWProvenanceResourceAndEvidence() throws IOException {

		final String statementJSONString = TestUtil.getResourceAsString("statement_w_provenance_resource_and_evidence.json");
		final Statement statement = Util.getJSONObjectMapper().readValue(statementJSONString, Statement.class);

		Assert.assertNotNull("deserialized statement shouldn't be null", statement);

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new ResourceNode(2, "http://example.com/dataresource", "http://example.com");

		final Statement expectedStatement = new Statement(1, expectedSubject, expectedPredicate, expectedObject, "0815");

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
		Assert.assertNotNull(((ResourceNode) expectedStatement.getObject()).getProvenance());
		Assert.assertEquals("provenances of the statements' objects should be equal", ((ResourceNode) expectedStatement.getObject()).getProvenance(), ((ResourceNode) statement.getObject()).getProvenance());
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

		final Statement expectedStatement = new Statement(1, "18d68601-0623-42b4-ad89-f8954cc25912", expectedSubject, expectedPredicate,
				expectedObject);

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

		final Statement expectedStatement = new Statement("18d68601-0623-42b4-ad89-f8954cc25912", expectedSubject, expectedPredicate, expectedObject);

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

		final Statement expectedStatement = new Statement(statementId, expectedSubject, expectedPredicate, expectedObject, order);

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

		final Statement expectedStatement = new Statement(statementId, uuid, expectedSubject, expectedPredicate, expectedObject, order);

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

		final Statement expectedStatement = new Statement(uuid, expectedSubject, expectedPredicate, expectedObject, order);

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
