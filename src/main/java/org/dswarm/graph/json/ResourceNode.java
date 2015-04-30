/**
 * Copyright (C) 2013 – 2015 SLUB Dresden & Avantgarde Labs GmbH (<code@dswarm.org>)
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
package org.dswarm.graph.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author tgaengler
 */
public class ResourceNode extends Node {

	private String	uri;

	@JsonProperty("data_model")
	private String dataModel;

	public ResourceNode(final String uriArg) {

		super(NodeType.Resource);

		uri = uriArg;
	}

	public ResourceNode(final String uriArg, final String dataModelArg) {

		super(NodeType.Resource);

		uri = uriArg;
		dataModel = dataModelArg;
	}

	public ResourceNode(final long idArg, final String uriArg) {

		super(idArg, NodeType.Resource);

		uri = uriArg;
	}

	// id param needs to be Long; otherwise default value is 0 at deserialization
	@JsonCreator
	public ResourceNode(@JsonProperty("id") final Long idArg, @JsonProperty("uri") final String uriArg, @JsonProperty("data_model") final String dataModelArg) {

		super(idArg, NodeType.Resource);

		uri = uriArg;
		dataModel = dataModelArg;
	}

	public String getUri() {

		return uri;
	}

	public void setUri(final String uriArg) {

		uri = uriArg;
	}

	public String getDataModel() {

		return dataModel;
	}

	public void setDataModel(final String dataModelArg) {

		dataModel = dataModelArg;
	}

	@Override
	public int hashCode() {

		int result = super.hashCode();
		result = 31 * result + uri.hashCode();
		result = 31 * result + (dataModel != null ? dataModel.hashCode() : 0);

		return result;
	}

	@Override
	public boolean equals(final Object o) {

		if (this == o) {
			return true;
		}
		if (!(o instanceof ResourceNode)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		final ResourceNode that = (ResourceNode) o;

		return !(dataModel != null ? !dataModel.equals(that.dataModel) : that.dataModel != null) && uri.equals(that.uri);
	}
}
