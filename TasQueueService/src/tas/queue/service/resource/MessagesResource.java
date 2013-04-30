package tas.queue.service.resource;



import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;




import tas.queue.service.config.QSConstants;
import tas.queue.service.dao.MessageDao;
import tas.queue.service.exception.QSException;
import tas.queue.service.exception.QSException.ErrorCode;
import tas.queue.service.interceptor.BuildResponse;
import tas.queue.service.model.Message;
import tas.queue.service.model.Queue;
import tas.queue.service.util.Global;
import tas.queue.service.util.RequestItem;
import tas.queue.service.util.RequestLogger;

import com.google.inject.Inject;
import com.google.inject.Provider;


public class MessagesResource extends BaseResource {
	private Provider < MessageResource > messageProvider;
	private MessageDao messageDao;
	
	@Inject
	public MessagesResource ( RequestItem requestItem,
			  			   	  RequestLogger requestLogger,
			  			   	  Provider < MessageResource > messageProvider,
			  			   	  MessageDao messageDao ) {
		super ( requestItem, requestLogger );	
		this.messageProvider = messageProvider;
		this.messageDao = messageDao;		
	}
	
	@GET
	@BuildResponse
	public Message getVisibleMessage () {
		logger.info ( "fetching visible message from queue: "  + requestItem.getQueue().getQueueName() );
		Message message = messageDao.getNextVisibleMessage ( requestItem.getQueue() );
		return ( ( message != null ) ? message : new Message () ); 
	}
	
	@POST
	@BuildResponse	
	public Message createMessage ( @FormParam ( QSConstants.FormParameters.messageText ) 
								   String messageText ) throws QSException {		
		Queue messageQueue = requestItem.getQueue();
		long currentDateInSecs = Global.getCurrentTimeInSecs ( );
		Message message = new Message ( Global.generateUniqueName() + "-" + Global.generateUniqueName(),
										messageText,
										messageQueue );
		
		message.setCreationTimeInSecs ( currentDateInSecs );		
		message.setVisibilityTimeoutInSecs ( currentDateInSecs );
		
		logger.info ( "creating a new message. text: " + messageText 
						+ ", id: " + message.getMessageId ( ) );
		return messageDao.save ( message );
	}

	@Path ( "/{" + QSConstants.PathParameters.messageId + "}" )	
	public MessageResource getMessageResource ( @PathParam ( QSConstants.PathParameters.messageId ) 
												String messageId ) throws QSException {
		
		Message message = messageDao.find ( messageId );
		
		if ( message == null ) {
			throw new QSException ( ErrorCode.InvalidInput,
									Global.createInvalidParametersMessage ( QSConstants.PathParameters.messageId ) );
		}
		
		requestItem.setMessage ( message );
		return messageProvider.get();
	}


}
