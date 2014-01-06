package org.kot.tools.pickup.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 03/12/2013 00:25
 */
public class DateAdapter implements Adapter<Date> {

	private ThreadLocal<SimpleDateFormat> format = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		}
	};

	@Override
	public Date convertFrom(final String value) {
		try {
			return format.get().parse(value);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Can't parse date from " + value);
		}
	}

	@Override
	public String convertTo(final Date value) {
		return format.get().format(value);
	}
}
