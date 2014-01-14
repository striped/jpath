package org.kot.tools.pickup;

import java.util.Collection;

/**
 * Collection builder.
 * <p/>
 * Extends the {@link org.kot.tools.pickup.ObjectBuilder} to traverse current index (conditional pick up?). Apparently has a two predefined (by
 * provided {@link org.kot.tools.pickup.CollectionTypeMeta metadata}) states:
 * <ol>
 *     <li>binding primitive values - serves repetitive {@link #bind(String) value binding calls} and</li>
 *     <li>binding nested containers - serves nested containers by {@link #startObject() start mark} that follows by children's {@link #endObject() end mark}.</li>
 * </ol>
 * On final, binding built collection to the parent context.
 * @author <a href=mailto:striped@gmail.com>striped</a>
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
