package tas.queue.service.util;



import tas.queue.service.model.Message;
import tas.queue.service.model.Queue;
import tas.queue.service.model.User;

import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class RequestItem {
	
	private long startTime;
	private String requestName;
	private String requestId;
	
	private String queueName;
	private User user;	
	private Queue queue;
	private Message message;
	
	public RequestItem () {
		requestId = Global.generateUniqueName();		
		this.requestName = "[" + requestId + "] " + "::";
		startTime = System.currentTimeMillis();	
	}

	public long getStartTime() {
		return startTime;
	}

	public String getRequestName() {
		return requestName;
	}

	public String getRequestId() {
		return requestId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
}
