package org.kot.tools.pickup.json;

import org.kot.tools.pickup.JPath;

import java.util.List;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 30/11/2013 00:57
 */
public class ComplexObject {

	@JPath("root")
	private String root;

	@JPath("obj1/obj13/simple131")
	private String value;

	@JPath("obj2/obj22")
	private SimpleObject obj;

	@JPath("obj3")
	private SimpleObject[] objects;

	@JPath("obj4")
	private List<String> strings;

	@JPath("obj5")
	private int[] ints;

	public String getRoot() {
		return root;
	}

	public String getValue() {
		return value;
	}

	public SimpleObject getSimpleObject() {
		return obj;
	}

	public SimpleObject[] getSimpleObjects() {
		return objects;
	}

	public List<String> getStrings() {
		return strings;
	}

	public int[] getIntegers() {
		return ints;
	}
}
