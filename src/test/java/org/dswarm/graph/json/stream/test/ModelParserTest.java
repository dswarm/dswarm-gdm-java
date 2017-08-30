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
package org.dswarm.graph.json.stream.test;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.dswarm.graph.json.LiteralNode;
import org.dswarm.graph.json.Model;
import org.dswarm.graph.json.Node;
import org.dswarm.graph.json.Predicate;
import org.dswarm.graph.json.Resource;
import org.dswarm.graph.json.ResourceNode;
import org.dswarm.graph.json.Statement;
import org.dswarm.graph.json.stream.ModelParser;
import org.dswarm.graph.json.test.util.TestUtil;
import org.dswarm.graph.json.util.Util;

import org.junit.Assert;
import org.junit.Test;

import rx.Observable;

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

	@Test
	public void testParse2() throws Exception {

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(1);

		final Predicate expectedPredicate2 = new Predicate("http://www.openarchives.org/OAI/2.0/header222222");
		final Node expectedObject2 = new Node(3);
		final Statement expectedStatement2 = new Statement(expectedSubject, expectedPredicate2, expectedObject2);
		expectedStatement2.setId(2);

		final Resource expectedResource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		expectedResource.addStatement(expectedStatement);
		expectedResource.addStatement(expectedStatement2);
		final Model expectedModel = new Model();
		expectedModel.addResource(expectedResource);

		final InputStream inputStream = TestUtil.getResourceAsInputStream("model2.json");

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

			final Set<Statement> statements = actualResource.getStatements();

			Assert.assertEquals(2, statements.size());

			int j = 0;

			for (final Statement statement : statements) {

				final Statement actualStatement = statement;

				final Statement tempExpectedStatement;

				if (j == 0) {

					tempExpectedStatement = expectedStatement;
				} else {

					tempExpectedStatement = expectedStatement2;
				}

				Assert.assertEquals(tempExpectedStatement.getId(), actualStatement.getId());
				Assert.assertNull(actualStatement.getUUID());
				Assert.assertNull(actualStatement.getOrder());
				Assert.assertNull(actualStatement.getEvidence());
				Assert.assertNull(actualStatement.getConfidence());
				Assert.assertNotNull(actualStatement.getSubject());
				Assert.assertNotNull(actualStatement.getPredicate());
				Assert.assertNotNull(actualStatement.getObject());

				Assert.assertNotNull(actualStatement.getSubject().getId());
				Assert.assertEquals(tempExpectedStatement.getSubject().getId(), actualStatement.getSubject().getId());

				Assert.assertTrue(actualStatement.getSubject() instanceof ResourceNode);

				Assert.assertNotNull(((ResourceNode) actualStatement.getSubject()).getUri());
				Assert.assertEquals(((ResourceNode) tempExpectedStatement.getSubject()).getUri(),
						((ResourceNode) actualStatement.getSubject()).getUri());

				Assert.assertNotNull(actualStatement.getPredicate().getUri());
				Assert.assertEquals(tempExpectedStatement.getPredicate().getUri(), actualStatement.getPredicate().getUri());

				Assert.assertTrue(actualStatement.getObject() instanceof Node);
				Assert.assertFalse(actualStatement.getObject() instanceof ResourceNode);
				Assert.assertFalse(actualStatement.getObject() instanceof LiteralNode);

				Assert.assertNotNull(actualStatement.getObject().getId());
				Assert.assertEquals(tempExpectedStatement.getObject().getId(), actualStatement.getObject().getId());

				j++;
			}

			i++;
		}
	}

	@Test
	public void testParse3() throws Exception {

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(1);

		final Predicate expectedPredicate2 = new Predicate("http://www.openarchives.org/OAI/2.0/header222222");
		final Node expectedObject2 = new Node(3);
		final Statement expectedStatement2 = new Statement(expectedSubject, expectedPredicate2, expectedObject2);
		expectedStatement2.setId(2);

		final Predicate expectedPredicate3 = new Predicate("http://www.openarchives.org/OAI/2.0/header333333");
		final Node expectedObject3 = new Node(4);
		final Statement expectedStatement3 = new Statement(expectedObject2, expectedPredicate3, expectedObject3);
		expectedStatement3.setId(3);

		final Resource expectedResource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		expectedResource.addStatement(expectedStatement);
		expectedResource.addStatement(expectedStatement2);
		expectedResource.addStatement(expectedStatement3);
		final Model expectedModel = new Model();
		expectedModel.addResource(expectedResource);

		final InputStream inputStream = TestUtil.getResourceAsInputStream("model3.json");

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

			final Set<Statement> statements = actualResource.getStatements();

			Assert.assertEquals(3, statements.size());

			int j = 0;

			for (final Statement statement : statements) {

				final Statement actualStatement = statement;

				final Statement tempExpectedStatement;

				if (j == 0) {

					tempExpectedStatement = expectedStatement;
				} else if (j == 1) {

					tempExpectedStatement = expectedStatement2;
				} else {

					tempExpectedStatement = expectedStatement3;
				}

				Assert.assertEquals(tempExpectedStatement.getId(), actualStatement.getId());
				Assert.assertNull(actualStatement.getUUID());
				Assert.assertNull(actualStatement.getOrder());
				Assert.assertNull(actualStatement.getEvidence());
				Assert.assertNull(actualStatement.getConfidence());
				Assert.assertNotNull(actualStatement.getSubject());
				Assert.assertNotNull(actualStatement.getPredicate());
				Assert.assertNotNull(actualStatement.getObject());

				Assert.assertNotNull(actualStatement.getSubject().getId());
				Assert.assertEquals(tempExpectedStatement.getSubject().getId(), actualStatement.getSubject().getId());

				if (j == 0 || j == 1) {

					Assert.assertTrue(actualStatement.getSubject() instanceof ResourceNode);

					Assert.assertNotNull(((ResourceNode) actualStatement.getSubject()).getUri());
					Assert.assertEquals(((ResourceNode) tempExpectedStatement.getSubject()).getUri(),
							((ResourceNode) actualStatement.getSubject()).getUri());
				} else {

					Assert.assertTrue(actualStatement.getSubject() instanceof Node);
					Assert.assertFalse(actualStatement.getSubject() instanceof ResourceNode);
					Assert.assertFalse(actualStatement.getSubject() instanceof LiteralNode);

					Assert.assertNotNull(actualStatement.getSubject().getId());
					Assert.assertEquals(tempExpectedStatement.getSubject().getId(), actualStatement.getSubject().getId());
				}

				Assert.assertNotNull(actualStatement.getPredicate().getUri());
				Assert.assertEquals(tempExpectedStatement.getPredicate().getUri(), actualStatement.getPredicate().getUri());

				Assert.assertTrue(actualStatement.getObject() instanceof Node);
				Assert.assertFalse(actualStatement.getObject() instanceof ResourceNode);
				Assert.assertFalse(actualStatement.getObject() instanceof LiteralNode);

				Assert.assertNotNull(actualStatement.getObject().getId());
				Assert.assertEquals(tempExpectedStatement.getObject().getId(), actualStatement.getObject().getId());

				j++;
			}

			i++;
		}
	}

	@Test
	public void testParse4() throws Exception {

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(1);

		final Predicate expectedPredicate2 = new Predicate("http://www.openarchives.org/OAI/2.0/header222222");
		final Node expectedObject2 = new Node(3);
		final Statement expectedStatement2 = new Statement(expectedSubject, expectedPredicate2, expectedObject2);
		expectedStatement2.setId(2);

		final Predicate expectedPredicate3 = new Predicate("http://www.openarchives.org/OAI/2.0/header333333");
		final Node expectedObject3 = new Node(4);
		final Statement expectedStatement3 = new Statement(expectedObject2, expectedPredicate3, expectedObject3);
		expectedStatement3.setId(3);

		final Predicate expectedPredicate4 = new Predicate("http://www.openarchives.org/OAI/2.0/header444444");
		final LiteralNode expectedObject4 = new LiteralNode("A VALUE LITERAL");
		final Statement expectedStatement4 = new Statement(expectedObject3, expectedPredicate4, expectedObject4);
		expectedStatement4.setId(4);

		final Resource expectedResource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		expectedResource.addStatement(expectedStatement);
		expectedResource.addStatement(expectedStatement2);
		expectedResource.addStatement(expectedStatement3);
		expectedResource.addStatement(expectedStatement4);
		final Model expectedModel = new Model();
		expectedModel.addResource(expectedResource);

		final InputStream inputStream = TestUtil.getResourceAsInputStream("model4.json");

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

			final Set<Statement> statements = actualResource.getStatements();

			Assert.assertEquals(4, statements.size());

			int j = 0;

			for (final Statement statement : statements) {

				final Statement actualStatement = statement;

				final Statement tempExpectedStatement;

				if (j == 0) {

					tempExpectedStatement = expectedStatement;
				} else if (j == 1) {

					tempExpectedStatement = expectedStatement2;
				} else if (j == 2) {

					tempExpectedStatement = expectedStatement3;
				} else {

					tempExpectedStatement = expectedStatement4;
				}

				Assert.assertEquals(tempExpectedStatement.getId(), actualStatement.getId());
				Assert.assertNull(actualStatement.getUUID());
				Assert.assertNull(actualStatement.getOrder());
				Assert.assertNull(actualStatement.getEvidence());
				Assert.assertNull(actualStatement.getConfidence());
				Assert.assertNotNull(actualStatement.getSubject());
				Assert.assertNotNull(actualStatement.getPredicate());
				Assert.assertNotNull(actualStatement.getObject());

				Assert.assertNotNull(actualStatement.getSubject().getId());
				Assert.assertEquals(tempExpectedStatement.getSubject().getId(), actualStatement.getSubject().getId());

				if (j == 0 || j == 1) {

					Assert.assertTrue(actualStatement.getSubject() instanceof ResourceNode);

					Assert.assertNotNull(((ResourceNode) actualStatement.getSubject()).getUri());
					Assert.assertEquals(((ResourceNode) tempExpectedStatement.getSubject()).getUri(),
							((ResourceNode) actualStatement.getSubject()).getUri());
				} else {

					Assert.assertTrue(actualStatement.getSubject() instanceof Node);
					Assert.assertFalse(actualStatement.getSubject() instanceof ResourceNode);
					Assert.assertFalse(actualStatement.getSubject() instanceof LiteralNode);

					Assert.assertNotNull(actualStatement.getSubject().getId());
					Assert.assertEquals(tempExpectedStatement.getSubject().getId(), actualStatement.getSubject().getId());
				}

				Assert.assertNotNull(actualStatement.getPredicate().getUri());
				Assert.assertEquals(tempExpectedStatement.getPredicate().getUri(), actualStatement.getPredicate().getUri());

				if (j == 0 || j == 1 || j == 2) {

					Assert.assertTrue(actualStatement.getObject() instanceof Node);
					Assert.assertFalse(actualStatement.getObject() instanceof ResourceNode);
					Assert.assertFalse(actualStatement.getObject() instanceof LiteralNode);

					Assert.assertNotNull(actualStatement.getObject().getId());
					Assert.assertEquals(tempExpectedStatement.getObject().getId(), actualStatement.getObject().getId());
				} else {

					Assert.assertTrue(actualStatement.getObject() instanceof LiteralNode);
					Assert.assertFalse(actualStatement.getObject() instanceof ResourceNode);

					Assert.assertNull(actualStatement.getObject().getId());
					Assert.assertNotNull(((LiteralNode) actualStatement.getObject()).getValue());
					Assert.assertEquals(((LiteralNode) tempExpectedStatement.getObject()).getValue(),
							((LiteralNode) actualStatement.getObject()).getValue());
				}

				j++;
			}

			i++;
		}
	}

	@Test
	public void testParse5() throws Exception {

		final ResourceNode expectedSubject = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final Predicate expectedPredicate = new Predicate("http://www.openarchives.org/OAI/2.0/header");
		final Node expectedObject = new Node(2);

		final Statement expectedStatement = new Statement(expectedSubject, expectedPredicate, expectedObject);
		expectedStatement.setId(1);

		final Resource expectedResource = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		expectedResource.addStatement(expectedStatement);

		final ResourceNode expectedSubject2 = new ResourceNode(4,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc2591222222222222222222");
		final Predicate expectedPredicate2 = new Predicate("http://www.openarchives.org/OAI/2.0/header2222222222");
		final Node expectedObject2 = new Node(5);

		final Statement expectedStatement2 = new Statement(expectedSubject2, expectedPredicate2, expectedObject2);
		expectedStatement2.setId(3);

		final Resource expectedResource2 = new Resource("http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc2591222222222222222222");
		expectedResource2.addStatement(expectedStatement2);

		final Model expectedModel = new Model();
		expectedModel.addResource(expectedResource);
		expectedModel.addResource(expectedResource2);

		final InputStream inputStream = TestUtil.getResourceAsInputStream("model5.json");

		final ModelParser modelParser = new ModelParser(inputStream);

		final Observable<Resource> resourceObservable = modelParser.parse();

		final Iterable<Resource> iterable = resourceObservable.toBlocking().toIterable();

		int i = 0;

		for (final Resource resource : iterable) {

			final Resource tempExpectedResource;

			if(i == 0) {

				tempExpectedResource = expectedResource;
			} else {

				tempExpectedResource = expectedResource2;
			}

			final Resource actualResource = resource;

			Assert.assertNotNull(actualResource.getUri());
			Assert.assertEquals(tempExpectedResource.getUri(), actualResource.getUri());
			Assert.assertNotNull(actualResource.getStatements());
			Assert.assertEquals(1, actualResource.getStatements().size());

			final Statement actualStatement = actualResource.getStatements().iterator().next();

			final Statement tempExpectedStatement;

			if(i == 0) {

				tempExpectedStatement = expectedStatement;
			} else {

				tempExpectedStatement = expectedStatement2;
			}

			Assert.assertEquals(tempExpectedStatement.getId(), actualStatement.getId());
			Assert.assertNull(actualStatement.getUUID());
			Assert.assertNull(actualStatement.getOrder());
			Assert.assertNull(actualStatement.getEvidence());
			Assert.assertNull(actualStatement.getConfidence());
			Assert.assertNotNull(actualStatement.getSubject());
			Assert.assertNotNull(actualStatement.getPredicate());
			Assert.assertNotNull(actualStatement.getObject());

			Assert.assertNotNull(actualStatement.getSubject().getId());
			Assert.assertEquals(tempExpectedStatement.getSubject().getId(), actualStatement.getSubject().getId());

			Assert.assertTrue(actualStatement.getSubject() instanceof ResourceNode);

			Assert.assertNotNull(((ResourceNode) actualStatement.getSubject()).getUri());
			Assert.assertEquals(((ResourceNode) tempExpectedStatement.getSubject()).getUri(), ((ResourceNode) actualStatement.getSubject()).getUri());

			Assert.assertNotNull(actualStatement.getPredicate().getUri());
			Assert.assertEquals(tempExpectedStatement.getPredicate().getUri(), actualStatement.getPredicate().getUri());

			Assert.assertTrue(actualStatement.getObject() instanceof Node);
			Assert.assertFalse(actualStatement.getObject() instanceof ResourceNode);
			Assert.assertFalse(actualStatement.getObject() instanceof LiteralNode);

			Assert.assertNotNull(actualStatement.getObject().getId());
			Assert.assertEquals(tempExpectedStatement.getObject().getId(), actualStatement.getObject().getId());

			i++;
		}
	}

	@Test
	public void testParse6() throws Exception {

		final InputStream inputStream = TestUtil.getResourceAsInputStream("test-mabxml2.gson");

		final ModelParser modelParser = new ModelParser(inputStream);

		final Observable<Resource> resourceObservable = modelParser.parse();

		final Iterable<Resource> iterable = resourceObservable.toBlocking().toIterable();

		final Set<Resource> actualResources = new LinkedHashSet<>();

		for (final Resource resource : iterable) {

			actualResources.add(resource);
		}

		Assert.assertFalse(actualResources.isEmpty());

		final String expectedJSONString = TestUtil.getResourceAsString("test-mabxml2.gson");
		final Model expectedModel = Util.getJSONObjectMapper().readValue(expectedJSONString, Model.class);

		Assert.assertNotNull(expectedModel.getResources());

		Assert.assertEquals(expectedModel.getResources().size(), actualResources.size());
		Assert.assertEquals(1, actualResources.size());

		final Resource expectedResource = expectedModel.getResources().iterator().next();
		final Resource actualResource = actualResources.iterator().next();

		Assert.assertNotNull(expectedResource.getUri());
		Assert.assertNotNull(actualResource.getUri());
		Assert.assertEquals(expectedResource.getUri(), actualResource.getUri());

		Assert.assertNotNull(expectedResource.getStatements());
		Assert.assertNotNull(actualResource.getStatements());

		final Set<Statement> expectedStatements = expectedResource.getStatements();
		final Set<Statement> actualStatements = actualResource.getStatements();

		Assert.assertEquals(expectedStatements.size(), actualStatements.size());

		final Iterator<Statement> expectedIter = expectedStatements.iterator();
		final Iterator<Statement> actualIter = actualStatements.iterator();

		while(expectedIter.hasNext() && actualIter.hasNext()) {

			final Statement expectedStatement = expectedIter.next();
			final Statement actualStatement = actualIter.next();

			Assert.assertEquals(expectedStatement, actualStatement);
		}

		Assert.assertFalse(expectedIter.hasNext());
		Assert.assertFalse(actualIter.hasNext());
	}
}
