package org.kot.tools.pickup.reflective;

import org.kot.tools.pickup.Binder;
import org.kot.tools.pickup.TypeMeta;
import org.kot.tools.pickup.adapter.Adapter;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 03/12/2013 21:49
 */
class PrimitiveMeta<T> implements Binder<String>, TypeMeta<T> {

	private final Adapter<T> adapter;

	private Binder<T> binder;

	public PrimitiveMeta(final Adapter<T> adapter) {
		this.adapter = adapter;
	}

	@Override
	public void bind(final Object holder, final String value) {
		binder.bind(holder, adapter.convertFrom(value));
	}

	public void setBinder(final Binder<T> binder) {
		this.binder = binder;
	}
}
