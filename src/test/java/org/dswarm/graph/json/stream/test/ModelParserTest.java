package org.dswarm.graph.json.stream.test;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import rx.Observable;

import org.dswarm.graph.json.Resource;
import org.dswarm.graph.json.stream.ModelParser;
import org.dswarm.graph.json.test.util.TestUtil;

/**
 * @author tgaengler
 */
public class ModelParserTest {

	@Test
	public void testParse() throws Exception {

		final InputStream inputStream = TestUtil.getResourceAsInputStream("model.json");

		final ModelParser modelParser = new ModelParser(inputStream);

		final Observable<Resource> resourceObservable = modelParser.parse();

		final Iterable<Resource> iterable = resourceObservable.toBlocking().toIterable();

		for (final Resource resource : iterable) {

			// TODO: do comparison
		}
	}
}