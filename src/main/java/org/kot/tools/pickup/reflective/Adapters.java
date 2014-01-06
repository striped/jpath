package org.kot.tools.pickup.reflective;

import org.kot.tools.pickup.adapter.Adapter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * Description.
 * @author <a href=mailto:striped@gmail.com>striped</a>
 * @todo Add JavaDoc
 * @created 03/12/2013 00:01
 */
class Adapters {

	private static final Adapters instance = new Adapters();

	private final Map<Type, Adapter> loaded;

	@SuppressWarnings("unchecked")
	public static <T> Adapter<T> getFor(Class<T> type) {
		final Map<Type, Adapter> adapters = instance.loaded;
		if (type.isPrimitive()) {
			if (boolean.class == type) {
				return adapters.get(Boolean.class);
			}
			if (byte.class == type) {
				return adapters.get(Byte.class);
			}
			if (short.class == type) {
				return adapters.get(Short.class);
			}
			if (int.class == type) {
				return adapters.get(Integer.class);
			}
			if (long.class == type) {
				return adapters.get(Long.class);
			}
			if (float.class == type) {
				return adapters.get(Float.class);
			}
			if (double.class == type) {
				return adapters.get(Double.class);
			}
		}
		return adapters.get(type);
	}

	public void reload() {
		for (Adapter adapter : ServiceLoader.load(Adapter.class)) {
			Type[] params = Utils.genericParametersOf(adapter.getClass(), Adapter.class);
			if (1 != params.length) {
				throw new ServiceConfigurationError("Adapter " + adapter.getClass() + " has unexpected generic parameters");
			}
			loaded.put(params[0], adapter);
		}
	}

	Adapters() {
		loaded = new HashMap<Type, Adapter>();
		reload();
	}

}
