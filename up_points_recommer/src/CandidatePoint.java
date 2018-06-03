/**
 * Created by jiayi on 2018/6/3.
 */
public class CandidatePoint extends Point{
    double v_walk = 110; // m/min
    CandidatePoint(double longitude,double latitude, double walkingDistance){
        super(latitude, longitude);
        this.walkingDistance = walkingDistance;
        this.walkingTimeToOrigin = walkingDistance/v_walk;
    }
    private double walkingDistance;
    private double walkingTimeToOrigin;
    private double CarTimeToDestination;


}
