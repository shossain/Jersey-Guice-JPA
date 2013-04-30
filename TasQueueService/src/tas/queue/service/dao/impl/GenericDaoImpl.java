package tas.queue.service.dao.impl;


import java.io.Serializable;


import javax.persistence.EntityManager;

import tas.queue.service.dao.GenericDao;
import tas.queue.service.model.BaseBean;
import tas.queue.service.util.RequestLogger;

import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public abstract class GenericDaoImpl < T extends BaseBean, PK extends Serializable > 
									implements GenericDao < T, PK > {

    private final Provider < EntityManager > emp;
    private Class < T > clazz;
    protected RequestLogger logger;
    
    protected GenericDaoImpl ( Provider < EntityManager > emp, 
    					  	   Class < T > clazz,
    					       RequestLogger logger ) {
        this.emp = emp;
        this.clazz = clazz;
        this.logger = logger;
    }

    protected EntityManager em() {
        return emp.get();
    }

    @Transactional
    public T save ( T t ) {
    	logger.info ( "saving " + clazz.getSimpleName() + ". primary key: " + t.getPrimaryKey() );
    	return em().merge ( t );
    	//todo: consider using persist
    }

    public T find ( PK id ) {
    	logger.info ( "finding " + clazz.getSimpleName() + ". primary key: " + id ); 	
        return em().find ( clazz, id );
    }

    @Transactional
    public void remove ( T t ) {
    	logger.info ( "removing " + clazz.getSimpleName() + ". primary key: " + t.getPrimaryKey() );
        em().remove ( t );
    }
}
