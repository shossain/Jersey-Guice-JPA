package tas.queue.service.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.inject.Inject;
import com.google.inject.Provider;




import tas.queue.service.config.QSConstants;
import tas.queue.service.interceptor.AuthorizationRequired;
import tas.queue.service.interceptor.BuildResponse;

import tas.queue.service.model.Queue;
import tas.queue.service.model.QueueList;
import tas.queue.service.util.RequestItem;
import tas.queue.service.util.RequestLogger;


@Path ( QSConstants.Path.queues )
public class QueuesResource extends BaseResource {
			
	private Provider < QueueResource > queueProvider; 
	
	@Inject	
	public QueuesResource ( RequestItem requestItem,
						    RequestLogger requestLogger,
						    Provider < QueueResource > queueProvider ) {
		super ( requestItem, requestLogger );
		this.queueProvider = queueProvider;		
	}
	
	@GET
	@AuthorizationRequired
	@BuildResponse		
	public QueueList getQueues (  ) {
		List < Queue > queues = new ArrayList < Queue > (
										requestItem.getUser().getQueues().values() );
		logger.info ( "number of queues: " + queues.size() 
						+ " userId: " + requestItem.getUser().getUserId() );
		return new QueueList ( queues );
	}
	
	@AuthorizationRequired
	@Path ( "/{" + QSConstants.PathParameters.queueName + "}" )	
	public QueueResource getQueueResource ( @PathParam ( QSConstants.PathParameters.queueName ) 
											String queueName ) {
		logger.info( "[queueName]::" + queueName );
		requestItem.setQueueName ( queueName );
		requestItem.setQueue ( requestItem.getUser().getQueueByName ( queueName ));
		
	    return queueProvider.get();
	 }

}
