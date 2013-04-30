package tas.queue.service.dao;

import java.io.Serializable;


import tas.queue.service.model.BaseBean;

public interface GenericDao < T extends BaseBean, PK extends Serializable > {
	public T save ( T t );
	public T find ( PK id );
	public void remove ( T t );
}
