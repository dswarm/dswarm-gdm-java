package de.avgl.dmp.graph.json;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author tgaengler
 */
@XmlRootElement
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		ResourceNode other = (ResourceNode) obj;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
}
