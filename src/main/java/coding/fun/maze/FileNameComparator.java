package coding.fun.maze;

import java.io.File;
import java.util.Comparator;

public class FileNameComparator implements Comparator<File> {

	@Override
	public int compare(File file1, File file2) {
		if (file1 == null || file2 == null) {
			throw new IllegalArgumentException("The objects compared should be of type File");
		}

		return file1.getName().compareTo(file2.getName());
	}

}
