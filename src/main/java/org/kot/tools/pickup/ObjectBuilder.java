package org.kot.tools.pickup;

import java.util.HashMap;
import java.util.Map;

/**
* Description.
* @author <a href=mailto:striped@gmail.com>striped</a>
* @todo Add JavaDoc
* @created 29/11/2013 22:52
*/
public class ObjectBuilder<T> {

	protected final MutableBranch ctx;

	protected final ObjectTypeMeta<T> meta;

	protected final Map<Branch, ObjectBuilder<?>> cache;

	protected ObjectBuilder<?> parent;

	protected T instance;

	public ObjectBuilder(ObjectTypeMeta<T> meta) {
		this.ctx = new MutableBranch();
		this.meta = meta;
		this.cache = new HashMap<Branch, ObjectBuilder<?>>();
	}

	public ObjectBuilder<?> startObject() {
		if (ctx.isRoot()) {
			instance = meta.newInstance();
		}
		return this;
	}

	public ObjectBuilder<?> startEntry(final String key) {
		ctx.push(key);

		if (cache.containsKey(ctx)) {
			return cache.get(ctx);
		}
		final ObjectBuilder<?> candidate = meta.lookupFor(ctx);
		if (null == candidate) {
			return this;
		}
		candidate.parent = this;

		cache.put(ctx.freeze(), candidate);
		return candidate;
	}

	public void bind(final String value) {
		meta.bindSimple(ctx, instance, value);
	}

	public void endEntry() {
		ctx.pop();
	}

	public ObjectBuilder<?> endObject() {
		if (ctx.isRoot() && null != parent) {
			final Binder<T> binder = meta.binder();
			binder.bind(parent.getInstance(), instance);
			return parent;
		}
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();
		if (null != parent) {
			result.append(parent);
		}
		result.append(ctx);
		return result.toString();
	}

	public T getInstance() {
		return instance;
	}

}
