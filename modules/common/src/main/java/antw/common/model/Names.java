package antw.common.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Names<T extends Name> extends Duration {

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

    public Collection<T> values() {
	return _names.values();
    }

    final Collection<T> computeRelativeDurationOverall(long duration) {
	Set<T> sortedTs = new TreeSet<T>(compareByDuration());

	Collection<T> ts = values();
	for (T t : ts) {
	    sortedTs.add((T) t.computeRelativeDuration(duration));
	}
	return sortedTs;
    }

    private Comparator<T> compareByDuration() {
	return new Comparator<T>() {
	    @Override
	    public int compare(T thisT, T thatT) {
		long otherDuration = thatT.getRelativeDuration();
		long thisDuration = thisT.getRelativeDuration();
		return new Long(otherDuration)
			.compareTo(new Long(thisDuration));
	    }
	};
    }
}
