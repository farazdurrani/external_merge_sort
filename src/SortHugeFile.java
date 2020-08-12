import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortHugeFile {

    private static final String DOUBLE_QUOTES = "\"";
    private static final String COMMA = ",";
    private static final String EMPTY_STRING = "";

    public static void main(String[] args) throws IOException {
	// create temp directory
	File tempDir = new File(System.getProperty("user.home") + "/tempdir");
	tempDir.mkdir();

	Reader reader = new BufferedReader(
	    new FileReader("/home/faraz/Desktop/manynames.txt"));

	int theCharNum = reader.read();
	String word = EMPTY_STRING;
	List<String> words = new ArrayList<>();
	boolean alreadyChecked = false;
	int slices = 0;
	int numFiles = 0;
	while (theCharNum != -1) {
	    char theChar = (char) theCharNum;
	    word = word + theChar;
	    if (!DOUBLE_QUOTES.equals(String.valueOf(theChar))) {
		if (COMMA.equals(String.valueOf(theChar))) {
		    words.add(word.replace(COMMA, EMPTY_STRING).replace(DOUBLE_QUOTES,
		        EMPTY_STRING));
		    word = EMPTY_STRING;
		    alreadyChecked = false;
		}
	    }

	    
	    if (words.size() % 100_000_0 == 0 && words.size() != 0 && !alreadyChecked) { // 100
	                                                                                 // million
	                                                                                 // =
	                                                                                 // 100_000_000
		System.out.println("Slice Number: " + slices);
		alreadyChecked = true;
		Collections.sort(words);
		Files.write(Paths.get(tempDir.getAbsolutePath() + "/sorted_sub_file_" + ++slices), words,
		    StandardOpenOption.CREATE);
		words.clear();
		numFiles++;
	    }
	    
	    if(numFiles == 100) {
		break;
	    }

	    theCharNum = reader.read();
	}

	reader.close();

	System.out.println("Final File size: " + words.size());
	System.out.println("Final Slice number: " + slices);
	Collections.sort(words);
	Files.write(Paths.get(tempDir.getAbsolutePath() + ++slices), words,
	    StandardOpenOption.CREATE);

//	words.forEach(System.out::println);
	
	
    }
}
