package de.noxworks.noxnition;

import java.util.Comparator;

public class NamedElementComparator implements Comparator<INamedElement> {

	private static final NamedElementComparator instance = new NamedElementComparator();

	@Override
	public int compare(INamedElement lhs, INamedElement rhs) {
		return lhs.getName().compareTo(rhs.getName());
	}

	public static NamedElementComparator getInstance() {
		return instance;
	}
}