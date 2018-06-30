import java.io.*;

/**
 * Created by jiayi on 2018/6/18.
 */
public class Main {
    POI[] POIs;
    Candidates candidates;
    Order[] orders;
    private void set_pois(String filename){
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
            POIs = new POI[count];
            // read data
            reader = new InputStreamReader(new FileInputStream(file),"UTF-8");
            bufferedReader = new BufferedReader(reader);
            String line;
            int i = 0;
            bufferedReader.readLine(); // header
            while((line = bufferedReader.readLine()) != null){
                POIs[i++] = new POI(line);
            }
            reader.close();
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void get_orders(){
        String filename = "E:\\实验室\\公交数据\\毕设代码\\20160901_path.csv";
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
            orders = new Order[count];
            // read data
            reader = new InputStreamReader(new FileInputStream(file),"UTF-8");
            bufferedReader = new BufferedReader(reader);
            String line;
            int i = 0;
            bufferedReader.readLine(); // header
            while((line = bufferedReader.readLine()) != null){
               orders[i++] = new Order(line);
            }
            reader.close();
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();

        }
    }
    private void setOrdersOAsPOI(){
        for(Order o: orders){
            o.setPoAsPOI(POIs);
        }
        System.out.println();
    }
    private void write(String filename){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for(Order o:orders){
                writer.write(o.toString()+"\n");
            }

            writer.close();
        }
        catch (Exception e){

        }
    }
    Main(){

        String filename_poi = "E:\\实验室\\公交数据\\毕设代码\\centers_POI_gd.txt";

        set_pois(filename_poi);

        // 获取订单，并把订单的起点归属到POI
        get_orders();
        setOrdersOAsPOI();
        write( "E:\\实验室\\公交数据\\毕设代码\\20160901_path_OtoPOI.txt");
       //  String centers_poi = "E:\\实验室\\公交数据\\毕设代码\\centers_gd.txt";
//        candidates = new Candidates(centers_poi);
    }
    public static void main(String[] args){

        Main main = new Main();

    }
}
