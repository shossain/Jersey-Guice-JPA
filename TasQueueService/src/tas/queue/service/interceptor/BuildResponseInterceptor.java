package tas.queue.service.interceptor;







import org.aopalliance.intercept.MethodInvocation;

import tas.queue.service.model.BaseBean;
import tas.queue.service.util.RequestItem;
import tas.queue.service.util.RequestLogger;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BuildResponseInterceptor extends BaseInterceptor {
	
	@Inject
	public BuildResponseInterceptor ( Provider < RequestItem > requestItemProvider,
							 		  Provider < RequestLogger > loggerProvider ) {
		super ( requestItemProvider, loggerProvider);				
	}

	@Override	
	public Object invoke ( MethodInvocation invocation, 
						   String methodName,
						   RequestItem requestItem, 
						   RequestLogger logger ) throws Throwable {

		try {
			logger.info ( "@BuildResponse::" + methodName );			
			
			BaseBean methodReturn = ( BaseBean ) invocation.proceed();
			
			methodReturn.setRequestId ( requestItem.getRequestId() );			
			return methodReturn;			
		} finally {
			logger.logRequestComplete ();			
		}
	 }
}
