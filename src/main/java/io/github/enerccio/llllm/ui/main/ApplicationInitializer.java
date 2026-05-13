package io.github.enerccio.llllm.ui.main;

import io.github.enerccio.llllm.model.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        XmlWebApplicationContext rootContext = initRootApplicationContext(servletContext);

        UserService userService = rootContext.getBean(UserService.class);
        try {
            userService.onInitialize();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private XmlWebApplicationContext initRootApplicationContext(ServletContext servletContext) throws ServletException {
        XmlWebApplicationContext rootContext = new XmlWebApplicationContext();
        rootContext.setConfigLocations("/WEB-INF/classes/META-INF/spring/application-context.xml");

        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.addListener(new RequestContextListener());
        return rootContext;
    }
}
