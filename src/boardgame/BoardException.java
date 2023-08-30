package boardgame;

public class BoardException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public BoardException(String msg) {//esse construtor vai repassar a msg ao construtor da super classe RunTimeException
		super(msg);
	}
}
