package rs.goran.ui;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
    public boolean validateUser(String user, String password) {
        return user.equalsIgnoreCase("goran") && password.equals("admin");
    }

    public boolean validateUsername() {
        return false;
    }

}