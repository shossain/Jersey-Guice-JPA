package tas.queue.service.model;

import java.util.List;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QueueList extends BaseBean {
	private List < Queue > queues;

	public QueueList () {
	}
	
	public QueueList ( List < Queue > queues ) {
		this.queues = queues;
	}
		
	public List < Queue > getQueues() {
		return queues;
	}

	public void setQueues ( List < Queue > queues ) {
		this.queues = queues;
	}
}
