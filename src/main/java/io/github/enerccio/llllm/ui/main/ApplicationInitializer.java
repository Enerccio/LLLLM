package io.github.enerccio.llllm.ui.main;

import io.github.enerccio.llllm.model.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        XmlWebApplicationContext rootContext = initRootApplicationContext(servletContext);
        rootContext.addApplicationListener(event -> {
            try {
                if (event instanceof ContextRefreshedEvent) {
                    UserService userService = rootContext.getBean(UserService.class);
                    userService.onInitialize();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private XmlWebApplicationContext initRootApplicationContext(ServletContext servletContext) throws ServletException {
        XmlWebApplicationContext rootContext = new XmlWebApplicationContext();
        rootContext.setConfigLocations("/WEB-INF/classes/META-INF/spring/application-config.xml");

        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.addListener(new RequestContextListener());
        return rootContext;
    }
}
