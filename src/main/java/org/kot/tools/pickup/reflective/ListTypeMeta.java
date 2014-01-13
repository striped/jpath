package org.kot.tools.pickup.reflective;

import org.kot.tools.pickup.Binder;
import org.kot.tools.pickup.Branch;
import org.kot.tools.pickup.CollectionTypeMeta;
import org.kot.tools.pickup.ObjectTypeMeta;
import org.kot.tools.pickup.ObjectBuilder;
import org.kot.tools.pickup.adapter.Adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 01/01/2014 19:43
 */
class ListTypeMeta<E> implements CollectionTypeMeta<E> {

	private Binder<Collection<E>> binder;

	private PrimitiveMeta<E> primitiveMeta;

	private ObjectTypeMeta<E> elementMeta;

	public ListTypeMeta(final Class<E> elementType) {
		final Adapter<E> primitiveAdapter = Adapters.getFor(elementType);
		if (null != primitiveAdapter) {
			primitiveMeta = new PrimitiveMeta<E>(primitiveAdapter);
			primitiveMeta.setBinder(new ListElementBinder<E>());
		}
		if (null == primitiveAdapter) {
			if (elementType.isArray() || Collection.class.isAssignableFrom(elementType)) {
				throw new UnsupportedOperationException("array of array");
			}
			final AnnotatedTypeMeta<E> meta = new AnnotatedTypeMeta<E>(elementType);
			meta.setBinder(new ListElementBinder<E>());
			elementMeta = meta;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <O> ObjectBuilder<O> lookupFor(final Branch path) {
		return new ObjectBuilder<O>((ObjectTypeMeta<O>) elementMeta);
	}

	@Override
	public void bindSimple(final Branch path, final Collection<E> instance, final String value) {
		primitiveMeta.bind(instance, value);
	}

	@Override
	public Binder<Collection<E>> binder() {
		return binder;
	}

	@Override
	public List<E> newInstance() {
		return new ArrayList<E>();
	}

	public void setBinder(final Binder<Collection<E>> binder) {
		this.binder = binder;
	}

	static class ListElementBinder<E> implements Binder<E> {

		@SuppressWarnings("unchecked")
		@Override
		public void bind(final Object holder, final E value) {
			((List<E>) holder).add(value);
		}
	}
}
