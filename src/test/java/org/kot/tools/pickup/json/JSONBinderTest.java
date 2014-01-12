package org.kot.tools.pickup.json;

import net.minidev.json.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kot.tools.pickup.reflective.AnnotatedTypeMeta;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class JSONBinderTest {

	private static ComplexObject object;

	@BeforeClass
	public static void parse() throws ParseException, IOException {
		final InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("complex.json");
		try {
			final AnnotatedTypeMeta<ComplexObject> meta = new AnnotatedTypeMeta<ComplexObject>(ComplexObject.class);
			final JSONBinder<ComplexObject> binder = new JSONBinder<ComplexObject>(meta);
			object = binder.pickUp(new InputStreamReader(stream));
		} finally {
			stream.close();
		}
		assertThat(object, notNullValue());
	}

	@Test
	public void testPrimitiveValueAtRoot() throws ParseException {
		assertThat(object.getRoot(), greaterThan(0));
	}

	@Test
	public void testNestedPrimitiveValue() throws ParseException {
		assertThat(object.getValue(), notNullValue());
	}

	@Test
	public void testNestedSimpleObject() throws ParseException {
		assertThat(object.getSimpleObject(), notNullValue());
		assertThat(object.getSimpleObject().getValue(), notNullValue());
	}

	@Test
	public void testCollectionOfSimpleObject() throws ParseException {
		assertThat(object.getSimpleObjects(), notNullValue());
		assertThat(object.getSimpleObjects(), arrayWithSize(greaterThan(1)));
		for (SimpleObject obj : object.getSimpleObjects()) {
			assertThat(obj.getValue(), notNullValue());
		}
	}

	@Test
	public void testCollectionOfReferencedObjects() throws ParseException {
		assertThat(object.getStrings(), notNullValue());
		assertThat(object.getStrings(), hasSize(greaterThan(1)));
		for (String string : object.getStrings()) {
			assertThat(string, notNullValue());
		}
	}

	@Test
	public void testCollectionOfPrimitives() throws ParseException {
		assertThat(object.getIntegers(), notNullValue());
		assertThat(object.getIntegers().length, greaterThan(1));
		for (int number : object.getIntegers()) {
			assertThat(number, greaterThan(0));
		}
	}
}
