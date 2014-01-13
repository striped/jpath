package org.kot.tools.pickup;

import java.util.Collection;

/**
* Description.
* @author <a href=mailto:striped@gmail.com>striped</a>
* @todo Add JavaDoc
* @created 13/01/2014 20:18
*/
public class CollectionBuilder<E> extends ObjectBuilder<Collection<E>> {

	private int idx;

	public CollectionBuilder(final CollectionTypeMeta<E> binder) {
		super(binder);
		idx = -1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObjectBuilder<?> startObject() {
		idx++;
		if (0 == idx) {
			instance = meta.newInstance();
			return this;
		}
		final ObjectBuilder<E> result = (ObjectBuilder<E>) super.startEntry("[" + idx + "]");
		ctx.pop();
		result.startObject();
		return result;
	}

	@Override
	public void bind(final String value) {
		ctx.push("[" + idx + "]");
		meta.bindSimple(ctx, instance, value);
		ctx.pop();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObjectBuilder<?> endObject() {
		if (ctx.isRoot() && null != parent) {
			meta.binder().bind(parent.getInstance(), instance);
			idx = -1;
			return parent;
		}
		return this;
	}
}
