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
}
