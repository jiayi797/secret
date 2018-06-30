/**
 * Created by jiayi on 2018/6/20.
 */
public class POI {
    Point p;
    String name;
    POI(String line){
        String[] info = line.split("\t");
        String[] loc = info[1].split(",");
        p = new Point(Double.valueOf(loc[0]), Double.valueOf(loc[1]));
        name = info[0];
    }
}
