package org.kot.tools.pickup.reflective;

import org.kot.tools.pickup.Binder;
import org.kot.tools.pickup.Branch;
import org.kot.tools.pickup.CollectionBinder;
import org.kot.tools.pickup.ObjectBinder;
import org.kot.tools.pickup.ObjectBuilder;
import org.kot.tools.pickup.adapter.Adapter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 01/01/2014 19:43
 */
class ListTypeBinder<E> implements Binder<List<E>>, CollectionBinder<E> {

	protected final Field field;

	private final Class<E> elementType;

	private final boolean requireRepack;

	private ObjectBinder<E> complexElementBinder;

	private final Adapter<E> adapter;

	public ListTypeBinder(final Field field) {
		field.setAccessible(true);
		this.field = field;
		this.requireRepack = field.getType().isArray();
		this.elementType = Utils.lookupElementType(field);

		adapter = Adapters.getFor(elementType);
		if (null == adapter) {
			complexElementBinder = new POJOElementTypeBinder<E>(elementType);
		} else if (elementType.isArray() || List.class.isAssignableFrom(elementType)) {
			throw new UnsupportedOperationException("array of array");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public <O> ObjectBuilder<O> lookupContainer(final Branch path) {
		return new ObjectBuilder<O>((ObjectBinder<O>) complexElementBinder);
	}

	@Override
	public void bindSimple(final Branch path, final Collection<E> instance, final String value) {
		instance.add(adapter.convertFrom(value));
	}

	public void bind(Object holder, List<E> value) {
		Object obj = value;
		if (requireRepack) {
			obj = Array.newInstance(elementType, value.size());
			int i = 0;
			for (E item : value) {
				Array.set(obj, i++, item);
			}
		}
		try {
			field.set(holder, obj);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Cant bound " + value + " to " + field, e);
		}
	}

	@Override
	public List<E> newInstance() {
		return new ArrayList<E>();
	}
}
