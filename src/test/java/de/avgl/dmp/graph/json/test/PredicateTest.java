package de.avgl.dmp.graph.json.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import de.avgl.dmp.graph.json.Predicate;
import de.avgl.dmp.graph.json.test.util.TestUtil;
import de.avgl.dmp.graph.json.util.Util;

public class PredicateTest {

	@Test
	public void testSerializePredicate() throws IOException {

		final Predicate predicate = new Predicate("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		final String predicateJSONString = Util.getJSONObjectMapper().writeValueAsString(predicate);

		final String expectedJSONString = TestUtil.getResourceAsString("rdf_type_predicate.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, predicateJSONString);
	}

	@Test
	public void testDeserializePredicate() throws IOException {

		final String predicateJSONString = TestUtil.getResourceAsString("rdf_type_predicate.json");
		final Predicate predicate = Util.getJSONObjectMapper().readValue(predicateJSONString, Predicate.class);
		
		Assert.assertNotNull("deserialized predicate shouldn't be null", predicate);
		
		final Predicate expectedPredicate = new Predicate("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		
		Assert.assertEquals("uris of the predicates should be equal", expectedPredicate.getUri(), predicate.getUri());
	}
}
