package org.kot.tools.pickup.reflective;

import org.junit.Test;
import org.kot.tools.pickup.JPath;
import org.kot.tools.pickup.adapter.Adapter;
import org.kot.tools.pickup.adapter.DateAdapter;
import org.kot.tools.pickup.adapter.DoubleAdapter;
import org.kot.tools.pickup.adapter.FloatAdapter;
import org.kot.tools.pickup.adapter.IntegerAdapter;
import org.kot.tools.pickup.adapter.LongAdapter;
import org.kot.tools.pickup.json.ComplexObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.kot.tools.pickup.reflective.Utils.genericParametersOf;
import static org.kot.tools.pickup.reflective.Utils.iterateFieldsOf;

/**
 * Test suite for reflection utility
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @created 01/01/2014 18:06
 */
public class UtilsTest {

	@Test
	public void testFieldIterator() throws NoSuchFieldException {
		final Iterator<Field> i = iterateFieldsOf(ComplexObject.class, JPath.class);

		final List<Field> fields = new ArrayList<Field>();
		while (i.hasNext()) {
			fields.add(i.next());
		}

		assertThat(fields, contains(getAnnotatedFields(ComplexObject.class)));
	}

	@Test
	public void testLookupOfAdapterGenericParameter() {
		assertThat(genericParametersOf(IntegerAdapter.class, Adapter.class), arrayContaining((Type) Integer.class));
		assertThat(genericParametersOf(LongAdapter.class, Adapter.class), arrayContaining((Type) Long.class));
		assertThat(genericParametersOf(FloatAdapter.class, Adapter.class), arrayContaining((Type) Float.class));
		assertThat(genericParametersOf(DoubleAdapter.class, Adapter.class), arrayContaining((Type) Double.class));
		assertThat(genericParametersOf(DateAdapter.class, Adapter.class), arrayContaining((Type) Date.class));
	}

	static Field[] getAnnotatedFields(final Class<ComplexObject> clazz) throws NoSuchFieldException {
		final ArrayList<Field> result = new ArrayList<Field>();
		for (Class c = clazz; c != null; c = c.getSuperclass()) {
			for (Field f : c.getDeclaredFields()) {
				final JPath a = f.getAnnotation(JPath.class);
				if (null != a) {
					result.add(f);
				}
			}
		}
		return result.toArray(new Field[result.size()]);
	}
}
