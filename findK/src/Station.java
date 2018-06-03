import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jiayi on 2018/4/18.
 */
public class Station{
    double lat;
    double lng;
    boolean translated = false;
    Station(String lnglatStr){
        String[] lnglat = lnglatStr.split(",");
        this.lng = Double.valueOf(lnglat[0]);
        this.lat = Double.valueOf(lnglat[1]);
        translated = true;
    }
    Station(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
        //translate2Gaode();
    }
    public void translate2Gaode(){
        try {
            String loc = String.valueOf(lng) + "," + String.valueOf(lat);
            URL yahoo = new URL("http://restapi.amap.com/v3/assistant/coordinate/convert?" +
                    "locations="+loc+"" +
                    "&coordsys=gps" +
                    "&output=JSON" +
                    "&key=2a19f25ef9117a9c5b32c96b6f57dedd");
            URLConnection yc = yahoo.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String respond = in.readLine();
            String element = respond.split(":")[4];
            element = element.split("}")[0];
            element = element.split("\"")[1];
            String[] lnglat = element.split(",");
            this.lng = Double.valueOf(lnglat[0]);
            this.lat = Double.valueOf(lnglat[1]);
            in.close();
            translated = true;
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
