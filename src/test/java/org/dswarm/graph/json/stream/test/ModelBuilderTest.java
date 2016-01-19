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
package org.dswarm.graph.json.stream.test;

import java.io.ByteArrayOutputStream;
import java.util.Collection;

import org.dswarm.graph.json.Model;
import org.dswarm.graph.json.Resource;
import org.dswarm.graph.json.stream.ModelBuilder;
import org.dswarm.graph.json.test.util.TestUtil;
import org.dswarm.graph.json.util.Util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author tgaengler
 */
public class ModelBuilderTest {

	@Test
	public void testBuild() throws Exception {

		final String expectedJSONString = TestUtil.getResourceAsString("model.json");
		final Model expectedModel = Util.getJSONObjectMapper().readValue(expectedJSONString, Model.class);

		Assert.assertNotNull(expectedModel);

		final Collection<Resource> expectedResources = expectedModel.getResources();

		Assert.assertNotNull(expectedResources);

		final ByteArrayOutputStream modelStream = new ByteArrayOutputStream();
		final ModelBuilder modelBuilder = new ModelBuilder(modelStream);

		for(final Resource expectedResource : expectedResources) {

			modelBuilder.addResource(expectedResource);
		}

		modelBuilder.build();
		modelStream.close();

		final String actualJSONString = modelStream.toString("UTF-8");

		Assert.assertEquals(expectedJSONString, actualJSONString);

		final Model actualModel = Util.getJSONObjectMapper().readValue(actualJSONString, Model.class);

		Assert.assertNotNull(actualModel);

		Assert.assertEquals(expectedModel, actualModel);
	}

	@Test
	public void testBuild2() throws Exception {

		testBuildInternal("model2.json");
	}

	@Test
	public void testBuild3() throws Exception {

		testBuildInternal("model3.json");
	}

	@Test
	public void testBuild4() throws Exception {

		testBuildInternal("model4.json");
	}

	@Test
	public void testBuild5() throws Exception {

		testBuildInternal("model5.json");
	}

	@Test
	public void testBuild6() throws Exception {

		testBuildInternal("test-mabxml2.gson");
	}

	/**
	 * multiple CSV records to check order
	 *
	 * @throws Exception
	 */
	@Test
	public void testBuild7() throws Exception {

		testBuildInternal("test-csv.gson");
	}

	private void testBuildInternal(final String testModelFile) throws Exception {

		final String inputJSONString = TestUtil.getResourceAsString(testModelFile);
		final Model inputModel = Util.getJSONObjectMapper().readValue(inputJSONString, Model.class);
		final String expectedJSONString = Util.getJSONObjectMapper().writeValueAsString(inputModel);

		Assert.assertNotNull(inputModel);

		final Collection<Resource> inputResources = inputModel.getResources();

		Assert.assertNotNull(inputResources);

		final ByteArrayOutputStream modelStream = new ByteArrayOutputStream();
		final ModelBuilder modelBuilder = new ModelBuilder(modelStream);

		for(final Resource inputResource : inputResources) {

			modelBuilder.addResource(inputResource);
		}

		modelBuilder.build();
		modelStream.close();

		final String actualJSONString = modelStream.toString("UTF-8");

		Assert.assertEquals(expectedJSONString, actualJSONString);

		final Model actualModel = Util.getJSONObjectMapper().readValue(actualJSONString, Model.class);

		Assert.assertNotNull(actualModel);

		Assert.assertEquals(inputModel, actualModel);
	}
}
