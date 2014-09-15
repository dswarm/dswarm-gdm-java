package org.dswarm.graph.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author tgaengler
 * @author polowins
 */
public class ResourceNode extends Node {

	private String	uri;
	
	/**
	 * A resource is only set if this resourceNode is used as a subject in a CBD.
	 */
	private Resource resource;

	public ResourceNode(final String uriArg) {

		super(NodeType.Resource);

		uri = uriArg;
	}

	@JsonCreator
	public ResourceNode(@JsonProperty("id") final long idArg, @JsonProperty("uri") final String uriArg) {

		super(idArg, NodeType.Resource);

		uri = uriArg;
	}

	public ResourceNode(Resource resource) {
		
		super(NodeType.Resource);
		
		this.resource = resource;
		
		this.uri = resource.getUri();
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
	
	public String toString(){
		return getUri() + ":ResourceNode"; 
	}
	
	/**
	 * Get the Resource if this resource node is used in statements of a resource (CBD). 
	 * While (in RDF) every statement has a (rdfs:)Resource as it's subject, we only provide a Resource, if
	 * the subject is an entity corresponding to an (rdfs:)Resource with a URI, i.e. blank nodes used as 
	 * a subject do not start a CBD.
	 * 
	 * @return the resource or null if this node is not subject in a resource statement
	 */
	public Resource getResource(){
		return this.resource;
	}

	
//	/**
//	 * @param resource - the resource in which this node is used as a subject
//	 */
//	public void setResource(Resource resource) {
//		this.resource = resource;
//	}
}
