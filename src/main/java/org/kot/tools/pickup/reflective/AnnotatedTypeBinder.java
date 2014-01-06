package org.kot.tools.pickup.reflective;

import org.kot.tools.pickup.Binder;
import org.kot.tools.pickup.Branch;
import org.kot.tools.pickup.JPath;
import org.kot.tools.pickup.ObjectBinder;
import org.kot.tools.pickup.adapter.Adapter;

import java.lang.reflect.Field;
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
public class AnnotatedTypeBinder<T> implements ObjectBinder<T> {

	private final Class<?> clazz;

	private final Map<Branch, Binder<?>> children;

	public AnnotatedTypeBinder(Class<?> clazz) {
		this.clazz = clazz;
		children = new HashMap<Branch, Binder<?>>();
		final Iterator<Field> i = Utils.iterateFieldsOf(clazz, JPath.class);
		while (i.hasNext()) {
			Field f = i.next();
			final Branch path = Branch.from(f.getAnnotation(JPath.class).value());
			final Adapter<?> adapter = Adapters.getFor(f.getType());
			if (null != adapter) {
				children.put(path, new PrimitiveBinder(f, adapter));
			} else if (f.getType().isArray() || List.class.isAssignableFrom(f.getType())) {
				children.put(path, new ListTypeBinder(f));
			} else {
				children.put(path, new ObjectTypeBinder(f, f.getType()));
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <O> ObjectBinder<O> lookupContainer(final Branch path) {
		final Binder<?> field = children.get(path);
		if (field instanceof ObjectBinder) {
			return (ObjectBinder<O>) field;
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
}
