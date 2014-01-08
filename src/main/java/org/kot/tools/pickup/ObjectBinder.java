package org.kot.tools.pickup;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 30/12/2013 12:10
 */
public interface ObjectBinder<T> {

	<O> ObjectBuilder<O> lookupContainer(Branch path);

	void bindSimple(Branch path, final T instance, final String value);

	T newInstance();
}
