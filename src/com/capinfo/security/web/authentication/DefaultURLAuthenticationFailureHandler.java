package com.capinfo.security.web.authentication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class DefaultURLAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    //Failure logic:
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //We inherited that method:
        saveException(request, exception);
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write("{ success: false, error: { reason: '"+exception.getMessage()+"' }}");
        out.close();
    }

    
}