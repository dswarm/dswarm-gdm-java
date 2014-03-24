package de.avgl.dmp.graph.json;

/**
 * 
 * @author tgaengler
 *
 */
public class LiteralNode extends Node {

	private String	value;

	public LiteralNode(final long idArg, final String valueArg) {

		super(idArg, NodeType.Literal);

		value = valueArg;
	}

	
	public String getValue() {
		
		return value;
	}
	
	public void setValue(final String valueArg) {
		
		value = valueArg;
	}
}
