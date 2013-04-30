package tas.queue.service.interceptor;

import javax.servlet.http.HttpServletRequest;


import org.aopalliance.intercept.MethodInvocation;

import tas.queue.service.config.QSConstants;
import tas.queue.service.dao.UserDao;
import tas.queue.service.exception.QSException;
import tas.queue.service.exception.QSException.ErrorCode;
import tas.queue.service.model.User;
import tas.queue.service.util.Global;
import tas.queue.service.util.RequestItem;
import tas.queue.service.util.RequestLogger;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class AuthorizationRequiredInterceptor extends BaseInterceptor {
	private final Provider < HttpServletRequest > servletRequestProvider;
	private final Provider < UserDao > userDaoProvider;

	@Inject
	public AuthorizationRequiredInterceptor ( Provider < RequestItem > requestItemProvider,
			 								  Provider < RequestLogger > loggerProvider,
			 								  Provider < HttpServletRequest > servletRequestProvider,
			 								  Provider < UserDao > userDaoProvider ) {	
		super ( requestItemProvider, loggerProvider);
		
		this.servletRequestProvider = servletRequestProvider;
		this.userDaoProvider = userDaoProvider;
	}
	
	@Override
	public Object invoke ( MethodInvocation invocation, 
						   String methodName,
						   RequestItem requestItem, 
						   RequestLogger logger ) throws Throwable {
		
		logger.info ( "@AuthorizationRequired::" + methodName );
		requestItem.setUser ( getUserFromRequest ( servletRequestProvider.get() ) );
		logger.info ( "[userId]::" + requestItem.getUser().getUserId() );
		
		return invocation.proceed();
	}
	
	private User getUserFromRequest ( HttpServletRequest request ) throws QSException {		
		String userId = request.getParameter ( QSConstants.QueryParameters.userId );
		String apiKey = request.getParameter ( QSConstants.QueryParameters.apiKey );
		
		if ( userId == null || apiKey == null ) {
			throw new QSException ( ErrorCode.UnAuthorized,
									Global.createInvalidParametersMessage ( 
											QSConstants.QueryParameters.userId, 
											QSConstants.QueryParameters.apiKey ) );					 
		}

		User user = userDaoProvider.get().find ( userId );

		if ( user == null || !apiKey.equals ( user.getApiKey() ) ) {
			throw new QSException ( ErrorCode.UnAuthorized,
									Global.createInvalidParametersMessage ( 
											QSConstants.QueryParameters.userId, 
											QSConstants.QueryParameters.apiKey ) );
		} 
		
		return user;
	}


}
