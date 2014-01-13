package org.kot.tools.pickup.reflective;

import org.kot.tools.pickup.Binder;
import org.kot.tools.pickup.Branch;
import org.kot.tools.pickup.CollectionBuilder;
import org.kot.tools.pickup.CollectionTypeMeta;
import org.kot.tools.pickup.JPath;
import org.kot.tools.pickup.ObjectTypeMeta;
import org.kot.tools.pickup.ObjectBuilder;
import org.kot.tools.pickup.TypeMeta;
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
public class AnnotatedTypeMeta<T> implements ObjectTypeMeta<T> {

	private final Class<?> clazz;

	private Binder<T> binder;

	private final Map<Branch, TypeMeta<?>> children;

	@SuppressWarnings("unchecked")
	public AnnotatedTypeMeta(Class<?> clazz) {
		this.clazz = clazz;
		binder = new NullBinder<T>();
		children = new HashMap<Branch, TypeMeta<?>>();
		// check fields
		final Iterator<Field> i = Utils.iterateFieldsOf(clazz, JPath.class);
		while (i.hasNext()) {
			Field f = i.next();
			final Branch path = Branch.from(f.getAnnotation(JPath.class).value());
			final Adapter<?> adapter = Adapters.getFor(f.getType());
			TypeMeta<?> meta;
			if (null != adapter) {
				children.put(path, meta = new PrimitiveMeta(adapter));
			} else if (f.getType().isArray() || List.class.isAssignableFrom(f.getType())) {
				final Class<?> elementType = Utils.lookupElementType(f);
				meta = new ListTypeMeta(elementType);
				children.put(path, meta);
			} else {
				meta = new AnnotatedTypeMeta(f.getType());
				children.put(path, meta);
			}
			meta.setBinder((f.getType().isArray())? new ArrayFieldBinder(f): new FieldBinder<Collection>(f));
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <O> ObjectBuilder<O> lookupFor(final Branch path) {
		final TypeMeta<O> meta = (TypeMeta<O>) children.get(path);
		if (meta instanceof CollectionTypeMeta) {
			return new CollectionBuilder((CollectionTypeMeta) meta);
		}
		if (meta instanceof ObjectTypeMeta) {
			return new ObjectBuilder<O>((ObjectTypeMeta) meta);
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
	public Binder<T> binder() {
		return binder;
	}

	public void setBinder(final Binder<T> binder) {
		this.binder = binder;
	}
}
