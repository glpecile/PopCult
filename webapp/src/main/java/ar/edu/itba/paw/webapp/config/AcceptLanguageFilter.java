package ar.edu.itba.paw.webapp.config;

import org.springframework.context.i18n.LocaleContextHolder;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.Locale;

/**
 * Links de referencia
 * https://docs.oracle.com/javaee/7/api/javax/ws/rs/container/ContainerRequestFilter.html
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/i18n/LocaleContextHolder.html
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Language
 * https://docs.oracle.com/javaee/7/api/javax/ws/rs/container/ContainerRequestContext.html
 */

@Provider
public class AcceptLanguageFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        List<Locale> acceptableLanguages = requestContext.getAcceptableLanguages();
        if (!acceptableLanguages.isEmpty()) {
            LocaleContextHolder.setLocale(acceptableLanguages.get(0));
        }
    }
}
