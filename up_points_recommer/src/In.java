import java.util.List;

public class In {
    Stations stations;
    In(String filename){
        stations = new Stations(filename);
    }
    String getBest(String oLoc, String dLoc){
        Point p = new Point(oLoc);
        List<CandidatePoint> candidates = stations.findK(p);
        System.out.print("a");
        return "";
    }
    public static void main(String[] args) {
	// write your code here
        String filename = "E:\\实验室\\公交数据\\毕设代码\\centers_gd.txt";
        new In(filename).getBest("116.357825,39.962527","116.357825,39.962527");

    }
}
