package org.kot.tools.pickup.reflective;

import org.kot.tools.pickup.Binder;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 12/01/2014 21:44
 */
class NullBinder<T> implements Binder<T> {

	@Override
	public void bind(final Object holder, final T value) {}
}
