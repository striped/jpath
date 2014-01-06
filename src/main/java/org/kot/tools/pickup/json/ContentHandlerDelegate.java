package org.kot.tools.pickup.json;

import net.minidev.json.parser.ContentHandler;
import net.minidev.json.parser.ParseException;
import org.kot.tools.pickup.ObjectBuilder;

import java.io.IOException;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 02/12/2013 20:21
 */
class ContentHandlerDelegate implements ContentHandler {

	private ObjectBuilder mapper;

	public ContentHandlerDelegate(final ObjectBuilder mapper) {
		this.mapper = mapper;
	}

	@Override
	public void startJSON() throws ParseException, IOException {}

	@Override
	public boolean startObject() throws ParseException, IOException {
		mapper = mapper.startObject();
		return true;
	}

	@Override
	public boolean startArray() throws ParseException, IOException {
		mapper = mapper.startObject();
		return true;
	}

	@Override
	public boolean startObjectEntry(final String key) throws ParseException, IOException {
		mapper = mapper.startEntry(key);
		return true;
	}

	@Override
	public boolean primitive(final Object value) throws ParseException, IOException {
		mapper.bind((String) value);
		return true;
	}

	@Override
	public boolean endObjectEntry() throws ParseException, IOException {
		mapper.endEntry();
		return true;
	}

	@Override
	public boolean endArray() throws ParseException, IOException {
		mapper = mapper.endObject();
		return true;
	}

	@Override
	public boolean endObject() throws ParseException, IOException {
		mapper = mapper.endObject();
		return true;
	}

	@Override
	public void endJSON() throws ParseException, IOException {}
}
