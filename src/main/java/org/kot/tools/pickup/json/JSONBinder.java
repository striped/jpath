package org.kot.tools.pickup.json;

import net.minidev.json.parser.JSONEventReader;
import net.minidev.json.parser.ParseException;
import org.kot.tools.pickup.ObjectTypeMeta;
import org.kot.tools.pickup.ObjectBuilder;

import java.io.Reader;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 02/12/2013 21:07
 */
public class JSONBinder<T> {

	private final JSONEventReader parser;

	private ObjectBuilder<T> mapper;

	private ContentHandlerDelegate handler;

	public JSONBinder(final ObjectTypeMeta<T> binder) {
		this.parser = new JSONEventReader();
		this.mapper = new ObjectBuilder<T>(binder);
		this.handler = new ContentHandlerDelegate(mapper);
	}

	public T pickUp(final Reader reader) throws ParseException {
		parser.parse(reader, handler);
		return mapper.getInstance();
	}
}
