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
		 * palavra  join no  contexto  de  bancos  de dados SQL  é 
		 * um inner join.  Um  inner  join é o  tipo de join  mais 
		 * simples.
		 *
		 * Por exemplo, para se  recuperar  todos os  produtos que 
		 * possuem lances, é preciso utilizar um inner join. Neste 
		 * caso apenas produtos que possuem lances são recuperados. 
		 * Mas se desejarmos recuperar os produtos e valores nulos
		 * para os  dados dos  lances  quando o  produto não tiver 
		 * lances,  neste caso  utilizaremos um  left  outer join. 
		 * (estilo ANSI).
		 *
		 * Se fizermos a  junção de  duas tabelas PRODUTO e LANCE, 
		 * utilizando um inner join obteremos  todos os produtos e 
		 * seus lances na tabela resultante.  No caso de um  "left 
		 * outer join", cada  linha da  tabela a  esquerda (left - 
		 * tabela PRODUTO) que nunca satisfaz a condição de junção
		 * também  é  incluída  no  resultado  com  valores  nulos 
		 * retornados para todas as colunas da tabela LANCE.
		 * 
		 * Um "right outer join" recuperaria  todos os lances  com 
		 * um valor nulo para o produto se o lance não tem relação
		 * com nenhum produto.
		 * 
		 * A condição de  join deve ser  especificada na  cláusula 
		 * "on" para  uma  junção no  estilo "ANSI" ou na cláusula 
		 * "where" para uma junção no estilo "theta". 
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

			// A busca retorna um único produto (SingleResult()).

			/* 	Em função do método getSingleResult() será propagada
			 *  a exceção NoResultException caso nenhum produto seja
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