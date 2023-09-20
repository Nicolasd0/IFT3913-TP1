package tloc;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        boolean inMultiLine = false;
        do{
            currentLine = reader.readLine();
            if(currentLine != null){
                currentLine = currentLine.trim();
                //Empty or single line comment
                if(currentLine.isEmpty() || currentLine.startsWith("//")){
                    continue;
                } 
                //Start of comment
                if(currentLine.startsWith("/*")){
                    inMultiLine = true;
                    continue;
                }
                //End of comment
                if(currentLine.endsWith("*/")){
                    inMultiLine = false;
                    continue;
                }
                
                if(!inMultiLine){
                    tloc++;
                }   
            }
        } while(currentLine != null);

        reader.close();
        return tloc;
    }
}
