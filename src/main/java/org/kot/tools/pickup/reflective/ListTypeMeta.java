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

	private ObjectTypeMeta<E> elementMeta;

	private final Adapter<E> primitiveAdapter;

	public ListTypeMeta(final Class<E> elementType) {
		primitiveAdapter = Adapters.getFor(elementType);
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
	public <O> ObjectBuilder<O> lookupContainer(final Branch path) {
		return new ObjectBuilder<O>((ObjectTypeMeta<O>) elementMeta);
	}

	@Override
	public void bindSimple(final Branch path, final Collection<E> instance, final String value) {
		instance.add(primitiveAdapter.convertFrom(value));
	}

	@Override
	public Binder<Collection<E>> binderToParent() {
		return binder;
	}

	@Override
	public List<E> newInstance() {
		return new ArrayList<E>();
	}

	public ListTypeMeta<E> setBinder(final Binder<Collection<E>> binder) {
		this.binder = binder;
		return this;
	}

}
