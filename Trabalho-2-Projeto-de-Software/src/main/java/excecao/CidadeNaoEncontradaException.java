package excecao;

public class CidadeNaoEncontradaException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	public CidadeNaoEncontradaException()
	{	super();
	}

	public CidadeNaoEncontradaException(String msg)
	{	super(msg);
	}
}	