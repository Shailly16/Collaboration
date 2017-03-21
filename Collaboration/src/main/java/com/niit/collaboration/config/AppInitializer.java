package com.niit.collaboration.config;

import java.io.File;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(AppInitializer.class);
	
	@Override
	protected Class[] getRootConfigClasses() {
		logger.debug("Starting of the metnod getRootConfigClasses");
		return new Class[] { AppConfig.class, WebSocketConfig.class };
	}

	@Override
	protected Class[] getServletConfigClasses() {
		logger.debug("Starting of the metnod getServletConfigClasses");
		return new Class[] { AppConfig.class, WebSocketConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		logger.debug("Starting of the metnod getServletMappings");
		return new String[] { "/" };
	}
	
	private int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MB
	@Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		logger.debug("Starting of the metnod customizeRegistration");
		//String path = "resources/img/";
		String path = "F://Collaboration_images";
        File uploadDirectory = new File(path);

        // register a MultipartConfigElement
        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                        maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);

        registration.setMultipartConfig(multipartConfigElement);
        logger.debug("Ending of the metnod customizeRegistration");
    }

	//To use request scope without Spring MVC, you should declare RequestContextListener in web.xml 
	public void onStartup(ServletContext servletContext) throws ServletException {
        
        servletContext.addListener(new RequestContextListener());
	}
}
	
	
	
	