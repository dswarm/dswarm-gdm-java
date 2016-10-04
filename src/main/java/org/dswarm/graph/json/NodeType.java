/**
 * Copyright © 2013 – 2016 SLUB Dresden & Avantgarde Labs GmbH (<code@dswarm.org>)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dswarm.graph.json;

/**
 * @author tgaengler
 */
public enum NodeType {

	/**
	 * Type for resources.
	 */
	Resource("RESOURCE"),

	/**
	 * Type for bnodes.
	 */
	BNode("BNODE"),

	/**
	 * Type for literals.
	 */
	Literal("LITERAL");

	/**
	 * The name of the node type.
	 */
	private final String	name;

	/**
	 * Gets the name of the node type.
	 *
	 * @return the name of the node type
	 */
	public String getName() {

		return name;
	}

	/**
	 * Creates a new node type with the given name.
	 *
	 * @param nameArg the name of the node type.
	 */
	private NodeType(final String nameArg) {

		this.name = nameArg;
	}

	/**
	 * Gets the node type by the given name.<br>
	 * Created by: ydeng
	 *
	 * @param name the name of the node type
	 * @return the appropriated node type
	 */
	public static NodeType getByName(final String name) {

		for (final NodeType functionType : NodeType.values()) {

			if (functionType.name.equals(name)) {

				return functionType;
			}
		}

		throw new IllegalArgumentException(name);
	}

	/**
	 * {@inheritDoc}<br>
	 * Returns the name of the node type.
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {

		return name;
	}
}
