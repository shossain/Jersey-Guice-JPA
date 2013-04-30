package tas.queue.service.dao.impl;



import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import tas.queue.service.dao.UserDao;

import tas.queue.service.model.User;
import tas.queue.service.util.RequestLogger;

@Singleton
public class UserDaoImpl extends GenericDaoImpl < User, String > implements UserDao {
	@Inject
	public UserDaoImpl ( Provider < EntityManager > emp, RequestLogger logger ) {
		super( emp, User.class, logger );
	}
	
}
