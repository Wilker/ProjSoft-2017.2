package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ESTADIO")
public class Estadio
{	
	private Long id;
	private String nome;
	
	// Um lance se refere a um único produto

	private Cidade cidade;

	// ********* Construtores *********

	public Estadio()
	{
	}

	public Estadio(String nome, Cidade cidade){
		this.nome = nome;
		this.cidade = cidade;
	}

	// ********* Métodos do Tipo Get *********

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	public Long getId()
	{	return id;
	}

	@Column(nullable = false)
	public String getNome(){
		return nome;
	}
	
	// ********* Métodos do Tipo Set *********

	@SuppressWarnings("unused")
	private void setId(Long id)
	{	this.id = id;
	}

	public void setNome(String nome)
	{	this.nome = nome;
	}

	// ********* Métodos para Associações *********

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idcidade")
	public Cidade getCidade()
	{	return cidade;
	}
	
	public void setCidade(Cidade cidade)
	{	this.cidade = cidade;
	}
}	