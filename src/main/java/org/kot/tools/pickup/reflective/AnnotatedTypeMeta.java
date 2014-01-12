package org.kot.tools.pickup.reflective;

import org.kot.tools.pickup.Binder;
import org.kot.tools.pickup.Branch;
import org.kot.tools.pickup.CollectionTypeMeta;
import org.kot.tools.pickup.JPath;
import org.kot.tools.pickup.ObjectMeta;
import org.kot.tools.pickup.ObjectBuilder;
import org.kot.tools.pickup.adapter.Adapter;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
* Description.
* @author <a href=mailto:striped@gmail.com>striped</a>
* @todo Add JavaDoc
* @created 29/11/2013 22:52
*/
public class AnnotatedTypeMeta<T> implements ObjectMeta<T> {

	private final Class<?> clazz;

	private Binder<T> binder;

	private final Map<Branch, Binder<?>> children;
	private final Map<Branch, ObjectMeta<?>> nested;

	@SuppressWarnings("unchecked")
	public AnnotatedTypeMeta(Class<?> clazz) {
		this.clazz = clazz;
		binder = new NullBinder<T>();
		children = new HashMap<Branch, Binder<?>>();
		nested = new HashMap<Branch, ObjectMeta<?>>();
		final Iterator<Field> i = Utils.iterateFieldsOf(clazz, JPath.class);
		while (i.hasNext()) {
			Field f = i.next();
			final Branch path = Branch.from(f.getAnnotation(JPath.class).value());
			final Adapter<?> adapter = Adapters.getFor(f.getType());
			if (null != adapter) {
				children.put(path, new PrimitiveBinder(f, adapter));
			} else if (f.getType().isArray() || List.class.isAssignableFrom(f.getType())) {
				final Class<?> elementType = Utils.lookupElementType(f);
				final ListTypeMeta meta = new ListTypeMeta(elementType);
				meta.setBinder((f.getType().isArray())? new ArrayFieldBinder(f): new FieldBinder<Collection>(f));
				nested.put(path, meta);
			} else {
				final AnnotatedTypeMeta<?> meta = new AnnotatedTypeMeta(f.getType());
				meta.setBinder(new FieldBinder(f));
				nested.put(path, meta);
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <O> ObjectBuilder<O> lookupContainer(final Branch path) {
		final ObjectMeta<O> field = (ObjectMeta<O>) nested.get(path);
		if (field instanceof CollectionTypeMeta) {
			return new ObjectBuilder.CollectionBuilder<O>(field);
		}
		if (field != null) {
			return new ObjectBuilder<O>(field);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void bindSimple(Branch path, final T instance, final String value) {
		final Binder<String> binder = (Binder<String>) children.get(path);
		if (null != binder) {
			binder.bind(instance, value);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public T newInstance() {
		try {
			return (T) clazz.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("Can't instantiate " + clazz, e);
		}
	}

	@Override
	public Binder<T> binderToParent() {
		return binder;
	}

	public AnnotatedTypeMeta<T> setBinder(final Binder<T> binder) {
		this.binder = binder;
		return this;
	}
}
