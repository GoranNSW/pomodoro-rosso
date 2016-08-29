package rs.goran.ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.identitytoolkit.GitkitClient;
import com.google.identitytoolkit.GitkitClientException;
import com.google.identitytoolkit.GitkitUser;

@Controller
public class LoginServlet {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws ServletException, IOException {
        // This check prevents the "/" handler from handling all requests by
        // default
        if (!"/".equals(request.getServletPath())) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "";
        }

        try {
            GitkitUser gitkitUser = null;
            GitkitClient gitkitClient = GitkitClient.createFromJson("gitkit-server-config.json");

            gitkitUser = gitkitClient.validateTokenInRequest(request);
            String userInfo = null;
            if (gitkitUser != null) {
                userInfo = "Welcome back!<br><br> Email: " + gitkitUser.getEmail() + "<br> Id: "
                        + gitkitUser.getLocalId() + "<br> Provider: " + gitkitUser.getCurrentProvider();
                model.put("email", "Successfully logged with email: " + gitkitUser.getEmail());
            }

            // response.getWriter()
            // .print(new Scanner(new File("/WEB-INF/views/index.jsp"),
            // "UTF-8").useDelimiter("\\A").next()
            // .replaceAll("WELCOME_MESSAGE", userInfo != null ? userInfo : "You
            // are not logged in")
            // .toString());
            // response.setStatus(HttpServletResponse.SC_OK);
        } catch (FileNotFoundException | GitkitClientException | JSONException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().print(e.toString());
        }
        return "index";
    }
}
