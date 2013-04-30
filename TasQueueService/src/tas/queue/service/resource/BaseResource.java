package tas.queue.service.resource;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.servlet.RequestScoped;

import tas.queue.service.util.RequestItem;
import tas.queue.service.util.RequestLogger;

@RequestScoped
@Produces ( MediaType.APPLICATION_JSON )
public abstract class BaseResource {
	protected RequestItem requestItem;
	protected RequestLogger logger;

	public BaseResource ( RequestItem requestItem,
						  RequestLogger logger ) {
		this.requestItem = requestItem;
		this.logger = logger;
	}
}
