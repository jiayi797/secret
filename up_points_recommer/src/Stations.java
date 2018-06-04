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
            if(getDistance(p,o) <= 1000){
                candidates0.add(p);
            }
        }
        double min = 500; // m
        List<CandidatePoint> candidates = new ArrayList<>();
        // pick points who's walking distance is smaller than min meters
        CandidatePoint o1 = new CandidatePoint(o.longitude, o.latitude, 0);
        for(Point p : candidates0){
            double walkingDist = o1.getWalkingDistance(p);
            if(walkingDist <= min){
                candidates.add(new CandidatePoint(p.longitude, p.latitude, walkingDist));
            }
        }
        return candidates;
    }

    private final double EARTH_RADIUS = 6371393; // m
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
        return s * EARTH_RADIUS;

    }

}
