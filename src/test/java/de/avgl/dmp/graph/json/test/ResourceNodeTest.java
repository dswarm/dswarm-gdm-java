package de.avgl.dmp.graph.json.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import de.avgl.dmp.graph.json.ResourceNode;
import de.avgl.dmp.graph.json.test.util.TestUtil;
import de.avgl.dmp.graph.json.util.Util;

public class ResourceNodeTest {

	@Test
	public void testSerializeResourceNode() throws IOException {

		final ResourceNode resourceNode = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");
		final String resourceNodeJSONString = Util.getJSONObjectMapper().writeValueAsString(resourceNode);

		final String expectedJSONString = TestUtil.getResourceAsString("resource_node.json");

		Assert.assertEquals("wrong serialisation", expectedJSONString, resourceNodeJSONString);
	}

	@Test
	public void testDeserializeResourceNode() throws IOException {

		final String resourceNodeJSONString = TestUtil.getResourceAsString("resource_node.json");
		final ResourceNode resourceNode = Util.getJSONObjectMapper().readValue(resourceNodeJSONString, ResourceNode.class);

		Assert.assertNotNull("deserialized resource node shouldn't be null", resourceNode);

		final ResourceNode expectedResourceNode = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912");

		Assert.assertEquals("ids of the resource nodes should be equal", expectedResourceNode.getId(), resourceNode.getId());
		Assert.assertEquals("uris of the resource nodes should be equal", expectedResourceNode.getUri(), resourceNode.getUri());
	}
}
