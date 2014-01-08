package org.kot.tools.pickup;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 03/12/2013 20:52
 */
public class BranchTest {

	@Test
	public void testFromString() {
		assertThat(Branch.from(""), is((Branch) new MutableBranch()));
		assertThat(Branch.from("a").toString(), is("/a"));
		assertThat(Branch.from("a/b/c/d").toString(), is("/a/b/c/d"));
		assertThat(Branch.from("/a").toString(), is("/a"));
	}

	@Test
	public void testToString() {
		final MutableBranch path = new MutableBranch();
		path.push("a");
		path.push("b");
		path.push("c");
		assertThat(path.toString(), is("/a/b/c"));
	}

	@Test
	public void testEquals() {
		final MutableBranch path = new MutableBranch();
		path.push("a");
		path.push("b");
		path.push("c");
		assertThat(path, equalTo(Branch.from("/a/b/c")));
	}

	@Test
	public void testHashingUpdate() {
		final MutableBranch path = new MutableBranch();
		assertThat(path.hashCode(), is(1));
		path.push("a");
		assertThat(path.hashCode(), greaterThan(1));
		path.pop();
		assertThat(path.hashCode(), is(1));
	}

	@Test
	public void testFreezing() {
		final MutableBranch path = new MutableBranch();
		path.push("a");
		path.push("b");
		final Branch frozen = path.freeze();
		assertThat(path, equalTo(frozen));
		path.pop();
		assertThat(path, not(equalTo(frozen)));
		path.pop();
		assertThat(path, not(equalTo(frozen)));
		path.push("a");
		path.push("b");
		assertThat(path, equalTo(frozen));
	}
}
