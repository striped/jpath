package org.kot.tools.pickup.reflective;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
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
public class ArrayFieldBinderTest<T> {

	private final Object holder;

	private final Collection<T> value;

	private final Field field;

	private final Class<T> clazz;

	@Parameters(name = "{index}: check binding of array of {0} ({1})")
	public static Iterable<Object[]> data() {
		return asList(new Object[][] {
				{Byte.TYPE, asList((byte) 127), new Object() {
					private byte[] field;
				}},
				{Byte.class, asList((byte) 127), new Object() {
					private Byte[] field;
				}},
				{Short.TYPE, asList((short) 10000), new Object() {
					private short[] field;
				}},
				{Short.class, asList((short) 10000), new Object() {
					private Short[] field;
				}},
				{Integer.TYPE, asList(100000), new Object() {
					private int[] field;
				}},
				{Integer.class, asList(100000), new Object() {
					private Integer[] field;
				}},
				{Long.TYPE, asList(10000000000L), new Object() {
					private long[] field;
				}},
				{Long.class, asList(10000000000L), new Object() {
					private Long[] field;
				}},
				{Float.TYPE, asList(1.0F), new Object() {
					private float[] field;
				}},
				{Float.class, asList(1.0F), new Object() {
					private Float[] field;
				}},
				{Double.TYPE, asList(1.0), new Object() {
					private double[] field;
				}},
				{Double.class, asList(1.0), new Object() {
					private Double[] field;
				}},
				{String.class, asList("test"), new Object() {
					private String[] field;
				}},
				{Object.class, asList(new Object()), new Object() {
					private Object[] field;
				}}
		});
	}

	public ArrayFieldBinderTest(final Class<T> clazz, final Collection<T> value, final Object holder) throws NoSuchFieldException {
		this.holder = holder;
		this.field = holder.getClass().getDeclaredField("field");
		this.clazz = clazz;
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws NoSuchFieldException {

		final Class<T[]> type = (Class<T[]>) field.getType();
		final Class<T> elementType = (Class<T>) type.getComponentType();

		new ArrayFieldBinder<T>(field).bind(holder, value);

		assertThat(elementType, equalTo(clazz));
		assertThat(holder, hasField("field", notNullValue()));
	}
}
