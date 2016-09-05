package rs.goran.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.identitytoolkit.GitkitClient;
import com.google.identitytoolkit.GitkitClientException;
import com.google.identitytoolkit.GitkitUser;

@Controller
@SessionAttributes({ "email" })
public class LoginController {

    @Autowired
    LoginService service;

    private String username;
    private String email;

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
                email = gitkitUser.getEmail();
                model.addAttribute("email", email);
                System.out.println("Gitkit " + userInfo);
                return "loggedin";
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
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String postBody = URLEncoder.encode(builder.toString(), "UTF-8");

        return "gitkit-widget";
    }

    @RequestMapping(value = "/gitkit", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    public String showLoggedIn(ModelMap model) {
        model.addAttribute("username", this.username);
        model.addAttribute("email", this.email);
        System.out.println(email);
        return "loggedin";
    }

    @RequestMapping(value = "/loggedin", method = RequestMethod.POST)
    public String handleUsernameInput(@RequestParam("username") String username, ModelMap model) {
        this.username = username;
        System.out.println("POST " + username + " " + "Email: " + email);
        return "redirect:/loggedin";
    }

}
