package org.kot.tools.pickup.adapter;

/**
* Description.
* @author <a href=mailto:striped@gmail.com>striped</a>
* @todo Add JavaDoc
* @created 03/12/2013 00:21
*/
public class IntegerAdapter implements Adapter<Integer> {

	@Override
	public Integer convertFrom(final String value) {
		return Integer.parseInt(value);
	}

	@Override
	public String convertTo(final Integer value) {
		return String.valueOf(value);
	}
}
