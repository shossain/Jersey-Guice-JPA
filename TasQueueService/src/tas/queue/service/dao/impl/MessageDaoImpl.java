package tas.queue.service.dao.impl;




import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import tas.queue.service.dao.MessageDao;
import tas.queue.service.model.Message;
import tas.queue.service.model.Queue;

import tas.queue.service.util.Global;
import tas.queue.service.util.RequestLogger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class MessageDaoImpl extends GenericDaoImpl < Message, String > implements MessageDao {
	
	@Inject
	public MessageDaoImpl ( Provider < EntityManager > emp, RequestLogger logger ) {
		super( emp, Message.class, logger );
	}
	
	//todo: 1. Delete expired messages with single query.
	//2. update and select with a single query.	
	@Override
	public Message getNextVisibleMessage ( Queue queue ) {
		
		
		return null;
	}
	
	@Transactional
	private int tryUpdateVisibilityTimeout ( String messageId, 
									 long updatedVisibilityTimeout, 
									 long currentTimeInSecs ) {
		
		return query.executeUpdate();
	}

	@Transactional
	private void removeAllMessages ( List < Message > messages ) {
		
		for ( Message message : messages ) {
			logger.info ( "removing expired message. messageId: " + message.getMessageId ( ) );
			em().remove ( message );
		}
	}
}
