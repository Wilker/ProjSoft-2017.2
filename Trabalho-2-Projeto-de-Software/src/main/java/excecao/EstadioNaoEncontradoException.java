package excecao;

public class EstadioNaoEncontradoException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	public EstadioNaoEncontradoException()
	{	super();
	}

	public EstadioNaoEncontradoException(String msg)
	{	super(msg);
	}
}	