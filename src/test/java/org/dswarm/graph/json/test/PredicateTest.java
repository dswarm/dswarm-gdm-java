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
package org.dswarm.graph.json.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import org.dswarm.graph.json.Predicate;
import org.dswarm.graph.json.test.util.TestUtil;
import org.dswarm.graph.json.util.Util;

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
