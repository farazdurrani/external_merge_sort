import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNames {

    private static final String DOUBLE_QUOTES = "\"";
    private static final String COMMA = ",";

    public static void main(String[] args) throws IOException {
	List<String> randomStrings = new ArrayList<>();
	for (int i = 0; i < Integer.MAX_VALUE; i++) {
	    randomStrings.add(generateRandomString());
	    if (i % 100_000_00 == 0 && i != 0) {
		StringBuilder sb = new StringBuilder();
		randomStrings.forEach(sb::append);
		System.out.println("Writing to file .. i = " + i);
		Files.write(Paths.get("/home/faraz/Desktop/manynames.txt"),
		    sb.toString().getBytes(), StandardOpenOption.APPEND);
		randomStrings.clear();
	    }
	}

	System.out.println("Done");
    }

    public static String generateRandomString() {
	int leftLimit = 97; // letter 'a'
	int rightLimit = 122; // letter 'z'
	int targetStringLength = 10;
	Random random = new Random();

	String generatedString = random.ints(leftLimit, rightLimit + 1)
	    .limit(targetStringLength)
	    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	    .toString();

	return DOUBLE_QUOTES + generatedString + DOUBLE_QUOTES + COMMA;
    }
}
