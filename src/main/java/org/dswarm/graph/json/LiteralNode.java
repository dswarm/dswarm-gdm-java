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
public class LiteralNode extends Node {

	@JsonProperty("v")
	private String	value	= null;

	public LiteralNode(final String valueArg) {

		super(NodeType.Literal);

		value = valueArg;
	}

	@JsonCreator
	public LiteralNode(@JsonProperty("id") final long idArg, @JsonProperty("v") final String valueArg) {

		super(idArg, NodeType.Literal);

		value = valueArg;
	}

	public String getValue() {

		return value;
	}

	public void setValue(final String valueArg) {

		value = valueArg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LiteralNode other = (LiteralNode) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	public String toString(){
		return getValue() + ":LiteralNode"; 
	}
}
