package org.kot.tools.pickup.reflective;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 12/01/2014 20:59
 */
class ArrayFieldBinder<E> extends FieldBinder<Collection<E>> {

	private final Class<E> elementType;

	public ArrayFieldBinder(final Field field) {
		super(field);
		this.elementType = Utils.lookupElementType(field);
	}

	public void bind(Object holder, Collection<E> value) {
		Object obj = Array.newInstance(elementType, value.size());
		int i = 0;
		for (E item : value) {
			Array.set(obj, i++, item);
		}
		try {
			field.set(holder, obj);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Cant bound " + value + " to " + field, e);
		}
	}

}
