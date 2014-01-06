package org.kot.tools.pickup;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 16/12/2013 22:23
 */
public interface Binder<T> {

	void bind(Object holder, T value);
}
