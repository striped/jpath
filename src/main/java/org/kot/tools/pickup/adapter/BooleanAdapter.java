package org.kot.tools.pickup.adapter;

/**
* Description.
* @author <a href=mailto:striped@gmail.com>striped</a>
* @todo Add JavaDoc
* @created 03/12/2013 00:20
*/
public class BooleanAdapter implements Adapter<Boolean> {

	@Override
	public Boolean convertFrom(final String value) {
		return Boolean.parseBoolean(value);
	}

	@Override
	public String convertTo(final Boolean value) {
		return value.toString();
	}
}
