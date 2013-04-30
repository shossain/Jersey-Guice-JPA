package tas.queue.service.util;

import java.util.Date;

import tas.queue.service.exception.QSException;
import tas.queue.service.exception.QSException.ErrorCode;

public class Global {
	public static String generateUniqueName () {		
		return ( "" + Math.random() ).substring ( 2 );
	}	
	
	public static String createInvalidParametersMessage ( String firstParameter,
														  String ... otherParameters ) {
		String message = "Provide valid '" + firstParameter + "'";
		
		for ( String parameter : otherParameters ) {
			message += ", '" + parameter + "'";
		}
		return message;
	}
	
	public static Long convertStringToLong ( String paramName, 
											 String paramValue ) throws QSException {
		if ( paramValue == null ) {
			return null;
		}
		
		Long numericParamValue = null;
		try {
			numericParamValue = new Long ( paramValue );
		} catch ( Exception e ) {
			throw new QSException ( ErrorCode.InvalidInput, 
									createInvalidParametersMessage ( paramValue ) );
		}
		
		return numericParamValue;
	}
	
	public static long getCurrentTimeInSecs () {
		return new Date().getTime ( ) / 1000;
	}
}
