package util.exceptions;

import java.sql.SQLException;

public class ServerOfflineException extends SQLException{
	
	public ServerOfflineException(String failureMessage) {
		super(failureMessage);
	}

}
