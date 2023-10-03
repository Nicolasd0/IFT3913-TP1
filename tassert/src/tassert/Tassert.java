package tassert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tassert {
    public static void main(String[] args) throws Exception {
        if(args.length < 1){
            throw new Error("Missing file path argument");
        }
        System.out.println(getTassert(args[0]));
    }

    public static int getTassert(String path) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        List<String> assertions = Arrays.asList( 
       "assertArrayEquals",
            "assertEquals",
            "assertFalse",
            "assertNotEquals",
            "assertNotNull",
            "assertNotSame",
            "assertNull",
            "assertSame",
            "assertThat",
            "assertThrows",
            "assertTrue",
            "fail"
        );
        String currentLine;
        int tassert = 0;
        String data = "";
        do{
            currentLine = reader.readLine();
            if(currentLine != null){
                currentLine = currentLine.trim();
                data += currentLine + "\n";
            }
        } while(currentLine != null);

        reader.close();

        data = removeContentBetweenDelimiters(data, "/*", "*/");
        data = removeContentBetweenDelimiters(data, "//", "\n");

        for(int i = 0; i < assertions.size(); i++){
            tassert += countOccurrencesRegex(data, assertions.get(i));
        }
        
        return tassert;

    }

    private static String removeContentBetweenDelimiters(String input, String startDelimiter, String endDelimiter) {
        String patternString = Pattern.quote(startDelimiter) + ".*?" + Pattern.quote(endDelimiter);
        Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
           // Preserve line breaks if they existed in the original input
           String replacement = matcher.group().replaceAll("[^\n]", "");
           matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);

        return result.toString();
    }

    private static int countOccurrencesRegex(String input, String target) {
        Pattern pattern = Pattern.compile(Pattern.quote(target));
        Matcher matcher = pattern.matcher(input);

        int count = 0;
        while (matcher.find()) {
            count++;
        }

        return count;
    }
}
