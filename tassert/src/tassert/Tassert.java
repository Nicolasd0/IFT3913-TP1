package tassert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
        boolean inMultiLine = false;
        do{
            currentLine = reader.readLine();
            if(currentLine != null){
                //Line validation, make sure the line isn't commented
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
                
                //Check if line contains an assert statement
                if(!inMultiLine){
                    for(int i = 0; i < assertions.size(); i++){
                        if(currentLine.startsWith(assertions.get(i)))
                            tassert++;
                    }
                }   
            }
        } while(currentLine != null);

        reader.close();
        return tassert;
    }
}
