package net.guilhermejr.apilivros.exception;

public class ExceptionPadrao extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExceptionPadrao(String mensagem){
        super(mensagem);
    }
	
}
