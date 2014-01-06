package org.kot.tools.pickup.adapter;

/**
* Description.
* @author <a href=mailto:striped@gmail.com>striped</a>
* @todo Add JavaDoc
* @created 03/12/2013 00:21
*/
public class DoubleAdapter implements Adapter<Double> {

	@Override
	public Double convertFrom(final String value) {
		return Double.parseDouble(value);
	}

	@Override
	public String convertTo(final Double value) {
		return String.valueOf(value);
	}
}
