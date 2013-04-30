package tas.queue.service.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;


import tas.queue.service.config.QSConstants;
import tas.queue.service.dao.MessageDao;
import tas.queue.service.exception.QSException;
import tas.queue.service.interceptor.BuildResponse;
import tas.queue.service.model.BaseBean;
import tas.queue.service.model.Message;
import tas.queue.service.util.Global;
import tas.queue.service.util.RequestItem;
import tas.queue.service.util.RequestLogger;

import com.google.inject.Inject;

public class MessageResource extends BaseResource {
	private MessageDao messageDao;
	
	@Inject
	public MessageResource ( RequestItem requestItem,
			  			   	 RequestLogger requestLogger,
			  			   	 MessageDao messageDao ) {
		super ( requestItem, requestLogger );			
		this.messageDao = messageDao;		
	}
	
	@DELETE
	@BuildResponse	
	public BaseBean deleteMessage () {
		messageDao.remove ( requestItem.getMessage() );
		return new BaseBean();
	}
	
	@PUT
	@BuildResponse	
	public Message updateMessage ( @QueryParam ( QSConstants.QueryParameters.visibilityTimeout ) 
								   String visibilityTimeout ) throws QSException {
		
		Long timeoutDelta = Global.convertStringToLong ( QSConstants.QueryParameters.visibilityTimeout, 
									 					 visibilityTimeout );
		logger.info ( "visibilityTimeout: " + visibilityTimeout );
		Message message = requestItem.getMessage();
		
		if ( timeoutDelta == null ) {
			logger.info ( "not updating message." );
			return message;
		}  
		
		message.updateVisibilityTimeout ( timeoutDelta );
		return messageDao.save ( message );
	}
	
	
}
