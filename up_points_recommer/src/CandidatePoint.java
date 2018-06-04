import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Comparator;

/**
 * Created by jiayi on 2018/6/3.
 */
public class CandidatePoint extends Point implements Comparable<CandidatePoint>{
    static double v_walk = 1.83; // m/s
    CandidatePoint(double longitude,double latitude, double walkingDistance){
        super(longitude, latitude);
        this.walkingDistance = walkingDistance;
        this.walkingTimeToOrigin = walkingDistance/v_walk;
    }
    private double walkingDistance = -1;
    private double walkingTimeToOrigin = -1;
    private double CarDistanceToDestination = -1;
    private double CarTimeToDestination = -1;
    private double  totalTimeCost = -1;

    public double getWalkingDistance( Point b){
        String url = "http://restapi.amap.com/v3/direction/walking?";
        url += ("key=2a19f25ef9117a9c5b32c96b6f57dedd"+
                "&origin="+this.toString()+
                "&destination="+b.toString()
        );
        JSONObject jsonObj = new JSONObject(HttpClient.doGet(url));
        String dist = (String)(((JSONObject)((JSONArray)(((JSONObject)jsonObj.get("route")).get("paths"))).get(0)).get("distance"));
        return Double.valueOf(dist);
    }
    public JSONObject getCarRoad(Point b){
        String url = "http://restapi.amap.com/v3/direction/driving?";
        url += ("key=2a19f25ef9117a9c5b32c96b6f57dedd"+
                "&origin="+this.toString()+
                "&destination="+b.toString()+
                "&strategy=0");
        JSONObject jsonObj = new JSONObject(HttpClient.doGet(url));
        jsonObj = ((JSONObject)((JSONArray)((JSONObject)jsonObj.get("route")).get("paths")).get(0));
        return jsonObj;
    }
    public void setCarDistanceAndTime(Point b){
        JSONObject jsonObj = getCarRoad(b);
        String time =  (String)(jsonObj.get("duration")); //duration
        String distance = (String)(jsonObj.get("distance"));
        CarDistanceToDestination = Double.valueOf(distance);
        CarTimeToDestination = Double.valueOf(time);
        totalTimeCost = CarTimeToDestination + walkingTimeToOrigin;
        //return new double[]{CarDistanceToDestination, CarTimeToDestination};
    }

    @Override
    public int compareTo(CandidatePoint o) {
        if(walkingDistance == -1 || walkingTimeToOrigin == -1 || CarTimeToDestination == -1 || CarDistanceToDestination == -1){
            System.out.println("距离错误");
            return 0;
        }
        return Double.compare(this.totalTimeCost, o.totalTimeCost);
    }
}
