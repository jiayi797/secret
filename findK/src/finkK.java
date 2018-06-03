import java.io.InputStreamReader;
import java.io.*;
public class finkK {

    Stations stations = new Stations(true);
    public void reader(){
        try {
            InputStream is = new FileInputStream("E:\\实验室\\公交数据\\毕设代码\\centers.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while(str != null)  {
                str = reader.readLine();
                stations.add(str);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    finkK(){
        reader();
    }
    private double dist(Double lng1, Double lat1, Double lng2, Double lat2){
        Station a = new Station(lng1, lat1);
        Station b = new Station(lng2, lat2);
        return dist(a,b);
    }
    private double dist(Station a, Station b){
        Station aMinDist = stations.minDistStation(a);
        Station bMinDist = stations.minDistStation(b);
        return stations.streetDist(aMinDist, bMinDist);
    }
    public static void main(String[] args){
        Station xiMen = new Station("116.355018,39.961683");
        Station zhengFaDongMen = new Station("116.353098,39.965038");
        finkK f = new finkK();
        System.out.println(f.dist(xiMen, zhengFaDongMen));
    }


}
