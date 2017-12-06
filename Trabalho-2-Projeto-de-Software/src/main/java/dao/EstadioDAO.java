package dao;

import java.util.List;

import modelo.Estadio;
import excecao.ObjetoNaoEncontradoException;

public interface EstadioDAO
{	
	public long inclui(Estadio umEstadio);

	public void exclui(Estadio umEstadio) 
		throws ObjetoNaoEncontradoException; 
	
	public Estadio recuperaUmEstadio(long numero) 
		throws ObjetoNaoEncontradoException; 
	
	public List<Estadio> recuperaEstadios();
	
}
