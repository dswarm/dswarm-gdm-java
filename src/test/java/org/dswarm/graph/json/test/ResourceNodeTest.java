/**
 * Copyright (C) 2013, 2014 SLUB Dresden & Avantgarde Labs GmbH (<code@dswarm.org>)
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
	public void testSerializeProvenanceResourceNode() throws IOException {

		final ResourceNode resourceNode = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912", "http://data.slub-dresden.de/datamodels/22");
		final String resourceNodeJSONString = Util.getJSONObjectMapper().writeValueAsString(resourceNode);

		final String expectedJSONString = TestUtil.getResourceAsString("provenance_resource_node.json");

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
	public void testDeserializeProvenanceResourceNode() throws IOException {

		final String resourceNodeJSONString = TestUtil.getResourceAsString("provenance_resource_node.json");
		final ResourceNode resourceNode = Util.getJSONObjectMapper().readValue(resourceNodeJSONString, ResourceNode.class);

		Assert.assertNotNull("deserialized provenance resource node shouldn't be null", resourceNode);

		final ResourceNode expectedResourceNode = new ResourceNode(1,
				"http://data.slub-dresden.de/datamodels/22/records/18d68601-0623-42b4-ad89-f8954cc25912", "http://data.slub-dresden.de/datamodels/22");

		Assert.assertEquals("ids of the provenance resource nodes should be equal", expectedResourceNode.getId(), resourceNode.getId());
		Assert.assertEquals("uris of the provenance resource nodes should be equal", expectedResourceNode.getUri(), resourceNode.getUri());
		Assert.assertNotNull(expectedResourceNode.getUri());
		Assert.assertEquals("provenances of the provenance resource nodes should be equal", expectedResourceNode.getProvenance(), resourceNode.getProvenance());
	}
}
