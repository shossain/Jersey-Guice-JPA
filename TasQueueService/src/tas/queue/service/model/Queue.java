package tas.queue.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import tas.queue.service.config.QSConstants;
import tas.queue.service.exception.QSException;



@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
@Entity
@Table ( name = "queues", 
	     uniqueConstraints = @UniqueConstraint ( columnNames = { "queue_name", "user_id" } ))
public class Queue extends BaseBean {
	@Id 
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@Column ( name = "queue_id" )
	@XmlTransient
	private Long queueId;
	
	@Column ( name = "queue_name", nullable = false )
	private String queueName;

	@Column ( name = "visibility_timeout_sec", nullable = false )
	@XmlElement ( name = QSConstants.QueryParameters.visibilityTimeout )
	private Long visibilityTimeoutInSecs = new Long ( 60 );
	
	@Column ( name = "expiration_timeout_sec", nullable = false )
	@XmlElement ( name = QSConstants.QueryParameters.expirationTimeout )
	private Long expirationTimeoutInSecs = new Long ( 345600 );	
	
	@ManyToOne ( optional = false, fetch = FetchType.LAZY )
    @JoinColumn ( name = "user_id", referencedColumnName = "user_id", nullable = false )
	@XmlTransient	
	private User user;
	
	@OneToMany ( mappedBy = "queue", targetEntity = Message.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE )
	@OrderBy ( "visibilityTimeoutInSecs ASC" )	
	@XmlTransient
	private List < Message > messages;
	//todo: only fetch timeout messages
	
	public Queue () {
	}
	
	public  Queue ( String queueName,
					User user ) throws QSException {
		
		verifyTextField ( QSConstants.Ranges.QueueName.length, 
						  queueName, 
						  QSConstants.PathParameters.queueName );
		
		this.queueName = queueName;
		this.user = user;
	}
	
	public String getQueueName() {
		return queueName;
	}
	
	public Long getQueueId() {
		return queueId;
	}

	public Long getExpirationTimeoutInSecs() {
		return expirationTimeoutInSecs;
	}

	public void setExpirationTimeoutInSecs(Long expirationTimeoutInSecs) throws QSException {
		if ( expirationTimeoutInSecs == null ) {
			return;
		}
		
		verifyNumericField ( QSConstants.Ranges.ExpirationTimeout.min, 
							 QSConstants.Ranges.ExpirationTimeout.max, 
							 expirationTimeoutInSecs, 
							 QSConstants.QueryParameters.expirationTimeout );
		
		this.expirationTimeoutInSecs = expirationTimeoutInSecs;		
	}

	public Long getVisibilityTimeoutInSecs() {
		return visibilityTimeoutInSecs;
	}

	public void setVisibilityTimeoutInSecs(Long visibilityTimeoutInSecs) throws QSException {
		if ( visibilityTimeoutInSecs == null ) {
			return;
		}
		
		verifyNumericField ( QSConstants.Ranges.VisibilityTimeout.min, 
							 QSConstants.Ranges.VisibilityTimeout.max, 
							 visibilityTimeoutInSecs, 
							 QSConstants.QueryParameters.visibilityTimeout );
		
		this.visibilityTimeoutInSecs = visibilityTimeoutInSecs;
	}
	
	public User getUser() {
		return user;
	}
	
	public List < Message > getMessages () {
		return messages;
	}
	
	@Override
	public Long getPrimaryKey () {
		return queueId;
	}
}
