import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MergeSortFiles {
    public static void main(String[] args) throws IOException {
	String[] paths = { "/home/faraz/tempdir2/sorted_sub_file_1",
	    "/home/faraz/tempdir2/sorted_sub_file_2",
	    "/home/faraz/tempdir2/sorted_sub_file_3" };
	BufferedWriter bw = new BufferedWriter(
	    new FileWriter("/home/faraz/tempdir2/sorted.txt"));
	int writeBuffer = 0;
	int totalLines = 0;

	BufferedReader[] bf_arr = new BufferedReader[paths.length];
	for (int i = 0; i < paths.length; i++) {
	    bf_arr[i] = new BufferedReader(new FileReader(paths[i]));
	}

	for (int i = 0; i < paths.length; i++) {
	    BufferedReader br = bf_arr[i];
	    String currLine = br.readLine();
	    while (Objects.nonNull(currLine) && !currLine.isBlank()) {
		totalLines++;
		currLine = br.readLine();
	    }
	}

	System.out.println("Total Lines..." + totalLines);

	for (int i = 0; i < paths.length; i++) {
	    bf_arr[i].close();
	}

	Map<Integer, Tracker> trackers = new LinkedHashMap<>();

	String currLine;
	for (int j = 0; j < totalLines; j++) {
	    for (int i = 0; i < paths.length; i++) {
		BufferedReader br = new BufferedReader(new FileReader(paths[i]));
		while ((currLine = br.readLine()) != null) {
		    if (trackers.containsKey(i)) {
			Tracker tracker = trackers.get(i);

			if (!currLine.equals(tracker.getLine())) {
			    continue;
			} else {
			    if (tracker.isSkip()) {
				currLine = br.readLine();
				tracker.setSkip(false);
				tracker.setLine(currLine);
			    }
			}

			System.out.println(currLine);
			trackers.put(i, new Tracker(currLine, false));

		    } else {
			System.out.println(currLine);
			Tracker tracker = new Tracker(currLine, false);
			trackers.put(i, tracker);
		    }
		    break;
		}
		br.close();

	    }
	    List<String> lines = new ArrayList<>();
	    trackers.values().forEach(tracker -> {
		if (null != tracker.getLine()) {
		    lines.add(tracker.getLine());
		}
	    });
	    String min = null;
	    lines.removeIf(String::isBlank);
	    lines.removeIf(Objects::isNull);
	    if (!lines.isEmpty()) {
		min = Collections.min(lines);
	    }

	    System.out.println("Min = " + min);

	    for (Integer i : trackers.keySet()) {
		Tracker tracker = trackers.get(i);
		if (null != tracker.getLine() && tracker.getLine().equals(min)) {
		    Tracker new_tracker = new Tracker(tracker.getLine(), true);
		    trackers.put(i, new_tracker);
		    break;
		}
	    }

	    bw.write(min);
	    bw.newLine();
	    writeBuffer++;

	    if (writeBuffer % 5 == 0) {
		bw.flush();
		bw.close();
		bw = new BufferedWriter(
		    new FileWriter("/home/faraz/tempdir2/sorted.txt", true));
	    }
	}

	bw.flush();
	bw.close();
    }
}
