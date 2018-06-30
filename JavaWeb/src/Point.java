import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by jiayi on 2018/6/3.
 */
public class Point{
    double latitude; // 纬度
    double longitude; // 经度
    public String toString(){
        return String.valueOf(longitude) + "," + String.valueOf(latitude);
    }
    Point(double longitude,double latitude){
        if(longitude < 115 || longitude > 118 || latitude < 35 || latitude > 41){
            System.out.println("经纬度不对"+longitude+','+latitude);
            return;
        }
        this.latitude = latitude;
        this.longitude = longitude;
    }
    Point(String loc){
        this(Double.valueOf(loc.split(",")[0]), Double.valueOf(loc.split(",")[1]));
    }

}