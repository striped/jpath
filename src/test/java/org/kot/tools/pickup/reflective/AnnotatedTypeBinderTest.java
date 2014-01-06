package org.kot.tools.pickup.reflective;

import org.junit.Test;
import org.kot.tools.pickup.ObjectBinder;
import org.kot.tools.pickup.json.ComplexObject;
import org.kot.tools.pickup.reflective.AnnotatedTypeBinder;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 16/12/2013 22:06
 */
public class AnnotatedTypeBinderTest {

	@Test
	public void test() {
		final ObjectBinder binder = new AnnotatedTypeBinder<ComplexObject>(ComplexObject.class);

	}

}
