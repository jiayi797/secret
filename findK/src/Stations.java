
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by jiayi on 2018/4/17.
 */
public class Stations {
    ArrayList<Station> stations;
    Stations(boolean tester){
        stations = new ArrayList<>();
        this.tester = tester;
    }
    private double latMax = 39.986912;
    private double latMin = 39.943758;
    private double lngMax = 116.371726; // 116.371726
    private double lngMin = 116.327437; //116.327437
    boolean tester = false;
    public void add(String s){
        try {
            String[] element = s.split(",");
            double lat = Integer.valueOf(element[1])/100000.0;
            double lng = Integer.valueOf(element[2])/100000.0;
            if(tester && (lat > latMax || lat < latMin || lng > lngMax || lng < lngMin)){
                return;
            }
            Station station = new Station(lat, lng);
            // 这里应该转化一下，我嫌慢
            station.translate2Gaode();
            System.out.println(station.lat + "," + station.lng);
            stations.add(station);
        }catch (Exception e){
            return;
        }

    }

    public double streetDist(Station o, Station d){
        return catchStreetDistFromGaoDe(o,d);

    }
    private int catchStreetDistFromGaoDe(Station oStation, Station dStation){
        try {
            if(!oStation.translated)oStation.translate2Gaode();
            if(!dStation.translated) dStation.translate2Gaode();
            String oLocStr = String.valueOf(oStation.lng) + "," + String.valueOf(oStation.lat);
            String dLocStr = String.valueOf(dStation.lng) + "," + String.valueOf(dStation.lat);
            URL yahoo = new URL("http://restapi.amap.com/v3/distance?" +
                    "origins=" + oLocStr +
                    "&destination=" + dLocStr +
                    "&type=1" +
                    "&output=JSON" +
                    "&key=2a19f25ef9117a9c5b32c96b6f57dedd");
            URLConnection yc = yahoo.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String respond = in.readLine();
            String distStr = respond.split(",")[5];
            return Integer.valueOf(distStr.split("\"")[3]);
        }catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }
    public Station minDistStation(Station o){
        double min = Double.MAX_VALUE;
        Station result = null;
        for(Station station : stations){
            Double currDist = dist(station, o);
            if(min > currDist){
                min = currDist;
                result = station;
            }
        }
        return result;
    }
    private double dist(Station o, Station d){
        // 暂时用欧式距离，应该转化一下的
        return Math.sqrt((o.lat - d.lat)*(o.lat - d.lat) +  (o.lng - d.lng)*(o.lng - d.lng));
    }
}
