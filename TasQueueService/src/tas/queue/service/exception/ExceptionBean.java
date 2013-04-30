package tas.queue.service.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import tas.queue.service.model.BaseBean;

@XmlRootElement
@XmlAccessorType ( XmlAccessType.FIELD )
public class ExceptionBean extends BaseBean {
	private String errorCode;
	private String message;	

	public ExceptionBean () { }

	public ExceptionBean ( QSException exception ) {
		setErrorCode ( exception.getErrorCode().getAppStatus ( ) );
		setMessage ( exception.getMessage ( ) );
		setRequestId ( exception.getRequestId ( ) );
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
