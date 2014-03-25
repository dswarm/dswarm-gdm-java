package de.avgl.dmp.graph.json.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import de.avgl.dmp.graph.json.LiteralNode;
import de.avgl.dmp.graph.json.test.util.TestUtil;
import de.avgl.dmp.graph.json.util.Util;

public class LiteralNodeTest {

	@Test
	public void testSerializeLiteralNode() throws IOException {

		final LiteralNode literalNode = new LiteralNode(267, "907");
		final String literalNodeJSONString = Util.getJSONObjectMapper().writeValueAsString(literalNode);

		final String expectedJSONString = TestUtil.getResourceAsString("literal_node.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, literalNodeJSONString);
	}

	@Test
	public void testDeserializeLiteralNode() throws IOException {

		final String literalNodeJSONString = TestUtil.getResourceAsString("literal_node.json");
		final LiteralNode literalNode = Util.getJSONObjectMapper().readValue(literalNodeJSONString, LiteralNode.class);

		Assert.assertNotNull("deserialized literal node shouldn't be null", literalNode);

		final LiteralNode expectedLiteralNode = new LiteralNode(267, "907");

		Assert.assertEquals("ids of the literal nodes should be equal", expectedLiteralNode.getId(), literalNode.getId());
		Assert.assertEquals("values of the literal nodes should be equal", expectedLiteralNode.getValue(), literalNode.getValue());
	}
}
