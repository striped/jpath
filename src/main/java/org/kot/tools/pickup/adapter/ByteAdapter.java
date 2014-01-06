package org.kot.tools.pickup.adapter;

/**
* Description.
* @author <a href=mailto:striped@gmail.com>striped</a>
* @todo Add JavaDoc
* @created 03/12/2013 00:21
*/
public class ByteAdapter implements Adapter<Byte> {

	@Override
	public Byte convertFrom(final String value) {
		return Byte.parseByte(value);
	}

	@Override
	public String convertTo(final Byte value) {
		return String.valueOf(value);
	}
}
