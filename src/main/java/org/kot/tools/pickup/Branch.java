package org.kot.tools.pickup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 03/12/2013 20:22
 */
public class Branch {

	protected int hash;

	protected final List<String> nodes;

	public Branch(List<String> path) {
		nodes = path;
		hash = nodes.hashCode();
	}

	public static Branch from(String path) {
		final List<String> nodes = new ArrayList<String>();
		int start = 0, pos = path.indexOf('/');
		for (; 0 <= pos; pos = path.indexOf('/', start)) {
			if (start != pos) {
				nodes.add(path.substring(start, pos));
			}
			start = pos + 1;
		}
		if (start < path.length()) {
			nodes.add(path.substring(start));
		}
		Collections.reverse(nodes);
		return new Branch(nodes);
	}

	public boolean isRoot() {
		return nodes.isEmpty();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof Branch)) {
			return false;
		}
		final Branch that = (Branch) o;
		return hash == that.hash && nodes.equals(that.nodes);

	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		ListIterator<String> i = nodes.listIterator(nodes.size());
		while (i.hasPrevious()) {
			sb.append('/').append(i.previous());
		}
		return sb.toString();
	}
}
