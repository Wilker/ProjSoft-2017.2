package dao;

import java.util.List;

import modelo.Cidade;
import excecao.ObjetoNaoEncontradoException;

public interface CidadeDAO
{	
	public long inclui(Cidade umaCidade); 

	public void altera(Cidade umaCidade)
		throws ObjetoNaoEncontradoException; 
	
	public void exclui(long id) 
		throws ObjetoNaoEncontradoException; 
	
	public Cidade recuperaUmaCidade(long numero) 
		throws ObjetoNaoEncontradoException; 
	
	public Cidade recuperaUmaCidadeComLock(long numero) 
		throws ObjetoNaoEncontradoException; 
	
	public Cidade recuperaUmaCidadeEEstadios(long numero) 
		throws ObjetoNaoEncontradoException; 
	
	public List<Cidade> recuperaCidadesEEstadios();
}