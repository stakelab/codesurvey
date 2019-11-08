package it.unimol.codesurvey.exceptions;


import java.lang.Exception;

public class PermissionException extends Exception {

	private static final long serialVersionUID = -7665581268972110249L;

	public PermissionException() {
		super("You do not have the rights to access this feature.");
	}

	public PermissionException(String pMessage) {
		super(pMessage);
	}
}