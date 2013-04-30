package tas.queue.service.exception;

import javax.ws.rs.core.Response.Status;


public class QSException extends Exception {

	private static final long serialVersionUID = 3695746557869146327L;
	
	private ErrorCode code;
	private String message;
	private String requestId;
	
	public enum ErrorCode {		
		InternalError ( "InternalError", Status.INTERNAL_SERVER_ERROR ),
		UnAuthorized ( "UnAuthorized", Status.UNAUTHORIZED ),
		InvalidInput ( "InvalidInput", Status.BAD_REQUEST );
						
		private String appStatus;
		private Status httpStatus;
		
		ErrorCode ( String appStatus, Status httpStatus ) {
			this.appStatus = appStatus;
			this.httpStatus = httpStatus;
		}
		
		public String getAppStatus () {
			return appStatus;
		}
		
		public Status getHttpStatus () {
			return httpStatus;
		}
	} 
	
	public QSException () {
		
	}
	
	public QSException ( ErrorCode code ) {
		this.code = code;		
	}
	
	public QSException ( ErrorCode code, String message ) {
		this.code = code;
		this.message = message;
	}
	
	public ErrorCode getErrorCode () {
		return code;
	}
	
	public String getMessage () {
		return message;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
