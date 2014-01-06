package org.kot.tools.pickup;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 03/12/2013 22:02
 */
public class MutableBranch extends Branch {

	public MutableBranch() {
		super(new LinkedList<String>());
	}

	public void push(String name) {
		nodes().push(name);
		hash = nodes().hashCode();
	}

	public String pop() {
		final String result = nodes().pop();
		hash = nodes().hashCode();
		return result;
	}

	@SuppressWarnings("unchecked")
	private Deque<String> nodes() {
		return (Deque<String>) nodes;
	}
}
