/**
 * Copyright © 2013 – 2016 SLUB Dresden & Avantgarde Labs GmbH (<code@dswarm.org>)
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
package org.dswarm.graph.json.test.util;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

import org.dswarm.graph.json.Model;
import org.dswarm.graph.json.Resource;
import org.dswarm.graph.json.util.Util;

/**
 * Created by tgaengler on 04.10.16.
 */
public class UtilTest {

	final ObjectMapper objectMapper = new ObjectMapper()
			.setSerializationInclusion(JsonInclude.Include.NON_NULL)
			.configure(SerializationFeature.INDENT_OUTPUT, true);

	@Test
	public void gdmCompactJSONTest() throws IOException {

		final String modelJSONString = TestUtil.getResourceAsString("test-mabxml.gson");
		final Model model = Util.getJSONObjectMapper().readValue(modelJSONString, Model.class);

		final JsonNode gdmCompactJSON = Util.toGDMCompactJSON(model, getRecordURIs(model));

		final String expectedResult = TestUtil.getResourceAsString("test-mabxml.gdm.compact.json");
		final String actualResult = objectMapper.writeValueAsString(gdmCompactJSON);

		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void gdmSimpleJSONTest() throws IOException {

		final String modelJSONString = TestUtil.getResourceAsString("test-mabxml.gson");
		final Model model = Util.getJSONObjectMapper().readValue(modelJSONString, Model.class);

		final JsonNode gdmSimpleJSON = Util.toGDMSimpleJSON(model, getRecordURIs(model));

		final String expectedResult = TestUtil.getResourceAsString("test-mabxml.gdm.simple.json");
		final String actualResult = objectMapper.writeValueAsString(gdmSimpleJSON);

		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void toJSONTest() throws IOException {

		final String modelJSONString = TestUtil.getResourceAsString("test-mabxml.gson");
		final Model model = Util.getJSONObjectMapper().readValue(modelJSONString, Model.class);

		final JsonNode json = Util.toJSON(model, getRecordURIs(model));

		final String expectedResult = TestUtil.getResourceAsString("test-mabxml.json");
		final String actualResult = objectMapper.writeValueAsString(json);

		Assert.assertEquals(expectedResult, actualResult);
	}

	private static final Set<String> getRecordURIs(final Model model) {

		// TODO: do not iterate over all resources of the model - only _record_ resources are needed here (_record_ resources should be contain a statement with the record class as type at least)
		final Set<Resource> recordResources = Sets.newLinkedHashSet(model.getResources());

		final Set<String> recordURIs = new LinkedHashSet<>();

		recordURIs.addAll(recordResources.stream().map(Resource::getUri).collect(Collectors.toList()));

		return recordURIs;
	}
}
