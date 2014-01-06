package org.kot.tools.pickup.json;

import net.minidev.json.parser.JSONEventReader;
import net.minidev.json.parser.ParseException;
import org.kot.tools.pickup.reflective.AnnotatedTypeBinder;
import org.kot.tools.pickup.ObjectBuilder;

import java.io.Reader;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 02/12/2013 21:07
 */
public class JSONPicker<T> {

	private final JSONEventReader parser;

	private final AnnotatedTypeBinder<T> binder;

	public JSONPicker(final Class<T> clazz) {
		parser = new JSONEventReader();
		binder = new AnnotatedTypeBinder<T>(clazz);
	}

	public T pickUp(final Reader reader) throws ParseException {
		final ObjectBuilder<T> mapper = new ObjectBuilder<T>(binder);
		parser.parse(reader, new ContentHandlerDelegate(mapper));
		return mapper.getInstance();
	}
}
