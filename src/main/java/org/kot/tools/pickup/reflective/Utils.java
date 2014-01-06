package org.kot.tools.pickup.reflective;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.ServiceConfigurationError;

/**
 * Utility around reflection that is used.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @created 01/01/2014 17:39
 */
class Utils {

	/**
	 * Returns iterator over fields of specified {@code clazz}, including ancestors, annotated with certain annotation (i.e. {@code annotatedAs}).
	 * @param clazz The class which fields are requested.
	 * @param annotatedAs The annotation.
	 * @param <A> The type of annotation.
	 * @return The requested iterator.
	 */
	public static <A extends Annotation> Iterator<Field> iterateFieldsOf(final Class<?> clazz, final Class<A> annotatedAs) {
		return new Iterator<Field>() {

			private int fIdx = -1;
			private Class<?> c = clazz;
			private Field next = prepareNext();

			@Override
			public boolean hasNext() {
				return null != next;
			}

			@Override
			public Field next() {
				final Field result = next;
				next = prepareNext();
				return result;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Field iterator doesn't allow modifications");
			}

			private Field prepareNext() {
				if (null == c || Object.class == c) {
					return null;
				}
				final Field[] fields = c.getDeclaredFields();
				for (int i = fIdx + 1; i < fields.length; i++) {
					final A a = fields[i].getAnnotation(annotatedAs);
					if (null != a) {
						fIdx = i;
						return fields[i];
					}
				}
				c = c.getSuperclass();
				return prepareNext();
			}
		};
	}

	/**
	 * Lookup parameter of generic interface (i.e. {@code face}) implemented by specified {@code clazz}
	 * @param clazz The class is looking in.
	 * @param face The parameterized interface implemented
	 * @param <A> The type of interface.
	 * @return The parameter of interface specified in implementation.
	 */
	public static <A> Type[] genericParametersOf(final Class<? extends A> clazz, final Class<A> face) {
		for (Class c = clazz; c != null; c = c.getSuperclass()) {
			for (Type type : c.getGenericInterfaces()) {
				if (type instanceof ParameterizedType && face == ((ParameterizedType)type).getRawType()) {
					return ((ParameterizedType)type).getActualTypeArguments();
				}
			}
		}
		throw new ServiceConfigurationError("Can't figure out generic parameters of " + face + " in " + clazz);
	}

	@SuppressWarnings("unchecked")
	public static <E> Class<E> lookupElementType(final Field field) {
		if (field.getType().isArray()) {
			return (Class<E>) field.getType().getComponentType();
		}
		final Type type = field.getGenericType();
		if (type instanceof ParameterizedType) {
			final Type[] args = ((ParameterizedType) type).getActualTypeArguments();
			if (0 < args.length) {
				return (Class<E>) args[0];
			}
		}
		return (Class<E>) String.class;
	}
}
