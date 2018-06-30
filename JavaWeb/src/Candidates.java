

import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Candidates {
    public static String key = "59e8fd0340332307cebb732080a3ddaf";
    public Stations stations;
    public List<CandidatePoint> candidatePoints;
    Candidates(String filename){
        stations = new Stations(filename);
    }

    private void getCarDistance(String dLoc){
        Point Pd = new Point(dLoc);
        getCarDistance(Pd);
    }
    private void getCarDistance(Point Pd){
        for(CandidatePoint c : candidatePoints){
            c.setCarDistanceAndTime(Pd);
        }
    }
    private void getWalkingCandidatePoints(String oLoc){
        Point Po = new Point(oLoc);
        candidatePoints= stations.findK(Po);
        if(candidatePoints.size() == 0){
            System.out.println("candidatePoints 是空的嗷嗷嗷嗷嗷嗷");
        }
    }
    private String getZ(String dLoc){
        Point Pd = new Point(dLoc);
        // 获取从candidates到pd的最近共同点Z
        Road[] can_Pd_roads = new Road[candidatePoints.size()];
        int i = 0;
        for(CandidatePoint c : candidatePoints){
            JSONObject jsonObj = c.getCarRoad(Pd);
            JSONArray jsonArr = (JSONArray)jsonObj.get("steps");
            can_Pd_roads[i++] = new Road(jsonArr);
        }
        // 从后往前一直删
        boolean finshed = false;
        String lastRoad = "";
        String lastPolyline = "";
        while (!finshed){
            String curr_road = "";
            String curr_polyline = "";
            for(Road r : can_Pd_roads){
                if(r.n <= 0){finshed = true; break;}
                if(curr_road.length() == 0){
                    curr_road = r.roads.get(r.n-1);
                    curr_polyline = r.polyline.get(r.n-1);
                    r.n = r.n - 1;
                }else{
                    if(curr_road.equals(r.roads.get(r.n - 1))){
                        r.n = r.n - 1;
                        continue;
                    }
                    else{
                        finshed = true;
                        break;
                    }
                }
            }
            lastRoad = curr_road;
            lastPolyline = curr_polyline;
        }
        if(lastPolyline.length() == 0){
            ArrayList<String> polyline = can_Pd_roads[can_Pd_roads.length - 1].polyline;
            lastPolyline = polyline.get(polyline.size() - 1);
        }
        return lastPolyline.split(";")[0];
    }
    public String getCandidate(String oLoc, String dLoc){
        getWalkingCandidatePoints(oLoc);
        String zLoc = getZ(dLoc);
        getCarDistance(zLoc);
        // sort
        Collections.sort(candidatePoints);
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for(CandidatePoint p : candidatePoints){
            if(counter > 3)break;
            sb.append(p.toString() + "_" + counter +";");
            ++counter;
        }
        return sb.toString();
    }
    public static void main(String[] args) {
	// write your code here

//        String filename = "E:\\实验室\\公交数据\\毕设代码\\centers_gd.txt";
//        Candidates in = new Candidates(filename);
//        String oLoc = "116.357825,39.962527";
//        String dLoc = "116.354699,39.941986";
//        String result = in.getCandidate(oLoc, dLoc);
//        System.out.println();


    }
}
