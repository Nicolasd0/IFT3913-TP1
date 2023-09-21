import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import tls.Tls;
public class Tcomp {
    public static void main(String[] args) throws Exception {
        if(args.length < 2){
            throw new Error("Missing file path argument");
        }
        ArrayList<String> tropcomp = getTcomp(args[1], Float.parseFloat(args[0]));
       
        //Print to file
        if(args.length > 2){
            writeToCSV(args[2], tropcomp);
        }
        //Print to console
        else{
            for(int i = 0; i < tropcomp.size(); i++){
                System.out.println(tropcomp.get(i));
            }
        }
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

    private static ArrayList<String> getPercentile(ArrayList<String> list, Float threshold){
        ArrayList<String> target = new ArrayList<String>();
        for(int i = 0; i < list.size() * threshold / 100.0; i++){
            target.add(list.get(i));
        }
        return target;
    }

    private static ArrayList<String> getTcomp(String path, Float threshold) throws IOException{
        try{
            ArrayList<String> tls_tloc = Tls.getTls(path);
            System.out.println(tls_tloc.size());
            ArrayList<String> tls_tcomp = new ArrayList<String>();
            tls_tcomp = (ArrayList<String>)tls_tloc.clone();

            tls_tloc.sort((a, b) -> {
                return Integer.parseInt(b.split(", ")[3]) - Integer.parseInt(a.split(", ")[3]);
            });

            tls_tcomp.sort((a, b) -> {
                if(Float.parseFloat(a.split(", ")[5]) < Float.parseFloat(b.split(", ")[5])){
                    return 1;
                } else{
                    return -1;
                }
            });

            ArrayList<String> threshold_tls_tloc = getPercentile(tls_tloc, threshold);
            ArrayList<String> threshold_tls_tcomp = getPercentile(tls_tcomp, threshold);
            ArrayList<String> tropcomp = new ArrayList<String>();

            for(int i = 0; i < threshold_tls_tloc.size(); i++){
                for(int j = 0; j < threshold_tls_tcomp.size(); j++){
                    String tloc_path = threshold_tls_tloc.get(i).split(", ")[0];
                    String tcomp_path = threshold_tls_tcomp.get(j).split(", ")[0];

                    if(tloc_path.equals(tcomp_path))
                        tropcomp.add(threshold_tls_tloc.get(i));
                }
            }

            return tropcomp;
        } catch(IOException e){
            throw e;
        }
       
    }
}
