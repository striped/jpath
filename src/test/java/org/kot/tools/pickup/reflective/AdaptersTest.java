package org.kot.tools.pickup.reflective;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kot.tools.pickup.adapter.Adapter;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 05/12/2013 22:34
 */
@RunWith(Parameterized.class)
public class AdaptersTest {

	@Parameters(name = "{index}: Converting {0} {1}<->{2}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{String.class, "text", "text"},
				{Boolean.class, "true", true},
				{Byte.class, "1", (byte) 1},
				{Short.class, "1", (short) 1},
				{Integer.class, "1", 1},
				{Long.class, "1", 1L},
				{Float.class, "0.1", 0.1F},
				{Double.class, "0.1", 0.1},
				{Date.class, "1970-01-01T00:00:00+0000", new Date(0)}
		});
	}

	private final Class<?> clazz;

	private final String value;

	private final Object data;

	@Test
	public void test() {
		final Adapter adapter = Adapters.getFor(clazz);
		assertThat(adapter.convertFrom(value), is(data));
	}

	public AdaptersTest(final Class<?> clazz, final String value, final Object data) {
		this.clazz = clazz;
		this.value = value;
		this.data = data;
	}
}
