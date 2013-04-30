package tas.queue.service.dao.impl;




import javax.persistence.EntityManager;



import tas.queue.service.dao.QueueDao;

import tas.queue.service.model.Queue;

import tas.queue.service.util.RequestLogger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class QueueDaoImpl extends GenericDaoImpl < Queue, Long > implements QueueDao {
	
	@Inject
	public QueueDaoImpl ( Provider < EntityManager > emp, RequestLogger logger ) {
		super( emp, Queue.class, logger );
	}
}
