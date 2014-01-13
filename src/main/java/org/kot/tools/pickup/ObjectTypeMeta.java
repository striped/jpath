package org.kot.tools.pickup;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 30/12/2013 12:10
 */
public interface ObjectTypeMeta<T> extends TypeMeta<T> {

	<O> ObjectBuilder<O> lookupFor(Branch path);

	void bindSimple(Branch path, final T instance, final String value);

	T newInstance();

	Binder<T> binder();
}
