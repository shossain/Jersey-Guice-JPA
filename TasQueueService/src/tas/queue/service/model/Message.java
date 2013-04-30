package tas.queue.service.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import tas.queue.service.config.QSConstants;
import tas.queue.service.exception.QSException;
import tas.queue.service.util.Global;


@XmlRootElement
@XmlAccessorType ( XmlAccessType.FIELD )

@Entity
@Table ( name = "messages" )
public class Message extends BaseBean {
	@Id
	@Column ( name = "message_id" )
	private String messageId;
	
	@Lob
	@Column ( name = "message_text", length = QSConstants.Ranges.MessageText.length )
	private String messageText;	
	
	@Column ( name = "visibility_timeout_sec", nullable = false )
	@XmlTransient
	private Long visibilityTimeoutInSecs;
	
	@Column ( name = "creation_time_sec", nullable = false )
	@XmlTransient
	private Long creationTimeInSecs;
	
	@ManyToOne ( optional = false, fetch = FetchType.LAZY )
    @JoinColumn ( name = "queue_id", referencedColumnName = "queue_id", nullable = false )
	@XmlTransient
	private Queue queue;
	
	public Message () {
	}
	
	public Message ( String messageId, String messageText, Queue queue ) throws QSException {
		verifyTextField ( QSConstants.Ranges.MessageText.length, 
						  messageText, 
						  QSConstants.FormParameters.messageText );
		
		this.messageId = messageId;
		this.messageText = messageText;
		this.queue = queue;
	}
	
	public String getMessageId() {
		return messageId;
	}

	public String getMessageText() {
		return messageText;
	}
	
	public Queue getQueue() {
		return queue;
	}
	
	public Long getVisibilityTimeoutInSecs() {
		return visibilityTimeoutInSecs;
	}

	public void updateVisibilityTimeout ( Long deltaInSecs ) throws QSException {
		if ( deltaInSecs == null ) {
			return;
		}
		
		verifyNumericField ( QSConstants.Ranges.VisibilityTimeout.min, 
							 QSConstants.Ranges.VisibilityTimeout.max, 
							 deltaInSecs, 
							 QSConstants.QueryParameters.visibilityTimeout );
		
		this.visibilityTimeoutInSecs = deltaInSecs + Global.getCurrentTimeInSecs ( );
	}

	public void setVisibilityTimeoutInSecs ( Long visibilityTimeoutInSecs ) {
		if ( visibilityTimeoutInSecs == null ) {
			return;
		}
		
		this.visibilityTimeoutInSecs = visibilityTimeoutInSecs;
	}
	
	public Long getCreationTimeInSecs() {
		return creationTimeInSecs;
	}

	public void setCreationTimeInSecs(Long creationTimeInSecs) {
		this.creationTimeInSecs = creationTimeInSecs;
	}
	
	@Override
	public String getPrimaryKey () {
		return messageId;
	}

}
