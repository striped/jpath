package org.kot.tools.pickup;

import java.util.HashMap;
import java.util.Map;

/**
 * Object builder class.
 * <p/>
 * Builds object according to events coming from relevant parser. FSM with following states:
 * <ol>
 *     <li>{@link #startObject()} - entry point to current object construction, prepares everything for upcoming events. May transit to {@link #startEntry(String)} or
 *     {@link #endObject()},</li>
 *     <li>{@link #startEntry(String)} - switch to a state either a new container (so, builder) have to be prepared or next expected entry is just a primitive value,</li>
 *     <li>{@link #bind(String)} - binds provided value to current container,</li>
 *     <li>{@link #endEntry()} - updates traversal path that identifies a current context,</li>
 *     <li>{@link #endObject()} - wrap up current context and bind current container to a parent one (if any),</li>
 * </ol>
 * <p/>
 * Provided metadata may be shared with other builder, but builder instance supposed to be used exclusively by thread. Consequent reusing are welcomed (child,
 * once instantiated may be reused so reduce pressure on GC).
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @created 29/11/2013 22:52
 */
public class ObjectBuilder<T> {

	/* traversal path */
	protected final MutableBranch ctx;

	/* expected object metadata */
	protected final ObjectTypeMeta<T> meta;

	/* nested context cache */
	protected final Map<Branch, ObjectBuilder<?>> cache;

	/* parent context reference */
	protected ObjectBuilder<?> parent;

	/* product of building */
	protected T instance;

	/**
	 * Constructs builder with provided metadata.
	 * @param meta An expected object metadata
	 */
	public ObjectBuilder(ObjectTypeMeta<T> meta) {
		this.ctx = new MutableBranch();
		this.meta = meta;
		this.cache = new HashMap<Branch, ObjectBuilder<?>>();
	}

	/**
	 * Callback on entries container (collection or POJO) section start.
	 * @return The builder instance supposed to be called on a next event coming (for a POJO always this).
	 */
	public ObjectBuilder<?> startObject() {
		if (ctx.isRoot()) {
			instance = meta.newInstance();
		}
		return this;
	}

	/**
	 * Callback on a new entry. The specified {@code key} designated identify the next state of this builder. May return a new builder instance in case if a new container is
	 * expected or {@code this} if a primitive value should go next.
	 * @param key The key associated with a new entry.
	 * @return The builder instance prepared to handle next event.
	 */
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

	/**
	 * Try to bind a value to current primitive entry, if any.
	 * @param value Textual representation of current entry data.
	 */
	public void bind(final String value) {
		meta.bindSimple(ctx, instance, value);
	}

	/**
	 * Callback on an entry finish.
	 */
	public void endEntry() {
		ctx.pop();
	}

	/**
	 * Callback on entries container (collection or POJO) section finish. Used to bind the current product with parent builder (if any) and context fold out (so delegating all
	 * upcoming events to parent one).
	 * @return The builder instance supposed to be called on a next event coming.
	 */
	public ObjectBuilder<?> endObject() {
		if (ctx.isRoot() && null != parent) {
			final Binder<T> binder = meta.binder();
			binder.bind(parent.getInstance(), instance);
			return parent;
		}
		return this;
	}

	/**
	 * Textual representation of this builder for debug / tracing purposes.
	 * @return The string representing this builder.
	 */
	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();
		if (null != parent) {
			result.append(parent);
		}
		result.append(ctx);
		return result.toString();
	}

	/**
	 * Gets the built instance.
	 * @return An expected object instance that was built during incoming events handling.
	 */
	public T getInstance() {
		return instance;
	}

}
