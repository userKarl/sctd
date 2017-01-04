package com.sc.td.frame.init;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.sc.td.frame.cfg.AppConfig;
import com.sc.td.frame.cfg.MvcConfig;
import com.sc.td.frame.filter.Filter_CheckToken;

public class WebAppInitializer implements WebApplicationInitializer {
	@Override
	public void onStartup(final ServletContext sc) throws ServletException {
		EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD,
				DispatcherType.INCLUDE);
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(AppConfig.class, MvcConfig.class);
		sc.addListener(new ContextLoaderListener(rootContext));

		DispatcherServlet dispatcherServlet = new DispatcherServlet(rootContext);

		ServletRegistration.Dynamic dynamic = sc.addServlet("dispatcherServlet", dispatcherServlet);
		dynamic.setLoadOnStartup(1);
		dynamic.addMapping("/");
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		Filter_CheckToken filter_CheckToken = new Filter_CheckToken();
		OpenEntityManagerInViewFilter omi = new OpenEntityManagerInViewFilter();
		omi.setEntityManagerFactoryBeanName("mysqlEntityManagerFactory");
		sc.addFilter("openEntityManagerInViewFilter", omi).addMappingForServletNames(dispatcherTypes, true,
				"dispatcherServlet");
		sc.addFilter("characterEncodingFilter", characterEncodingFilter).addMappingForServletNames(dispatcherTypes,
				true, "dispatcherServlet");
		sc.addFilter("filter_CheckToken", filter_CheckToken).addMappingForServletNames(dispatcherTypes, true,
				"dispatcherServlet");

	}
}
