package org.kot.tools.pickup.adapter;

/**
* Description.
* @author <a href=mailto:striped@gmail.com>striped</a>
* @todo Add JavaDoc
* @created 03/12/2013 00:21
*/
public class FloatAdapter implements Adapter<Float> {

	@Override
	public Float convertFrom(final String value) {
		return Float.parseFloat(value);
	}

	@Override
	public String convertTo(final Float value) {
		return String.valueOf(value);
	}
}
