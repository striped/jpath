package org.kot.tools.pickup.adapter;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 02/12/2013 23:59
 */
public interface Adapter<T> {

	T convertFrom(String value);

	String convertTo(T value);
}
