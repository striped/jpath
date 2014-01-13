package org.kot.tools.pickup.json;

import net.minidev.json.parser.JSONEventProducer;
import net.minidev.json.parser.ParseException;
import org.kot.tools.pickup.ObjectTypeMeta;
import org.kot.tools.pickup.ObjectBuilder;

import java.io.Reader;

/**
 * Default JSON binder implementation (awhile based on "net.minidev:json-smart", will be amended in future).
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @created 02/12/2013 21:07
 */
public class JSONBinder<T> {

	private final JSONEventProducer parser;

	private ObjectBuilder<T> mapper;

	private ContentHandlerDelegate handler;

	/**
	 * Constructor with expected type metadata.
	 * @param meta The metadata of expected JSON object
	 */
	public JSONBinder(final ObjectTypeMeta<T> meta) {
		this.parser = new JSONEventProducer();
		this.mapper = new ObjectBuilder<T>(meta);
		this.handler = new ContentHandlerDelegate(mapper);
	}

	/**
	 * Bind provided data reader to Java object (as expected by specified {@link #JSONBinder(org.kot.tools.pickup.ObjectTypeMeta) on construction metadata}).
	 * @param reader The data reader.
	 * @return The expected object instance
	 * @throws ParseException On unexpected parser failure (data stream are corrupted and / or not JSON).
	 */
	public T pickUp(final Reader reader) throws ParseException {
		parser.parse(reader, handler);
		return mapper.getInstance();
	}
}
