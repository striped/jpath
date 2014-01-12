package org.kot.tools.pickup.reflective;

import org.junit.Test;
import org.kot.tools.pickup.ObjectMeta;
import org.kot.tools.pickup.json.ComplexObject;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 16/12/2013 22:06
 */
public class AnnotatedTypeMetaTest {

	@Test
	public void test() {
		final ObjectMeta binder = new AnnotatedTypeMeta<ComplexObject>(ComplexObject.class);

	}

}
