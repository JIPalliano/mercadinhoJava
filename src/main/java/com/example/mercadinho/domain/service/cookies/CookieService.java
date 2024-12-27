//package com.example.mercadinho.domain.service.cookies;
//  REFAZER!@@#$!
//import io.netty.handler.codec.http.cookie.Cookie;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.session.CookieWebSessionIdResolver;
//import org.springframework.web.server.session.WebSessionIdResolver;
//
//@Service
//public class CookieService {
//
//    // Método para criar um cookie
//    public void createCookie(HttpServletResponse response, String name, String value, int maxAge) {
//        Cookie cookie = new Cookie(name, value);
//        cookie.setPath("/");
//        cookie.setMaxAge(maxAge);
//        response.addCookie(cookie);
//    }
//
//    // Método para ler um cookie pelo nome
//    public String readCookie(HttpServletRequest request, String name) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (name.equals(cookie.getName())) {
//                    return cookie.getValue();
//                }
//            }
//        }
//        return null;
//    }
//
//    // Método para deletar um cookie pelo nome
//    public void deleteCookie(HttpServletResponse response, String name) {
//        Cookie cookie = new Cookie(name, null);
//        cookie.setPath("/");
//        cookie.setMaxAge(0); // Expira o cookie imediatamente
//        response.addCookie(cookie);
//    }
//
//    @Bean
//    public WebSessionIdResolver cookieSerializer() {
//        CookieWebSessionIdResolver resolver = new CookieWebSessionIdResolver();
//        resolver.setCookieName("MYLOGGIN");
//        return resolver;
//    }
//}
