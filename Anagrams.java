import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Anagrams {
    Map<String, String> dict = new HashMap<>();

    private static String getKey(String w) {
        char[] charArr = w.toLowerCase().toCharArray();
        Arrays.sort(charArr);
        return new String(charArr);
    }

    public Anagrams(Path p) throws IOException {
        this(Files.newBufferedReader(p).lines());
    }

    public Anagrams(Stream<String> lines) {
        Map<String, List<String>> map = new HashMap<>();
        lines.forEach((String word) -> {
            String key = getKey(word);

            List<String> lst = map.get(key);
            if (lst == null) {
                lst = new ArrayList<>();
                map.put(key, lst);
            }
            lst.add(word);
        });

        // transform each list value to the sorted joined string
        for (Iterator<Map.Entry<String, List<String>>> iter = map.entrySet().iterator();
             iter.hasNext();) {
            Map.Entry<String, List<String>> entry = iter.next();
            String ana = entry.getValue()
                    .stream()
                    .sorted()
                    .collect(Collectors.joining(" "));
            dict.put(entry.getKey(), ana);
            iter.remove(); // remove so that we don't use too much memory }
        }
    }

    public String getAnagrams(String word, String defaultValue) {
        return dict.getOrDefault(getKey(word), defaultValue);
    }

    public static void main(String[] args) throws IOException {
        //testCase0();
        //testCase1();
        // offline: preprocess
        String fileStr = args[0];
        Anagrams A = new Anagrams(Paths.get(fileStr));

        // online: read input
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String w = scan.nextLine();
            if (w.isEmpty()) {
                break;
            }
            String anagrams = A.getAnagrams(w, "-");
            System.out.println(anagrams);
        }
    }

    public static void testCase0() {
        System.out.println("Beginning testing case 0 - test basic functionality on small input");
        List<String> input0 = Arrays.asList("ab", "ba", "ccCc", "ccdc", "d", "", "dccc");
        Anagrams a0 = new Anagrams(input0.stream());
        assert a0.getAnagrams("ab", "-").equals("ab ba");
        assert a0.getAnagrams("cccd", "-").equals("ccdc dccc");
        assert a0.getAnagrams("d", "-").equals("d");
        assert a0.getAnagrams("opentable ", "-").equals("-");
        assert a0.getAnagrams("opentable ", ".").equals(".");
    }

    public static void testCase1() throws IOException {
        System.out.println("Beginning testing case 0 - test big inputs of 466k words");
        String file = "dict_alpha.txt";
        Anagrams a1 = new Anagrams(Paths.get(file));
        assert a1.getAnagrams("apple", "-").equals("appel apple pepla");
        assert a1.getAnagrams("open", "-").equals("nope open peon pone");
        assert a1.getAnagrams("pager", "-").equals("gaper grape pager parge");
        assert a1.getAnagrams("nonexistentword", "-").equals("-");
    }
}
