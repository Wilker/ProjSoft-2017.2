package servico;

import java.util.List;

import modelo.Cidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dao.CidadeDAO;
import excecao.ObjetoNaoEncontradoException;
import excecao.CidadeNaoEncontradaException;

// @Service
public class CidadeAppService
{	
	@Autowired
	private CidadeDAO cidadeDAO;
	
	@Transactional
	public long inclui(Cidade umaCidade) 
	{	return cidadeDAO.inclui(umaCidade);
	}

	@Transactional
	public void altera(Cidade umaCidade)
		throws CidadeNaoEncontradaException
	{	try
		{	cidadeDAO.altera(umaCidade);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new CidadeNaoEncontradaException("Cidade não encontrada");
		}
	}

	@Transactional
	public void exclui(Cidade umaCidade) 
		throws CidadeNaoEncontradaException
	{	try
		{	
			Cidade cidade = cidadeDAO.recuperaUmaCidadeEEstadios(umaCidade.getId());
	
			if(cidade.getEstadios().size() > 0)
			{	throw new CidadeNaoEncontradaException("Esta cidade possui estadios e não pode ser removida");
			}
	
			cidadeDAO.exclui(cidade.getId());
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new CidadeNaoEncontradaException("Cidade não encontrada");
		}
	}

	public Cidade recuperaUmaCidade(long numero) 
		throws CidadeNaoEncontradaException
	{	try
		{	return cidadeDAO.recuperaUmaCidade(numero);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new CidadeNaoEncontradaException("Cidade não encontrada");
		}
	}

	public Cidade recuperaUmaCidadeEEstadios(long numero) 
		throws CidadeNaoEncontradaException
	{	try
		{	return cidadeDAO.recuperaUmaCidadeEEstadios(numero);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new CidadeNaoEncontradaException("Cidade não encontrada");
		}
	}

	public List<Cidade> recuperaCidadesEEstadios()
	{	return cidadeDAO.recuperaCidadesEEstadios();
	}
}