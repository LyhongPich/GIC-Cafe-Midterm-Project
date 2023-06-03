package gic.i4b.group6.CafeManagement.security;
 
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import gic.i4b.group6.CafeManagement.models.Users;
import gic.i4b.group6.CafeManagement.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired private UserRepository userRepository;
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
 
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Users user = customUserDetails.getUsers();

        user.setLogin_date(new Date());
        userRepository.save(user);

        String redirectURL = request.getContextPath();
         
        if (customUserDetails.hasRole("Admin")) {
            response.sendRedirect(redirectURL + "/admin");
        } else if (customUserDetails.hasRole("Cashier")) {
            request.setAttribute("cashierId", user.getId());

            request.getRequestDispatcher("/tableSelection").forward(request, response);
        }
        // response.sendRedirect(redirectURL);
    }
}