package com.codegym.web;

import java.io.File;
import java.io.IOException;

import com.codegym.web.servlets.*;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;
import org.apache.jasper.servlet.JasperInitializer;
import com.codegym.web.repository.InMemoryUserRepository;

public class TomcatMain {
  public static void main(String[] args) throws LifecycleException, IOException {
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8082);

    String baseDir = new File("target/tomcat").getAbsolutePath();
    tomcat.setBaseDir(baseDir);
    String webAppDir = new File("src/main/webapp").getCanonicalPath();
    new File(webAppDir).mkdirs();
    Context context = tomcat.addWebapp("", webAppDir);
    context.addWelcomeFile("index.jsp");
    context.addServletContainerInitializer(new JasperInitializer(), null);

    ContextConfig ctxCfg = new ContextConfig();
    context.addLifecycleListener(ctxCfg);
    ctxCfg.setDefaultWebXml("org/apache/catalina/startup/NO_DEFAULT_XML");
    context.setXmlValidation(false);
    context.setXmlNamespaceAware(false);


    InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
    Wrapper jsp = Tomcat.addServlet(context, "jsp", "org.apache.jasper.servlet.JspServlet");
    jsp.addInitParameter("fork", "false");
    jsp.setLoadOnStartup(3);
    context.addServletMappingDecoded("*.jsp", "jsp");

    /*
    Tomcat.addServlet(context, "userServletOld", new UserServlet(inMemoryUserRepository));
    context.addServletMappingDecoded("/users", "userServletOld");
    context.addServletMappingDecoded("/users/new", "userServletOld");
    context.addServletMappingDecoded("/users/create", "userServletOld");
    context.addServletMappingDecoded("/users/update", "userServletOld");
    context.addServletMappingDecoded("/users/edit", "userServletOld");
    context.addServletMappingDecoded("/users/delete", "userServletOld");
*/
    Tomcat.addServlet(context, "usersServlet", new UsersServlet(inMemoryUserRepository));
    Tomcat.addServlet(context, "UserCreateServlet", new UserCreateServlet(inMemoryUserRepository));
    Tomcat.addServlet(context, "UserNewServlet", new UserNewServlet());
    Tomcat.addServlet(context, "UserUpdateServlet", new UserUpdateServlet(inMemoryUserRepository));
    Tomcat.addServlet(context, "UserEditServlet", new UserEditServlet(inMemoryUserRepository));
    Tomcat.addServlet(context, "UserDeleteServlet", new UserDeleteServlet(inMemoryUserRepository));
    context.addServletMappingDecoded("/users", "usersServlet");
    context.addServletMappingDecoded("/users/create", "UserCreateServlet");
    context.addServletMappingDecoded("/users/new", "UserNewServlet");
    context.addServletMappingDecoded("/users/update", "UserUpdateServlet");
    context.addServletMappingDecoded("/users/edit", "UserEditServlet");
    context.addServletMappingDecoded("/users/delete", "UserDeleteServlet");


    tomcat.start();
    System.out.println("== Tomcat started on port " + tomcat.getConnector().getLocalPort());
    System.out.println("== Registered servlet mappings:");
    context.findChildren();
    for (String pattern : context.findServletMappings()) {
      String name = context.findServletMapping(pattern);
      System.out.println("   " + pattern + " -> " + name);
    }
    tomcat.getServer().await();
  }
}
