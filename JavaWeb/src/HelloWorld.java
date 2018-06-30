import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloWorld extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Candidates in = new Candidates("E:\\实验室\\公交数据\\毕设代码\\centers_gd.txt");

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWorld() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应内容类型
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String requestPos = request.getParameter("data");
        String[] s = requestPos.split(";");
        // respond
        String oLoc = s[0];
        String dLoc = s[1];
        String result = in.getCandidate(oLoc, dLoc);
        // return

        out.println(result);
    }

    // 处理 POST 方法请求的方法
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
//    private String message;
//
//    @Override
//    public void init() throws ServletException {
//        message = "Hello world, this message is from servlet!";
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //设置响应内容类型
//        resp.setContentType("text/html");
//
//        //设置逻辑实现
//        PrintWriter out = resp.getWriter();
//        out.println("<h1>" + message + "</h1>");
//    }
//
//    @Override
//    public void destroy() {
//        super.destroy();
//    }
}
