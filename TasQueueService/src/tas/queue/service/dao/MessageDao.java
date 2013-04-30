package tas.queue.service.dao;




import tas.queue.service.model.Message;
import tas.queue.service.model.Queue;



public interface MessageDao extends GenericDao < Message, String > {
	Message getNextVisibleMessage ( Queue queue );	
}
