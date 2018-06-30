import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

/**
 * Created by jiayi on 2018/6/18.
 */
public class FetchPOI {
    private String getPOI(String loc){
        String url = "http://restapi.amap.com/v3/geocode/regeo?";
        url += ("key=2a19f25ef9117a9c5b32c96b6f57dedd"+
                "&extensions=all"+
                "&batch=true"+
                "&location="+loc

        );//
        JSONObject jsonObj = new JSONObject(HttpClient.doGet(url));
        JSONArray jsonArray = (JSONArray) jsonObj.get("regeocodes");
        StringBuilder result = new StringBuilder();
        String[] locs = loc.split("\\|");
        int i = 0;
        for(Object ob : jsonArray){
            String name = (String)((JSONObject) ob).get("formatted_address")+ ';';
            JSONArray poiArr = (JSONArray) ((JSONObject) ob).get("pois");
            JSONObject poi0 = (JSONObject) poiArr.get(0);
            name = name  + poi0.get("name") + ";";
            name = name + poi0.get("address") + ';'; // formatted_address, name, address
            String poiLoc = (String) poi0.get("location");
            result.append(name + "\t" + poiLoc + "\n");//ocs[i] + "\t" +

        }
        return result.toString();
    }
    private void read(String filename){
        File file = new File(filename);
        try{
            // count lines
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder POIs = new StringBuilder();
            // read data
            reader = new InputStreamReader(new FileInputStream(file),"UTF-8");
            bufferedReader = new BufferedReader(reader);
            String loc;
            StringBuilder sb = new StringBuilder();
            int i = 1;
            bufferedReader.readLine(); // header
            while((loc = bufferedReader.readLine()) != null){
                sb.append(loc);
                if(i % 19 == 0){
                    POIs.append(getPOI(sb.toString()+"\n"));
                    sb = new StringBuilder();
                    System.out.println(i);
                    //break;
                }
                if(sb.length() > 0)sb.append("|");
                i += 1;
            }

            reader.close();
            bufferedReader.close();
            // write
            BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\实验室\\公交数据\\毕设代码\\centers_POI_gd.txt"));
            writer.write(POIs.toString());
            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        new FetchPOI().read( "E:\\实验室\\公交数据\\毕设代码\\centers_gd.txt");
    }
}
