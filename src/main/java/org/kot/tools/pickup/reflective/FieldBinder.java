package org.kot.tools.pickup.reflective;

import org.kot.tools.pickup.Binder;

import java.lang.reflect.Field;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 12/01/2014 20:52
 */
class FieldBinder<T> implements Binder<T> {

	protected final Field field;

	public FieldBinder(final Field field) {
		this.field = field;
		field.setAccessible(true);
	}

	public void bind(Object holder, T value) {
		try {
			field.set(holder, value);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Cant bound " + value + " to " + field, e);
		}
	}
}
