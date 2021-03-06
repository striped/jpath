package org.kot.tools.pickup.adapter;

/**
* Description.
* @author <a href=mailto:striped@gmail.com>striped</a>
* @todo Add JavaDoc
* @created 03/12/2013 00:21
*/
public class LongAdapter implements Adapter<Long> {

	@Override
	public Long convertFrom(final String value) {
		return Long.parseLong(value);
	}

	@Override
	public String convertTo(final Long value) {
		return String.valueOf(value);
	}
}
