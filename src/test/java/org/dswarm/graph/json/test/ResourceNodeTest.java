package org.dswarm.graph.json.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import org.dswarm.graph.json.ResourceNode;
import org.dswarm.graph.json.test.util.TestUtil;
import org.dswarm.graph.json.util.Util;

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
	public void testSerializeDataModelResourceNode() throws IOException {

		final ResourceNode resourceNode = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912", "http://data.slub-dresden.de/datamodels/22");
		final String resourceNodeJSONString = Util.getJSONObjectMapper().writeValueAsString(resourceNode);

		final String expectedJSONString = TestUtil.getResourceAsString("data_model_resource_node.json");

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

	@Test
	public void testDeserializeDataModelResourceNode() throws IOException {

		final String resourceNodeJSONString = TestUtil.getResourceAsString("data_model_resource_node.json");
		final ResourceNode resourceNode = Util.getJSONObjectMapper().readValue(resourceNodeJSONString, ResourceNode.class);

		Assert.assertNotNull("deserialized data model resource node shouldn't be null", resourceNode);

		final ResourceNode expectedResourceNode = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912", "http://data.slub-dresden.de/datamodels/22");

		Assert.assertEquals("ids of the data model resource nodes should be equal", expectedResourceNode.getId(), resourceNode.getId());
		Assert.assertEquals("uris of the data model resource nodes should be equal", expectedResourceNode.getUri(), resourceNode.getUri());
		Assert.assertNotNull(expectedResourceNode.getUri());
		Assert.assertEquals("data models of the data model resource nodes should be equal", expectedResourceNode.getDataModel(), resourceNode.getDataModel());
	}
}
