package tas.queue.service.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.google.inject.Inject;
import com.google.inject.Provider;

import tas.queue.service.config.QSConstants;
import tas.queue.service.dao.QueueDao;
import tas.queue.service.exception.QSException;
import tas.queue.service.exception.QSException.ErrorCode;
import tas.queue.service.interceptor.BuildResponse;
import tas.queue.service.model.Queue;
import tas.queue.service.model.BaseBean;
import tas.queue.service.util.Global;
import tas.queue.service.util.RequestItem;
import tas.queue.service.util.RequestLogger;


public class QueueResource extends BaseResource {
	private QueueDao queueDao;	
	private Provider < MessagesResource > messagesProvider;

	@Inject
	public QueueResource ( RequestItem requestItem,
			  			   RequestLogger requestLogger,
			  			   Provider < MessagesResource > messagesProvider,
			  			   QueueDao queueDao ) {
		super ( requestItem, requestLogger );	
		this.messagesProvider = messagesProvider;
		this.queueDao = queueDao;		
	}

	@PUT
	@BuildResponse	
	public Queue putQueue ( @QueryParam ( QSConstants.QueryParameters.visibilityTimeout ) 
							String visibilityTimeout,
							@QueryParam ( QSConstants.QueryParameters.expirationTimeout ) 
							String expirationTimeout ) throws QSException {
		Long visibilityTimeoutInSecs = Global.convertStringToLong ( 
													QSConstants.QueryParameters.visibilityTimeout, 
													visibilityTimeout );		
		Long expirationTimeoutInSecs = Global.convertStringToLong ( 
													QSConstants.QueryParameters.expirationTimeout, 
													expirationTimeout );
		
		logger.info ( "visibilityTimeout: " + visibilityTimeoutInSecs 
						+ ", expirationTimeout: " + expirationTimeoutInSecs );
		
		Queue queue = requestItem.getQueue();
		
		if ( queue == null ) {
			logger.info ( "creating queue. queueName: " + requestItem.getQueueName()
								+ ", userId: " + requestItem.getUser().getUserId ( ) );
			queue = new Queue ( requestItem.getQueueName(), requestItem.getUser() );			
		}
		
		queue.setVisibilityTimeoutInSecs ( visibilityTimeoutInSecs );
		queue.setExpirationTimeoutInSecs ( expirationTimeoutInSecs );
		
		return queueDao.save ( queue );
	} 

	@GET
	@BuildResponse		
	public Queue getQueue () throws QSException {
		if ( requestItem.getQueue() == null ) {
			throw new QSException ( ErrorCode.InvalidInput,
									Global.createInvalidParametersMessage ( QSConstants.PathParameters.queueName ) );
		}
		
		return requestItem.getQueue();
	}
	
	@DELETE
	@BuildResponse	
	public BaseBean deleteQueue () throws QSException {
		if ( requestItem.getQueue() == null ) {
			throw new QSException ( ErrorCode.InvalidInput, 
									Global.createInvalidParametersMessage ( QSConstants.PathParameters.queueName ) );
		}
		
		queueDao.remove ( requestItem.getQueue() );
		return new BaseBean(); 
	}
		
	@Path ( QSConstants.Path.messages )	
	public MessagesResource getMessageResource ( ) throws QSException {
		if ( requestItem.getQueue() == null ) {
			throw new QSException ( ErrorCode.InvalidInput,
									Global.createInvalidParametersMessage ( QSConstants.PathParameters.queueName ) );
		}
		
		return messagesProvider.get();
	}
}
