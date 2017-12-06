package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import modelo.Cidade;

import org.springframework.stereotype.Repository;

import dao.CidadeDAO;
import excecao.ObjetoNaoEncontradoException;

@Repository
public class CidadeDAOImpl implements CidadeDAO
{	
	@PersistenceContext
	private EntityManager em;

    public long inclui(Cidade umaCidade) 
	{	
    	em.persist(umaCidade);
		return umaCidade.getId();
	}

	public void altera(Cidade umaCidade) 
		throws ObjetoNaoEncontradoException 
	{	
		Cidade produto = em.find(Cidade.class, umaCidade.getId(), LockModeType.PESSIMISTIC_WRITE);
		
		if(produto == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		em.merge(umaCidade);
	}

    public void exclui(long id) 
		throws ObjetoNaoEncontradoException 
	{	
		Cidade cidade = em.find(Cidade.class, id, LockModeType.PESSIMISTIC_WRITE);
		
		if(cidade == null)
		{	throw new ObjetoNaoEncontradoException();
		}
		
		em.remove(cidade);
	}

    public Cidade recuperaUmaCidade(long numero) 
		throws ObjetoNaoEncontradoException 
	{	
		Cidade umaCidade = (Cidade)em
			.find(Cidade.class, new Long(numero));
			
		if (umaCidade == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		return umaCidade;
	}

	public Cidade recuperaUmaCidadeComLock(long numero) 
		throws ObjetoNaoEncontradoException 
	{	
		Cidade umaCidade = (Cidade)em
			.find(Cidade.class, new Long(numero), LockModeType.PESSIMISTIC_WRITE);

		if (umaCidade == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		return umaCidade;
	}

	public Cidade recuperaUmaCidadeEEstadios(long numero)
		throws ObjetoNaoEncontradoException 
	{	
		/* O  que a  maioria das  pessoas pensam  quando escutam a 
		 * palavra  join no  contexto  de  bancos  de dados SQL  � 
		 * um inner join.  Um  inner  join � o  tipo de join  mais 
		 * simples.
		 *
		 * Por exemplo, para se  recuperar  todos os  produtos que 
		 * possuem lances, � preciso utilizar um inner join. Neste 
		 * caso apenas produtos que possuem lances s�o recuperados. 
		 * Mas se desejarmos recuperar os produtos e valores nulos
		 * para os  dados dos  lances  quando o  produto n�o tiver 
		 * lances,  neste caso  utilizaremos um  left  outer join. 
		 * (estilo ANSI).
		 *
		 * Se fizermos a  jun��o de  duas tabelas PRODUTO e LANCE, 
		 * utilizando um inner join obteremos  todos os produtos e 
		 * seus lances na tabela resultante.  No caso de um  "left 
		 * outer join", cada  linha da  tabela a  esquerda (left - 
		 * tabela PRODUTO) que nunca satisfaz a condi��o de jun��o
		 * tamb�m  �  inclu�da  no  resultado  com  valores  nulos 
		 * retornados para todas as colunas da tabela LANCE.
		 * 
		 * Um "right outer join" recuperaria  todos os lances  com 
		 * um valor nulo para o produto se o lance n�o tem rela��o
		 * com nenhum produto.
		 * 
		 * A condi��o de  join deve ser  especificada na  cl�usula 
		 * "on" para  uma  jun��o no  estilo "ANSI" ou na cl�usula 
		 * "where" para uma jun��o no estilo "theta". 
		 * 
		 * Exemplo: P.ID = L.PRODUTO_ID.
		 *
		 * Left Outer Join no Oracle:
		 *
		 * SELECT P.ID, P.NOME, L.ID, L.VALOR
		 * FROM PRODUTO P, LANCE L
		 * WHERE P.ID = L.PRODUTO_ID(+)
		 * ORDER BY P.ID, L.VALOR;
		 */

		try
		{	String busca = "select c from Cidade c " +
			               "left outer join fetch c.estadios e " +
			               "where c.id = :id";

			Cidade umaCidade =
				(Cidade) em.createQuery(busca)
						    .setParameter("id", numero)
						    .getSingleResult();

			// A busca retorna um �nico produto (SingleResult()).

			/* 	Em fun��o do m�todo getSingleResult() ser� propagada
			 *  a exce��o NoResultException caso nenhum produto seja
			 *  encontrado.
			 */

			return umaCidade;
		} 
		catch(NoResultException e)
		{	throw new ObjetoNaoEncontradoException();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Cidade> recuperaCidadesEEstadios()
	{	
		List<Cidade> cidades = em
			.createQuery("select distinct c from Cidade c " +
					     "left outer join fetch c.estadios e " +
					     "order by c.id asc")
			.getResultList();

		return cidades;
	}
}