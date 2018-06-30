import java.io.*;

/**
 * Created by jiayi on 2018/6/21.
 */
public class Tester {
    public void read(){
        String filename = "E:\\实验室\\公交数据\\毕设代码\\20160901_path_OtoPOI.txt";
        File file = new File(filename);
        try{
            // count lines
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            int i = 0;
            bufferedReader.readLine(); // header
            Candidates in = new Candidates("E:\\实验室\\公交数据\\毕设代码\\centers_gd.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\实验室\\公交数据\\毕设代码\\20160901_candidates.txt"));
            while((line = bufferedReader.readLine()) != null){

                System.out.println(i);
                String[] datas = line.split(",");
                String oLoc = datas[1]+","+datas[2];
                String dLoc = datas[3]+","+datas[4];

                String result = in.getCandidate(oLoc, dLoc);
                writer.write(result + "\n");
                ++i;
                if(i>100){
                    break;
                }
            }
            writer.close();
            reader.close();
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();

        }
    }
    public static void main(String[] args){
        new Tester().read();
    }
}
