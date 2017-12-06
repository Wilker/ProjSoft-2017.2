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
		// A execução do método  recuperaUmProdutoComLock(id)  abaixo
		// impede  que  dois lances  sejam  cadastrados em  paralelo.
		// Como este  método põe  um lock em  Produto, a inserção  de 
		// dois  lances  acontece  sempre em  série, i. é, obedecendo
		// a uma fila. Isto impede que o valor do  segundo lance seja
		// inferior ao valor do primeiro ou que se tente cadastrar um
		// lance para um produto que tenha sido removido.
		
		Cidade umaCidade = umEstadio.getCidade();
		
		try
		{	cidadeDAO.recuperaUmaCidadeComLock(umaCidade.getId());
		}
		catch(ObjetoNaoEncontradoException e)
		{	
			throw new CidadeNaoEncontradaException("Cidade não encontada");
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
		{	throw new EstadioNaoEncontradoException("Estadio não encontrad.");
		}
	}

	public Estadio recuperaUmEstadio(long numero) 
		throws EstadioNaoEncontradoException
	{	try
		{	return estadioDAO.recuperaUmEstadio(numero);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new EstadioNaoEncontradoException("Estadio não encontrado");
		}
	}

	public List<Estadio> recuperaEstadios()
	{	return estadioDAO.recuperaEstadios();
	}
}