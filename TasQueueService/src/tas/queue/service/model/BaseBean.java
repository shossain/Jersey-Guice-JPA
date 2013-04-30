package tas.queue.service.model;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import tas.queue.service.exception.QSException;
import tas.queue.service.exception.QSException.ErrorCode;

//todo: verify every field
@XmlRootElement
@XmlAccessorType ( XmlAccessType.FIELD )
public class BaseBean {
	
	protected String requestId;
	
	public BaseBean () {
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	public Object getPrimaryKey () {
		return "";
	}
	
	protected void verifyNumericField ( Long min, Long max, 
										Long inputValue, String paramName ) throws QSException {
		if ( min > inputValue || max < inputValue ) {
			throw new QSException ( ErrorCode.InvalidInput, 
					"'" + paramName + "' should be between " + min + " and " + max );
		}
	}
	
	protected void verifyTextField ( int length, String inputValue, String paramName ) throws QSException {
		if ( inputValue != null && inputValue.length ( ) > length )	{	
			throw new QSException ( ErrorCode.InvalidInput, 
					"'" + paramName + "' should be shorter than " + length + " characters" );
		}
	} 
}
