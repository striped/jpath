package org.kot.tools.pickup.adapter;

/**
* Description.
* @author <a href=mailto:striped@gmail.com>striped</a>
* @todo Add JavaDoc
* @created 05/12/2013 22:31
*/
public class StringAdapter implements Adapter<String> {

	@Override
	public String convertFrom(final String value) {
		return value;
	}

	@Override
	public String convertTo(final String value) {
		return value;
	}
}
