package tas.queue.service.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


import com.google.inject.Singleton;

	
@Provider
@Singleton
public class QSExceptionMapper implements ExceptionMapper < QSException >
{
	@Override
	public Response toResponse ( final QSException exception )
	{
		return Response.status ( exception.getErrorCode().getHttpStatus() )
					   .entity ( new ExceptionBean ( exception ) )
					   .type ( MediaType.APPLICATION_JSON )
    		  		   .build();
	}
}

