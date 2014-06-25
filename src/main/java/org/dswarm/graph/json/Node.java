package org.dswarm.graph.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author tgaengler
 */
public class Node {

	private Long			id	= null;

	@JsonIgnore
	private final NodeType	type;

	@JsonCreator
	public Node(@JsonProperty("id") final long idArg) {

		id = Long.valueOf(idArg);
		type = NodeType.BNode;
	}

	public Node(final long idArg, final NodeType typeArg) {

		id = Long.valueOf(idArg);
		type = typeArg;
	}

	protected Node(final NodeType typeArg) {

		type = typeArg;
	}

	public Long getId() {

		return id;
	}

	public void setId(final long idArg) {

		id = Long.valueOf(idArg);
	}

	public NodeType getType() {

		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
