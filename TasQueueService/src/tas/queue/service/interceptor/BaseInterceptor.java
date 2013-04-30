package tas.queue.service.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import tas.queue.service.exception.QSException;
import tas.queue.service.exception.QSException.ErrorCode;
import tas.queue.service.util.RequestItem;
import tas.queue.service.util.RequestLogger;

import com.google.inject.Provider;

abstract class BaseInterceptor implements MethodInterceptor {
	private final Provider < RequestLogger > loggerProvider;
	private final Provider < RequestItem > requestItemProvider;
	
	public BaseInterceptor ( Provider < RequestItem > requestItemProvider,
							 Provider < RequestLogger > loggerProvider ) {
		this.requestItemProvider = requestItemProvider;
		this.loggerProvider = loggerProvider;		
	}
	
	@Override
	public Object invoke ( MethodInvocation invocation ) throws Throwable {
		RequestLogger logger = loggerProvider.get();
		RequestItem requestItem = requestItemProvider.get();		
		String methodName = invocation.getMethod().getDeclaringClass().getSimpleName() 
								+ "." + invocation.getMethod().getName();
		
		try {
			return invoke ( invocation, methodName, requestItem, logger );
		} catch ( QSException e ) {
			logger.error ( methodName, e );
			e.setRequestId ( requestItem.getRequestId() );
			throw e;
			
		} catch ( Exception e ) {
			logger.error ( methodName, e );
			QSException qsException = new QSException ( ErrorCode.InternalError );
			qsException.setRequestId ( requestItem.getRequestId() );
			throw qsException;
			
		} 
	
	}
	
	public abstract Object invoke ( MethodInvocation invocation, 
									String methodName,
									RequestItem requestItem, 
									RequestLogger logger ) throws Throwable;

}
