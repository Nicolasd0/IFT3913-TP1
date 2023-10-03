package tloc;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tloc {
    public static void main(String[] args) throws Exception {
        if(args.length < 1){
            throw new Error("Missing file path argument");
        }
        System.out.println(getTloc(args[0]));
    }

    public static int getTloc(String path) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String currentLine;
        int tloc = 0;
        String data = "";
        //Fetch all the file's content as a big string
        do{
            currentLine = reader.readLine();
            if(currentLine != null){
                currentLine = currentLine.trim();
                data += currentLine + "\n";
            }
        } while(currentLine != null);

        //Remove all the content between comments
        data = removeContentBetweenDelimiters(data, "/*", "*/");
        data = removeContentBetweenDelimiters(data, "//", "\n");

        String[] lines = data.split("\n");

        //Count every line that isn't empty
        for(int i = 0; i < lines.length; i++){
            if(lines[i].trim().length() != 0){
                tloc++;
            }
        }

        reader.close();
        return tloc;
    }

    public static String removeContentBetweenDelimiters(String input, String startDelimiter, String endDelimiter) {
        //Pattern matches the given delimiters
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
}
