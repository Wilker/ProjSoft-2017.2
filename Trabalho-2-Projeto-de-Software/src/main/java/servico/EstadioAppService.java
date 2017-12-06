package servico;

import java.util.List;
import modelo.Estadio;
import modelo.Cidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import dao.EstadioDAO;
import dao.CidadeDAO;
import excecao.EstadioNaoEncontradoException;
import excecao.ObjetoNaoEncontradoException;
import excecao.CidadeNaoEncontradaException;


// @Service
public class EstadioAppService
{	
	@Autowired
	private CidadeDAO cidadeDAO;
	
	@Autowired
	private EstadioDAO estadioDAO;
	
	@Transactional
	public long inclui(Estadio umEstadio) 
		throws CidadeNaoEncontradaException
	{	
		// A execu��o do m�todo  recuperaUmProdutoComLock(id)  abaixo
		// impede  que  dois lances  sejam  cadastrados em  paralelo.
		// Como este  m�todo p�e  um lock em  Produto, a inser��o  de 
		// dois  lances  acontece  sempre em  s�rie, i. �, obedecendo
		// a uma fila. Isto impede que o valor do  segundo lance seja
		// inferior ao valor do primeiro ou que se tente cadastrar um
		// lance para um produto que tenha sido removido.
		
		Cidade umaCidade = umEstadio.getCidade();
		
		try
		{	cidadeDAO.recuperaUmaCidadeComLock(umaCidade.getId());
		}
		catch(ObjetoNaoEncontradoException e)
		{	
			throw new CidadeNaoEncontradaException("Cidade n�o encontada");
		}
			
		long numero = estadioDAO.inclui(umEstadio);

		return numero;
	}
	
	@Transactional
	public void exclui(Estadio umaCidade) 
		throws EstadioNaoEncontradoException 
	{	
		try
		{	
			estadioDAO.exclui(umaCidade);	
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new EstadioNaoEncontradoException("Estadio n�o encontrad.");
		}
	}

	public Estadio recuperaUmEstadio(long numero) 
		throws EstadioNaoEncontradoException
	{	try
		{	return estadioDAO.recuperaUmEstadio(numero);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new EstadioNaoEncontradoException("Estadio n�o encontrado");
		}
	}

	public List<Estadio> recuperaEstadios()
	{	return estadioDAO.recuperaEstadios();
	}
}