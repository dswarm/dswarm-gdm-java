/**
 * Copyright (C) 2013, 2014 SLUB Dresden & Avantgarde Labs GmbH (<code@dswarm.org>)
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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.dswarm.graph.json.deserializer.PredicateDeserializer;
import org.dswarm.graph.json.serializer.PredicateSerializer;

/**
 *
 * @author tgaengler
 *
 */
@JsonDeserialize(using = PredicateDeserializer.class)
@JsonSerialize(using = PredicateSerializer.class)
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		Predicate other = (Predicate) obj;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}


	public String toString() {
		return getUri();
	}
}
