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
