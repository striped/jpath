package org.kot.tools.pickup.reflective;

import org.kot.tools.pickup.Binder;

import java.lang.reflect.Field;

/**
* Description.
* @author <a href=mailto:striped@gmail.com>striped</a>
* @todo Add JavaDoc
* @created 04/01/2014 14:38
*/
class ObjectTypeBinder extends AnnotatedTypeBinder<Object> implements Binder<Object> {

	private final Field field;

	public ObjectTypeBinder(Field field, Class<?> clazz) {
		super(clazz);
		field.setAccessible(true);
		this.field = field;
	}

	public void bind(Object holder, Object value) {
		try {
			field.set(holder, value);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Cant bound " + value + " to " + field, e);
		}
	}

}
