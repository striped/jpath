package org.kot.tools.pickup.reflective;

import org.kot.tools.pickup.Binder;

import java.util.List;

/**
* Description.
* @author <a href=mailto:striped@gmail.com>striped</a>
* @todo Add JavaDoc
* @created 12/01/2014 22:05
*/
class ListElementBinder<E> implements Binder<E> {

	@SuppressWarnings("unchecked")
	@Override
	public void bind(final Object holder, final E value) {
		((List<E>) holder).add(value);
	}
}
