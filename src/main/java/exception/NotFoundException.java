package exception;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = -7053834581688918244L;

	public NotFoundException() {
		super();
	}
	
	public NotFoundException(String msg) {
		super(msg);
	}
}
