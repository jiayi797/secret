import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jiayi on 2018/6/4.
 */
public class Road {
    ArrayList<String> roads;
    ArrayList<String> polyline;
    int n;
    Road(JSONArray jsonArr){
        roads = new ArrayList<>();
        polyline = new ArrayList<>();
        for(int i = 0; i < jsonArr.length(); ++i){
            JSONObject temp = (JSONObject)jsonArr.get(i);
            try{
                if(roads.size() > 0 && temp.get("road").equals(roads.get(roads.size()-1))){
                    continue; // 去重
                }
                roads.add((String) temp.get("road"));
                polyline.add((String) temp.get("polyline"));
            }catch (Exception e){
                continue;
            }
        }
        n = roads.size();
    }
}
