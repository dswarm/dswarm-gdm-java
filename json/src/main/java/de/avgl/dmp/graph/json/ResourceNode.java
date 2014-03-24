package de.avgl.dmp.graph.json;

/**
 * @author tgaengler
 */
public class ResourceNode extends Node {

	private String	uri;

	public ResourceNode(final long idArg, final String uriArg) {

		super(idArg, NodeType.Resource);

		uri = uriArg;
	}

	public String getUri() {

		return uri;
	}

	public void setUri(final String uriArg) {

		uri = uriArg;
	}
}
