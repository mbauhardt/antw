package asls.model;

public class Name extends Counter {

	private String _name;

	public Name() {
	}

	public Name(String name) {
		_name = name;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Name other = (Name) obj;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		return true;
	}

}
