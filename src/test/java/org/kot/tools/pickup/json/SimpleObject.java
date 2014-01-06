package org.kot.tools.pickup.json;

import org.kot.tools.pickup.JPath;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 29/11/2013 22:43
 */
public class SimpleObject {

	@JPath("value")
	private String root;

	public String getValue() {
		return root;
	}
}
