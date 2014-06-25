package org.dswarm.graph.json;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.dswarm.graph.json.deserializer.ModelDeserializer;
import org.dswarm.graph.json.serializer.ModelSerializer;

/**
 * @author tgaengler
 */
@JsonDeserialize(using = ModelDeserializer.class)
@JsonSerialize(using = ModelSerializer.class)
public class Model {

	/**
	 * ... linked hash set to keep the original order of the resources ...
	 */
	private Map<String, Resource>	resources	= new LinkedHashMap<String, Resource>();

	public Collection<Resource> getResources() {

		return resources.values();
	}

	public void setResources(final Set<Resource> resourcesArg) {

		resources.clear();

		if (resourcesArg != null) {

			for (final Resource resource : resourcesArg) {

				resources.put(resource.getUri(), resource);
			}
		}
	}

	public Model addResource(final Resource resource) {

		if (resource != null) {

			resources.put(resource.getUri(), resource);
		}

		return this;
	}

	public Resource getResource(final String resourceUri) {

		return resources.get(resourceUri);
	}

	public long size() {

		long size = 0;

		for (final Resource resource : resources.values()) {

			size += resource.size();
		}

		return size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resources == null) ? 0 : resources.hashCode());
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
		Model other = (Model) obj;
		if (resources == null) {
			if (other.resources != null)
				return false;
		} else if (!resources.equals(other.resources))
			return false;
		return true;
	}
}
