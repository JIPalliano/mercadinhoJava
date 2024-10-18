package com.example.mercadinho.service.cookies;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CookieInterceptor implements HandlerInterceptor {

    // Intercepta a requisição antes de chegar ao controlador
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Ler os cookies existentes
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("Cookie encontrado: " + cookie.getName() + " = " + cookie.getValue());
            }
        }

        // Adicionar um cookie na resposta, se necessário
        Cookie newCookie = new Cookie("interceptorCookie", "cookieValue");
        newCookie.setPath("mercadinho/api/v1/shoppintCart");
        newCookie.setMaxAge(3600); // 1 hora
        response.addCookie(newCookie);

        return true; // Continua a execução da requisição
    }

}
