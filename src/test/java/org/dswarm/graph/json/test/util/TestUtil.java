package org.dswarm.graph.json.test.util;

import java.io.IOException;
import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;


public class TestUtil {

	/**
	 * Retrieves a resource by the give path and converts its content to a string.
	 *
	 * @param resource a resource path
	 * @return a string representation fo the content of the resource
	 * @throws IOException
	 */
	public static String getResourceAsString(final String resource) throws IOException {

		final URL url = Resources.getResource(resource);
		return Resources.toString(url, Charsets.UTF_8);
	}
}
