package de.avgl.dmp.graph.json;

/**
 * @author tgaengler
 */
public class Node {

	private Long			id;

	private final NodeType	type;

	public Node(final long idArg) {

		id = Long.valueOf(idArg);
		type = NodeType.BNode;
	}

	public Node(final long idArg, final NodeType typeArg) {

		id = Long.valueOf(idArg);
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
}
