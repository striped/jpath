package org.kot.tools.pickup;

/**
* Description.
* @author <a href=mailto:striped@gmail.com>striped</a>
* @todo Add JavaDoc
* @created 29/11/2013 22:52
*/
public class ObjectBuilder<T> {

	protected final MutableBranch ctx;

	protected final ObjectBinder<T> binder;

	protected ObjectBuilder<?> parent;

	protected T instance;

	public ObjectBuilder(ObjectBinder<T> binder) {
		this.ctx = new MutableBranch();
		this.binder = binder;
	}

	public ObjectBuilder<?> startObject() {
		if (ctx.isRoot()) {
			instance = binder.newInstance();
		}
		return this;
	}

	public ObjectBuilder<?> startEntry(final String key) {
		ctx.push(key);

		final ObjectBinder<T> candidate = binder.lookupContainer(ctx);
		if (null == candidate) {
			return this;
		}
		final ObjectBuilder<?> next
				= (candidate instanceof CollectionBinder)? new CollectionBuilder<T>(candidate): new ObjectBuilder<T>(candidate);
		next.parent = this;
		return next;
	}

	public void bind(final String value) {
		binder.bindSimple(ctx, instance, value);
	}

	public void endEntry() {
		ctx.pop();
	}

	@SuppressWarnings("unchecked")
	public ObjectBuilder<?> endObject() {
		if (ctx.isRoot() && binder instanceof Binder) {
			((Binder<T>) binder).bind(parent.getInstance(), instance);
			return parent;
		}
		return this;
	}

	@Override
	public String toString() {
		return ctx.toString();
	}

	public T getInstance() {
		return instance;
	}

	static class CollectionBuilder<E> extends ObjectBuilder<E> {

		private int idx;

		public CollectionBuilder(final ObjectBinder<E> binder) {
			super(binder);
			idx = -1;
		}

		@SuppressWarnings("unchecked")
		@Override
		public ObjectBuilder<E> startObject() {
			idx++;
			if (0 == idx) {
				instance = binder.newInstance();
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
			binder.bindSimple(ctx, instance, value);
			ctx.pop();
		}

		@SuppressWarnings("unchecked")
		@Override
		public ObjectBuilder<?> endObject() {
			if (ctx.isRoot() && binder instanceof Binder) {
				((Binder<E>) binder).bind(parent.getInstance(), instance);
				idx = -1;
				return parent;
			}
			return this;
		}
	}

}
