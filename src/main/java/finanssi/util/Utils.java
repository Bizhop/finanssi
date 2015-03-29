package finanssi.util;

import java.util.List;

import finanssi.model.Square;

public class Utils {
	public static Square findByIndex(List<Square> grid, final Integer index) {
		return grid.stream()
			.filter(o -> o.getIndex() == index)
			.findFirst()
			.get();
	}
}
