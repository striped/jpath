package org.kot.tools.pickup.reflective;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.anything;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 13/01/2014 20:29
 */
public class HasField<T> extends TypeSafeDiagnosingMatcher<T> {

	private final String fieldName;

	private final Matcher<?> valueMatcher;

	public HasField(final String fieldName, final Matcher<?> valueMatcher) {
		this.fieldName = fieldName;
		this.valueMatcher = valueMatcher;
	}

	@Factory
	public static <T> Matcher<T> hasField(String fieldName) {
		return hasField(fieldName, anything());
	}

	@Factory
	public static <T> Matcher<T> hasField(String fieldName, Matcher<?> valueMatcher) {
		return new HasField<T>(fieldName, valueMatcher);
	}

	@Override
	public void describeTo(final Description description) {
		description.appendText("has field ").appendValue(fieldName).appendText(" that ").appendDescriptionOf(valueMatcher);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected boolean matchesSafely(final T object, final Description mismatchDescription) {
		return fieldOn(object.getClass(), mismatchDescription)
				.and(withValue(object))
				.matching((Matcher<Object>) valueMatcher, "field '" + fieldName + "' ");
	}

	private Condition<Field> fieldOn(final Class<?> clazz, final Description mismatchDescription) {
		try {
			final Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return Condition.matched(field, mismatchDescription);
		} catch (NoSuchFieldException e) {
			mismatchDescription.appendText("No field ").appendValue(fieldName);
			return Condition.notMatched();
		}
	}

	private Condition.Step<Field, Object> withValue(final T object) {
		return new Condition.Step<Field, Object>() {
			@Override
			public Condition<Object> apply(Field field, Description mismatchDescription) {
				try {
					return Condition.matched(field.get(object), mismatchDescription);
				} catch (Exception e) {
					mismatchDescription.appendText(e.getMessage());
					return Condition.notMatched();
				}
			}
		};
	}
}
