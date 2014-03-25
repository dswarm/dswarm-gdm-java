package de.avgl.dmp.graph.json;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.avgl.dmp.graph.json.deserializer.ModelDeserializer;
import de.avgl.dmp.graph.json.serializer.ModelSerializer;

/**
 * @author tgaengler
 */
@JsonDeserialize(using = ModelDeserializer.class)
@JsonSerialize(using = ModelSerializer.class)
public class Model {

	private Set<Resource>	resources	= new HashSet<Resource>();

	public Set<Resource> getResources() {

		return resources;
	}

	public void setResources(final Set<Resource> resourcesArg) {

		resources = resourcesArg;
	}

	public Model addResource(final Resource resource) {

		resources.add(resource);

		return this;
	}

	public long size() {
		
		long size = 0;
		
		for(final Resource resource : resources) {
			
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
