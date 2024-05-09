package shorturl.logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class ForbiddenWordsDictionary {
    private static final String FORBIDDEN_WORDS_FILE = "/forbidden_words.txt";
    private Set<String> forbiddenWords;

    public ForbiddenWordsDictionary() {
        forbiddenWords = new HashSet<>();
        loadForbiddenWords();
    }

    private void loadForbiddenWords() {
        try (InputStream inputStream = getClass().getResourceAsStream(FORBIDDEN_WORDS_FILE);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                forbiddenWords.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isForbidden(String word) {
        return forbiddenWords.contains(word);
    }
}
