import java.io.*;
import java.util.*;

public class BookFileManager {
    private static final String BOOKS_DIRECTORY = "src/%s.txt";

    public void processBook(String fileName) {
        File file = new File(String.format(BOOKS_DIRECTORY, fileName));
        if (!file.exists()) {
            System.out.println("No such book found!");
            return;
        }

        Map<String, Integer> wordCount = new HashMap<>();
        Set<String> uniqueWords = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.lines()
                    .map(line -> line.split("\\W+"))
                    .flatMap(Arrays::stream)
                    .filter(word -> word.length() > 2)
                    .map(String::toLowerCase)
                    .forEach(word -> {
                        wordCount.merge(word, 1, Integer::sum);
                        uniqueWords.add(word);
                    });

            List<Map.Entry<String, Integer>> sortedWordCount = wordCount.entrySet().stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .limit(10)
                    .toList();

            String statisticsFileName = String.format(BOOKS_DIRECTORY, fileName.replace(".txt", "") + "_statistic.txt");
            try (PrintWriter writer = new PrintWriter(new FileWriter(statisticsFileName))) {
                for (Map.Entry<String, Integer> entry : sortedWordCount) {
                    writer.println(entry.getKey() + " -> " + entry.getValue());
                }
                writer.println("Total unique words: " + uniqueWords.size());
            }

            System.out.println("Statistics:");
            for (Map.Entry<String, Integer> entry : sortedWordCount) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
            System.out.println("Total unique words: " + uniqueWords.size());
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file " + fileName);
            e.printStackTrace();
        }
    }

}
