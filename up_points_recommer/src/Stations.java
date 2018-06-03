/**
 * Created by jiayi on 2018/6/3.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

public class Stations {
    private Point[] points;

    Stations(String filename){
        File file = new File(filename);
        try{
            // count lines
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            bufferedReader.readLine(); // header
            int count = 0;
            while(bufferedReader.readLine() != null){
                ++count;
            }
            reader.close();
            bufferedReader.close();
            points = new Point[count];
            // read data
            reader = new InputStreamReader(new FileInputStream(file),"UTF-8");
            bufferedReader = new BufferedReader(reader);
            String line;
            int i = 0;
            bufferedReader.readLine(); // header
            while((line = bufferedReader.readLine()) != null){
                //System.out.println(line);
                String[] loc = line.split(",");
                points[i++] = new Point(Double.valueOf(loc[0]), Double.valueOf(loc[1]));
            }
            reader.close();
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<CandidatePoint> findK(Point o){
        List<Point> candidates0 = new ArrayList<>();
        // pick points who's Euclidean distance is smaller than 1km
        for(Point p : points){
            if(getDistance(p,o) <= 1){
                candidates0.add(p);
            }
        }
        double min = 500; // m
        List<CandidatePoint> candidates = new ArrayList<>();
        // pick points who's walking distance is smaller than min meters
        for(Point p : candidates0){
            double walkingDist = getWalkingDistance(p,o);
            if(walkingDist <= min){
                candidates.add(new CandidatePoint(p.latitude, p.longitude, walkingDist));
            }
        }
        return candidates;
    }
    private double getWalkingDistance(Point a, Point b){
        String url = "http://restapi.amap.com/v3/direction/walking?";
        url += ("key=2a19f25ef9117a9c5b32c96b6f57dedd"+
                "&origin="+a.toString()+
                "&destination="+b.toString()
        );
        JSONObject jsonObj = new JSONObject(HttpClient.doGet(url));
        String dist = (String)(((JSONObject)((JSONArray)(((JSONObject)jsonObj.get("route")).get("paths"))).get(0)).get("distance"));
        return Double.valueOf(dist);
    }
    private final double EARTH_RADIUS = 6371.393;
    private double rad(double d)
    {
        return d * Math.PI / 180.0;
    }
    private double getDistance(Point p1, Point p2){
        double radLat1 = rad(p1.latitude);
        double radLat2 = rad(p2.latitude);
        double a = radLat1 - radLat2;
        double b = rad(p1.longitude) - rad(p2.longitude);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        //s = Math.round(s);
        return s;

    }

}
