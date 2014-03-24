package de.avgl.dmp.graph.json;

/**
 * 
 * @author tgaengler
 *
 */
public class Predicate {

	private String uri = null;
	
	public Predicate(final String uriArg) {
		
		uri = uriArg;
	}

	
	public String getUri() {
		
		return uri;
	}

	
	public void setUri(final String uriArg) {
		
		uri = uriArg;
	}
}
