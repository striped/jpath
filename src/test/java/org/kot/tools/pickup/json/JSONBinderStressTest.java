package org.kot.tools.pickup.json;

import net.minidev.json.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kot.tools.ConcurrentRunner;
import org.kot.tools.pickup.reflective.AnnotatedTypeBinder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(ConcurrentRunner.class)
public class JSONBinderStressTest {

	@Parameters(name = "Run from {0} to {1}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{0, 1000},
				{1000, 2000},
				{10000, 11000}
		});
	}

	private static final Pattern pattern = Pattern.compile("\\$\\{num\\}");

	private static AnnotatedTypeBinder<ComplexObject> meta;

	private static String data;

	private final int start;

	private final int finish;

	@BeforeClass
	public static void read() throws ParseException, IOException {
		meta = new AnnotatedTypeBinder<ComplexObject>(ComplexObject.class);

		CharBuffer buf = CharBuffer.allocate(512);
		StringBuilder out = new StringBuilder();
		final InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("data.template");
		try {
			final InputStreamReader reader = new InputStreamReader(stream);
			while (0 <= reader.read(buf)) {
				out.append(buf.flip());
				buf.clear();
			}
			data = out.toString();
		} catch (IOException e) {
			stream.close();
		}
	}

	public JSONBinderStressTest(int start, int finish) {
		this.start = start;
		this.finish = finish;
	}

	@Test
	public void test() throws ParseException {
		final JSONBinder<ComplexObject> binder = new JSONBinder<ComplexObject>(meta);

		for (int i = start; i < finish; i++) {
			StringReader stream = new StringReader(pattern.matcher(data).replaceAll(String.valueOf(i)));
			ComplexObject object = binder.pickUp(stream);
			assertThat(object, notNullValue());
			assertThat(object.getRoot(), is(i));
			assertThat(object.getValue(), is("value13-" + i));

			assertThat(object.getSimpleObject(), notNullValue());
			assertThat(object.getSimpleObject().getValue(), is("value22-" + i));

			assertThat(object.getSimpleObjects(), arrayWithSize(2));
			assertThat(object.getSimpleObjects()[0].getValue(), is("value31-" + i));
			assertThat(object.getSimpleObjects()[1].getValue(), is("value32-" + i));

			assertThat(object.getStrings(), hasSize(2));
			assertThat(object.getStrings().get(0), is("value41-" + i));
			assertThat(object.getStrings().get(1), is("value42-" + i));

			assertThat(object.getIntegers(), notNullValue());
			assertThat(object.getIntegers().length, is(2));
			assertThat(object.getIntegers()[0], is(i));
			assertThat(object.getIntegers()[1], is(-i));
		}
	}
}
