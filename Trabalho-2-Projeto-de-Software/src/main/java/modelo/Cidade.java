package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="CIDADE")

public class Cidade
{	
	private Long id;
	private String nome;
	

	//  Uma cidade possui estadios

	private List<Estadio> estadios = new ArrayList<Estadio>();
	
	// ********* Construtores *********

	public Cidade()
	{
	}

	public Cidade(String nome){
		this.nome = nome;
	}

	// ********* Métodos do Tipo Get *********

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	public Long getId()
	{	return id;
	}
	
	public String getNome()
	{	return nome;
	}
	
	// ********* Métodos do Tipo Set *********

	public void setId(Long id)
	{	this.id = id;
	}

	public void setNome(String nome)
	{	this.nome = nome;
	}
		
	// ********* Métodos para Associações *********

	/* 
	 * Com o atributo mappedBy. Sem ele a  JPA irá procurar  pela 
	 * tabela PRODUTO_LANCE. Com ele, ao se  tentar recuperar  um  
	 * produto  e  todos  os  seus  lances, o  join de PRODUTO  e 
	 * LANCE irá acontecer através da chave estrangeira existente
	 * em  LANCE.  Sem  ele  a  JPA  irá  procurar  pela   tabela 
	 * PRODUTO_LANCE.
	 */
	@OneToMany(mappedBy="cidade")
	@OrderBy
	public List<Estadio> getEstadios()
	{	return estadios;
	}
	
	public void setEstadios(List<Estadio> estadios)
	{	this.estadios = estadios;	
	}
}

