package de.noxworks.noxnition;

import java.util.Iterator;
import java.util.List;

public class StringUtil {

	public static String join(List<Integer> list) {
		StringBuilder s = new StringBuilder();
		for (Iterator<Integer> i = list.iterator(); i.hasNext();) {
			s.append(i.next());
			if (i.hasNext())
				s.append(",");
		}
		return s.toString();
	}

}
