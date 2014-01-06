package net.minidev.json.parser;

import java.io.IOException;
import java.io.Reader;

import static net.minidev.json.parser.ParseException.ERROR_UNEXPECTED_CHAR;
import static net.minidev.json.parser.ParseException.ERROR_UNEXPECTED_TOKEN;

public class JSONEventReader extends JSONParserReader {

	private static final FakeContainerFactory CONTAINER_FACTORY = new FakeContainerFactory();

	public JSONEventReader() {
		super(JSONParser.MODE_PERMISSIVE);
	}

	public Object parse(Reader in, ContentHandler handler) throws ParseException {
		return super.parse(in, CONTAINER_FACTORY, handler);
	}

	protected Object readMain(boolean stop[]) throws ParseException, IOException {
		for (;;) {
			switch (c) {
				// skip spaces
				case ' ':
				case '\r':
				case '\n':
				case '\t':
					read();
					continue;
					// invalid stats
				case ':':
				case '}':
				case ']':
					throw new ParseException(pos, ERROR_UNEXPECTED_CHAR, c);
					// start object
				case '{':
					return readObject();
				// start Array
				case '[':
					return readArray();
				// start string
				case '"':
				case '\'':
					readString();
					handler.primitive(xs);
					return xs;
				// string or null
				case 'n':
					readNQString(stop);
					if ("null".equals(xs)) {
						handler.primitive(null);
						return null;
					}
					if (!acceptNonQuote)
						throw new ParseException(pos, ERROR_UNEXPECTED_TOKEN, xs);
					handler.primitive(xs);
					return xs;
				default:
					readNQString(stop);
					if (!acceptNonQuote)
						throw new ParseException(pos, ERROR_UNEXPECTED_TOKEN, xs);
					handler.primitive(xs);
					return xs;
			}
		}
	}
}
