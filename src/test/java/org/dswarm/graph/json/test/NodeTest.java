/**
 * Copyright (C) 2013 – 2016 SLUB Dresden & Avantgarde Labs GmbH (<code@dswarm.org>)
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

import org.dswarm.graph.json.Node;
import org.dswarm.graph.json.test.util.TestUtil;
import org.dswarm.graph.json.util.Util;

public class NodeTest {

	@Test
	public void testSerializeBNode() throws IOException {

		final Node bnode = new Node(1);
		final String bnodeJSONString = Util.getJSONObjectMapper().writeValueAsString(bnode);

		final String expectedJSONString = TestUtil.getResourceAsString("bnode_node.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, bnodeJSONString);
	}

	@Test
	public void testDeserializeBNode() throws IOException {

		final String bnodeJSONString = TestUtil.getResourceAsString("bnode_node.json");
		final Node bnode = Util.getJSONObjectMapper().readValue(bnodeJSONString, Node.class);

		Assert.assertNotNull("deserialized bnode shouldn't be null", bnode);

		final Node expectedBNode = new Node(1);

		Assert.assertEquals("ids of the bnodes should be equal", expectedBNode.getId(), bnode.getId());
	}
}
