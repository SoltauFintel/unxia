package unxia;

/**
 * Im Fehlerfall wird i.d.R. diese Exception geworfen.
 */
public class UnxiaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnxiaException(String msg) {
		super(msg);
	}

	public UnxiaException(String msg, Throwable e) {
		super(msg, e);
	}

	public UnxiaException(Throwable e) {
		super(e);
	}
}
