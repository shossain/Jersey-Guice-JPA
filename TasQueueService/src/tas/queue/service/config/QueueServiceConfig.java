package tas.queue.service.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import tas.queue.service.dao.MessageDao;
import tas.queue.service.dao.QueueDao;
import tas.queue.service.dao.UserDao;
import tas.queue.service.dao.impl.MessageDaoImpl;
import tas.queue.service.dao.impl.QueueDaoImpl;
import tas.queue.service.dao.impl.UserDaoImpl;
import tas.queue.service.exception.QSExceptionMapper;
import tas.queue.service.interceptor.AuthorizationRequired;
import tas.queue.service.interceptor.AuthorizationRequiredInterceptor;
import tas.queue.service.interceptor.BuildResponse;
import tas.queue.service.interceptor.BuildResponseInterceptor;
import tas.queue.service.util.RequestItem;
import tas.queue.service.util.RequestLogger;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import com.google.inject.matcher.Matchers;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class QueueServiceConfig extends GuiceServletContextListener {

	@Override
    protected Injector getInjector() {
        return Guice.createInjector ( new JerseyServletModule() {

            @Override
            protected void configureServlets() {
            	install ( new JpaPersistModule ( "tasqspu-dev" ) );
            	
            	bind ( QueueDao.class )
            		.to ( QueueDaoImpl.class )
            		.in( Singleton.class );
            	bind ( UserDao.class )
        			.to ( UserDaoImpl.class )
        			.in( Singleton.class );
            	bind ( MessageDao.class )
            		.to ( MessageDaoImpl.class )
            		.in( Singleton.class );            	
            	bind (QSExceptionMapper.class );
                
            	bindInterceptor ( Matchers.any(), Matchers.annotatedWith ( AuthorizationRequired.class ), 
			  	        		  new AuthorizationRequiredInterceptor ( 
				  						  getProvider ( RequestItem.class ), 
				  						  getProvider ( RequestLogger.class ),
				  						  getProvider ( HttpServletRequest.class ),
				  						  getProvider ( UserDao.class ) ) );
  	
            	bindInterceptor ( Matchers.any(), Matchers.annotatedWith ( BuildResponse.class ), 
            	        		  new BuildResponseInterceptor ( 
	        	        				  getProvider ( RequestItem.class ), 
				  						  getProvider ( RequestLogger.class ) ) );            	
            	
            	Map < String, String > params = new HashMap < String, String >();
                params.put ( PackagesResourceConfig.PROPERTY_PACKAGES, 
                			 "tas.queue.service.resource" );
                
                filter ( QSConstants.Path.root ).through ( PersistFilter.class );
                serve ( QSConstants.Path.root ).with ( GuiceContainer.class, params );            
            }
        });
	} 
}
