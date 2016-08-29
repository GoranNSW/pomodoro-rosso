package rs.goran.ui;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WidgetServlet {

    @RequestMapping(value = "/gitkit", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = request.getReader().readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String postBody = URLEncoder.encode(builder.toString(), "UTF-8");

        // try {
        // response.getWriter().print(new Scanner(new
        // File("/WEB-INF/views/gitkit-widget.jsp"), "UTF-8")
        // .useDelimiter("\\A").next().replaceAll("JAVASCRIPT_ESCAPED_POST_BODY",
        // postBody).toString());
        // response.setStatus(HttpServletResponse.SC_OK);
        // } catch (FileNotFoundException e) {
        // e.printStackTrace();
        // response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        // response.getWriter().print(e.toString());
        // }
        return "gitkit-widget";
    }

    @RequestMapping(value = "/gitkit", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}