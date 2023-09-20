
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import tassert.Tassert;
import tloc.Tloc;

public class Tls {
    public static void main(String[] args) throws Exception {
        if(args.length < 1){
            throw new Error("Missing file path argument");
        }
        ArrayList<String> csv = getTls(args[0]);
       
        //Print to file
        if(args.length > 1){
            writeToCSV(args[1], csv);
        }
        //Print to console
        else{
            for(int i = 0; i < csv.size(); i++){
                System.out.println(csv.get(i));
            }
        }
    }

    public static ArrayList<String> getTls(String path) throws IOException{
        //Source: https://stackoverflow.com/questions/2056221/recursively-list-files-in-java
        //Finds all files in folder recursively
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.DOWN);
        ArrayList<String> files = new ArrayList<String>();
        try (Stream<Path> stream = Files.walk(Paths.get(path))) {
            stream.filter(Files::isRegularFile)
                .forEach((file) -> files.add(file.toString()));
        }
        ArrayList<String> csv = new ArrayList<String>();
        for(int i = 0; i < files.size(); i++){
            String file_path = files.get(i);
            //Check for a test file
            if(file_path.toLowerCase().contains("test.java")){

                int tloc = Tloc.getTloc(file_path);
                int tassert = Tassert.getTassert(file_path);

                String package_name = getPackageName(file_path);
                String class_name = getClassName(file_path);
                Double tcomp;
                if(tassert == 0){
                    tcomp = 0.0;
                } else{
                    tcomp = Double.valueOf(tloc) / Double.valueOf(tassert);
                }
                csv.add(String.format("%s, %s, %s, %s, %s, %s", file_path, package_name, class_name, tloc, tassert, df.format(tcomp)));
            }
           
        }

        return csv;
    }

    private static void writeToCSV(String path, ArrayList<String> data) throws IOException{
        FileWriter fileWriter = new FileWriter(path);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(int i = 0; i < data.size(); i++){
            bufferedWriter.write(data.get(i));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    private static String getPackageName(String path)throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String currentLine;
        
        do{
            currentLine = reader.readLine();
            if(currentLine != null){
                currentLine = currentLine.trim();
                if(currentLine.startsWith("package ")){
                    reader.close();
                    return currentLine.split(" ")[1];
                }
            }
            
        } while(currentLine != null);

        reader.close();
        return "null";     
    
    }

    private static String getClassName(String path)throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String currentLine;
        
        do{
            currentLine = reader.readLine();
            if(currentLine != null){
                currentLine = currentLine.trim();
                if(currentLine.startsWith("public class ")){
                    reader.close();
                    return currentLine.split(" ")[2];
                }
            }
          
        } while(currentLine != null);

        reader.close();
        return "null";   
    }
}
