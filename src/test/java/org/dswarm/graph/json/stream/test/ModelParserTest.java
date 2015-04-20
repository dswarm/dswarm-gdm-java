package org.dswarm.graph.json.stream.test;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import rx.Observable;

import org.dswarm.graph.json.LiteralNode;
import org.dswarm.graph.json.Model;
import org.dswarm.graph.json.Node;
import org.dswarm.graph.json.Predicate;
import org.dswarm.graph.json.Resource;
import org.dswarm.graph.json.ResourceNode;
import org.dswarm.graph.json.Statement;
import org.dswarm.graph.json.stream.ModelParser;
import org.dswarm.graph.json.test.util.TestUtil;

/**
 * @author tgaengler
 */
public class ModelParserTest {

	@Test
	public void testParse() throws Exception {

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

		final InputStream inputStream = TestUtil.getResourceAsInputStream("model.json");

		final ModelParser modelParser = new ModelParser(inputStream);

		final Observable<Resource> resourceObservable = modelParser.parse();

		final Iterable<Resource> iterable = resourceObservable.toBlocking().toIterable();

		int i = 0;

		for (final Resource resource : iterable) {

			Assert.assertTrue(i == 0);

			final Resource actualResource = resource;

			Assert.assertNotNull(actualResource.getUri());
			Assert.assertEquals(expectedResource.getUri(), actualResource.getUri());
			Assert.assertNotNull(actualResource.getStatements());
			Assert.assertEquals(1, actualResource.getStatements().size());

			final Statement actualStatement = actualResource.getStatements().iterator().next();

			Assert.assertEquals(expectedStatement.getId(), actualStatement.getId());
			Assert.assertNull(actualStatement.getUUID());
			Assert.assertNull(actualStatement.getOrder());
			Assert.assertNull(actualStatement.getEvidence());
			Assert.assertNull(actualStatement.getConfidence());
			Assert.assertNotNull(actualStatement.getSubject());
			Assert.assertNotNull(actualStatement.getPredicate());
			Assert.assertNotNull(actualStatement.getObject());

			Assert.assertNotNull(actualStatement.getSubject().getId());
			Assert.assertEquals(expectedStatement.getSubject().getId(), actualStatement.getSubject().getId());

			Assert.assertTrue(actualStatement.getSubject() instanceof ResourceNode);

			Assert.assertNotNull(((ResourceNode) actualStatement.getSubject()).getUri());
			Assert.assertEquals(((ResourceNode) expectedStatement.getSubject()).getUri(), ((ResourceNode) actualStatement.getSubject()).getUri());

			Assert.assertNotNull(actualStatement.getPredicate().getUri());
			Assert.assertEquals(expectedStatement.getPredicate().getUri(), actualStatement.getPredicate().getUri());

			Assert.assertTrue(actualStatement.getObject() instanceof Node);
			Assert.assertFalse(actualStatement.getObject() instanceof ResourceNode);
			Assert.assertFalse(actualStatement.getObject() instanceof LiteralNode);

			Assert.assertNotNull(actualStatement.getObject().getId());
			Assert.assertEquals(expectedStatement.getObject().getId(), actualStatement.getObject().getId());

			i++;
		}
	}
}