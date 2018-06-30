/**
 * Created by jiayi on 2018/6/20.
 */
public class Order {
    String carId;
    Point Po;
    Point Pd;
    float timeDelta;
    Order(String line){
        String[] elements = line.split(",");
        carId = elements[0];
        Po = new Point(Double.valueOf(elements[1])/100000,Double.valueOf(elements[2])/100000);
        Pd = new Point(Double.valueOf(elements[5])/100000,Double.valueOf(elements[6])/100000);
        String timeStr = elements[7].split(" ")[2];
        String[] timeStrs = timeStr.split(":");
        timeDelta = (float) (Integer.valueOf(timeStrs[1]) + Float.valueOf(timeStrs[2])/60.0);
    }

    public String toString(){
        String result = carId + ",";
        result = result + Po.toString() + "," + Pd.toString() + "," + timeDelta;
        return result;
    }

    public void setPoAsPOI(POI[] POIs){
        double minDist = Double.MAX_VALUE;
        Stations helper = new Stations(true);
        Point Po_new = null;
        for(POI poI : POIs){
            double currDist = helper.getDistance(poI.p, Po);
            if(currDist < minDist){
                Po_new = poI.p;
                minDist = currDist;
            }
        }

    }


}
