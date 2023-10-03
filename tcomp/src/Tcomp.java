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
        Double seuil = 0.0;
        String output = "";
        String path = "";
        if(args.length == 2){
            seuil = Double.parseDouble(args[1]);
            path = args[0];
        } else if(args.length == 4){
            seuil = Double.parseDouble(args[3]);
            path = args[2];
            output = args[1];
        }
        ArrayList<String> tropcomp = getTcomp(path, seuil);
       
        //Print to file
        if(output != ""){
            writeToCSV(output, tropcomp);
        }
        //Print to console
        else{
            for(int i = 0; i < tropcomp.size(); i++){
                System.out.println(tropcomp.get(i));
            }
        }
    }

    private static void writeToCSV(String path, ArrayList<String> data) throws IOException{
        FileWriter fileWriter = new FileWriter(path, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(int i = 0; i < data.size(); i++){
            bufferedWriter.write(data.get(i));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    private static ArrayList<String> getPercentile(ArrayList<String> list, Double threshold){
        int k = (int) Math.ceil(list.size() * threshold / 100.0);
        return new ArrayList(list.subList(0, Math.min(k, list.size())));
    }

    private static ArrayList<String> getTcomp(String path, Double threshold) throws IOException{
        try{
            ArrayList<String> tls_tloc = Tls.getTls(path);
            ArrayList<String> tls_tcmp = new ArrayList<String>();
            tls_tcmp = (ArrayList<String>)tls_tloc.clone();

            tls_tloc.sort((a, b) -> {
                return Integer.parseInt(b.split(", ")[3]) - Integer.parseInt(a.split(", ")[3]);
            });

            tls_tcmp.sort((a, b) -> {
                if(Float.parseFloat(a.split(", ")[5]) < Float.parseFloat(b.split(", ")[5])){
                    return 1;
                } else{
                    return -1;
                }
            });

            ArrayList<String> threshold_tls_tloc = getPercentile(tls_tloc, threshold);
            ArrayList<String> threshold_tls_tcomp = getPercentile(tls_tcmp, threshold);

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
