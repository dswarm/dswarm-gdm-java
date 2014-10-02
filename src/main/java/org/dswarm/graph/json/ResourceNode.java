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
package org.dswarm.graph.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author tgaengler
 */
public class ResourceNode extends Node {

	private String	uri;

	private String provenance;

	public ResourceNode(final String uriArg) {

		super(NodeType.Resource);

		uri = uriArg;
	}

	public ResourceNode(final String uriArg, final String provenanceArg) {

		super(NodeType.Resource);

		uri = uriArg;
		provenance = provenanceArg;
	}

	public ResourceNode(final long idArg, final String uriArg) {

		super(idArg, NodeType.Resource);

		uri = uriArg;
	}

	@JsonCreator
	public ResourceNode(@JsonProperty("id") final long idArg, @JsonProperty("uri") final String uriArg, @JsonProperty("provenance") final String provenanceArg) {

		super(idArg, NodeType.Resource);

		uri = uriArg;
		provenance = provenanceArg;
	}

	public String getUri() {

		return uri;
	}

	public void setUri(final String uriArg) {

		uri = uriArg;
	}

	public String getProvenance() {

		return provenance;
	}

	public void setProvenance(final String provenanceArg) {

		provenance = provenanceArg;
	}

	@Override
	public int hashCode() {

		int result = super.hashCode();
		result = 31 * result + uri.hashCode();
		result = 31 * result + (provenance != null ? provenance.hashCode() : 0);

		return result;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (!(o instanceof ResourceNode)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		ResourceNode that = (ResourceNode) o;

		return !(provenance != null ? !provenance.equals(that.provenance) : that.provenance != null) && uri.equals(that.uri);
	}
}
