package org.kot.tools.pickup.adapter;

/**
* Description.
* @author <a href=mailto:striped@gmail.com>striped</a>
* @todo Add JavaDoc
* @created 03/12/2013 00:21
*/
public class ShortAdapter implements Adapter<Short> {

	@Override
	public Short convertFrom(final String value) {
		return Short.parseShort(value);
	}

	@Override
	public String convertTo(final Short value) {
		return String.valueOf(value);
	}
}
