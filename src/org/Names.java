package org;

import java.util.HashMap;
import java.util.Map;

public class Names<T extends Name> {

	private Map<String, T> _names = new HashMap<String, T>();
	private final Class<T> _clazz;

	public Names(Class<T> clazz) {
		_clazz = clazz;
	}

	public T get(String name) {
		if (!_names.containsKey(name)) {
			T t;
			try {
				t = _clazz.newInstance();
				t.setName(name);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			_names.put(name, t);
		}
		return _names.get(name);
	}
}
