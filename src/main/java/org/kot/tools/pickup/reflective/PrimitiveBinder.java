package org.kot.tools.pickup.reflective;

import org.kot.tools.pickup.Binder;
import org.kot.tools.pickup.adapter.Adapter;

import java.lang.reflect.Field;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 03/12/2013 21:49
 */
class PrimitiveBinder implements Binder<String> {

	private final Field field;

	private final Adapter<?> adapter;

	public PrimitiveBinder(final Field field, final Adapter<?> adapter) {
		field.setAccessible(true);
		this.field = field;
		this.adapter = adapter;
	}

	@Override
	public void bind(final Object holder, final String value) {
		try {
			field.set(holder, adapter.convertFrom(value));
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Cant bound " + value + " to " + field, e);
		}
	}
}
