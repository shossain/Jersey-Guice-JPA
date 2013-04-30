package tas.queue.service.model;



import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;


@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
@Entity
@Table ( name = "users" )
public class User extends BaseBean {
	@Id
	@Column ( name = "user_id" )
	private String userId;
	
	@Column ( name = "api_key", unique = true, nullable = false )
	private String apiKey;
	
	@OneToMany ( mappedBy = "user", targetEntity = Queue.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE )
	@MapKeyColumn ( name="queue_name", insertable = false, updatable = false )
	private Map < String, Queue > queues; 
	
	public User () {
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Map < String, Queue > getQueues() {
		return queues;
	}
		
	public Queue getQueueByName ( String queueName ) {		
		return queues.get ( queueName );
	}
	
	@Override
	public String getPrimaryKey () {
		return userId;
	}

}
