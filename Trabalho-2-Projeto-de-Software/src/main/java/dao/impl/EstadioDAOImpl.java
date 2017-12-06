package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import modelo.Estadio;
import dao.EstadioDAO;
import excecao.ObjetoNaoEncontradoException;

@Repository
public class EstadioDAOImpl implements EstadioDAO
{	
	@PersistenceContext
	private EntityManager em;

    public long inclui(Estadio umEstadio) 
	{	em.persist(umEstadio);
		return umEstadio.getId();
	}

    public void exclui(Estadio umEstadio) 
		throws ObjetoNaoEncontradoException 
	{	
    	Estadio estadio = em.find(Estadio.class, new Long(umEstadio.getId()), LockModeType.PESSIMISTIC_WRITE);
    	
    	if (estadio == null)
    	{	throw new ObjetoNaoEncontradoException();	
		}
    	
		em.remove(estadio);
	}

    public Estadio recuperaUmEstadio(long numero) 
		throws ObjetoNaoEncontradoException 
	{	
		Estadio umEstadio = (Estadio)em.find(Estadio.class, new Long(numero));

		if(umEstadio == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		return umEstadio;
	}

	@SuppressWarnings("unchecked")
	public List<Estadio> recuperaEstadios()
	{	
		return em.createQuery("select l from Estadio l order by l.id")
				 .getResultList();
	}

}
