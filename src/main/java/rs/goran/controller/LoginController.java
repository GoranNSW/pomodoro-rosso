package rs.goran.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.identitytoolkit.GitkitClient;
import com.google.identitytoolkit.GitkitClientException;
import com.google.identitytoolkit.GitkitUser;

import rs.goran.model.User;
import rs.goran.service.interfaces.UserService;

@Controller
@SessionAttributes({ "id" })
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class);
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
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
                userInfo = "Welcome back! Email: " + gitkitUser.getEmail() + " Provider: "
                        + gitkitUser.getCurrentProvider();
                model.addAttribute("id", gitkitUser.getLocalId());
                logger.info("Gitkit ID:" + gitkitUser.getLocalId());
                return "index";
                // return "redirect:http://localhost:4200/home";
            }

        } catch (FileNotFoundException | GitkitClientException | JSONException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().print(e.toString());
        }
        return "index";
    }

    @RequestMapping(value = "/gitkit", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = request.getReader().readLine()) != null) {
                builder.append(line);
                logger.info("Reader" + line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String postBody = URLEncoder.encode(builder.toString(), "UTF-8");
        logger.info("Return gitkit-widget" + builder.toString());
        return "gitkit-widget";
    }

    @RequestMapping(value = "/gitkit", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        logger.info("Return doGet(request, response);");
    }

}
