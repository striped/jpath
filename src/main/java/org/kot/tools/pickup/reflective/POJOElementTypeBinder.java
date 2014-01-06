package org.kot.tools.pickup.reflective;

import org.kot.tools.pickup.Binder;

import java.util.List;

/**
* Description.
* @author <a href=mailto:striped@gmail.com>striped</a>
* @todo Add JavaDoc
* @created 04/01/2014 14:36
*/
class POJOElementTypeBinder<E> extends AnnotatedTypeBinder<E> implements Binder<E> {

	public POJOElementTypeBinder(final Class<?> clazz) {
		super(clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void bind(final Object holder, final E value) {
		((List<E>) holder).add(value);
	}
}
