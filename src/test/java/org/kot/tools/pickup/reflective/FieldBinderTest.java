package org.kot.tools.pickup.reflective;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;
import static org.kot.tools.pickup.reflective.HasField.hasField;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 13/01/2014 20:20
 */
@RunWith(Parameterized.class)
public class FieldBinderTest<T> {

	private final Object holder;

	private final T value;

	private final Field field;

	private final Class<T> clazz;

	@Parameters(name = "{index}: check binding of {0} ({1})")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{Byte.TYPE, (byte) 127, new Object() {
					private byte field;
				}},
				{Byte.class, (byte) 127, new Object() {
					private Byte field;
				}},
				{Short.TYPE, (short) 10000, new Object() {
					private short field;
				}},
				{Short.class, (short) 10000, new Object() {
					private Short field;
				}},
				{Integer.TYPE, 100000, new Object() {
					private int field;
				}},
				{Integer.class, 100000, new Object() {
					private Integer field;
				}},
				{Long.TYPE, 10000000000L, new Object() {
					private long field;
				}},
				{Long.class, 10000000000L, new Object() {
					private Long field;
				}},
				{Float.TYPE, 1.0F, new Object() {
					private float field;
				}},
				{Float.class, 1.0F, new Object() {
					private Float field;
				}},
				{Double.TYPE, 1.0, new Object() {
					private double field;
				}},
				{Double.class, 1.0, new Object() {
					private Double field;
				}},
				{String.class, "test", new Object() {
					private String field;
				}},
				{Object.class, new Object(), new Object() {
					private Object field;
				}}
		});
	}

	public FieldBinderTest(final Class<T> clazz, final T value, final Object holder) throws NoSuchFieldException {
		this.holder = holder;
		this.field = holder.getClass().getDeclaredField("field");
		this.clazz = clazz;
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws NoSuchFieldException {

		final Class<T> type = (Class<T>) field.getType();
		new FieldBinder<T>(field).bind(holder, value);

		assertThat(type, equalTo(clazz));
		assertThat(holder, hasField("field", is(value)));
	}
}
