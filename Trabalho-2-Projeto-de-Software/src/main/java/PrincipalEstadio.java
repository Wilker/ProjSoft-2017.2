import java.util.List;

import modelo.Estadio;
import modelo.Cidade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import servico.EstadioAppService;
import servico.CidadeAppService;
import corejava.Console;
import excecao.EstadioNaoEncontradoException;
import excecao.CidadeNaoEncontradaException;

public class PrincipalEstadio
{	public static void main (String[] args) 
	{	
		Cidade umaCidade;
		Estadio umEstadio;

        ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

		CidadeAppService cidadeAppService = 
			(CidadeAppService)fabrica.getBean ("cidadeAppService");
		EstadioAppService estadioAppService = 
			(EstadioAppService)fabrica.getBean ("estadioAppService");

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que voc� deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um estadio de uma cidade");
			System.out.println("2. Remover um estadio");
			System.out.println("3. Recuperar todos os estadios");
			System.out.println("4. Sair");
						
			int opcao = Console.readInt('\n' + 
							"Digite um n�mero entre 1 e 4:");			
					
			switch (opcao)
			{	case 1:
				{
					long idCidade = Console.
						readInt('\n' + "Informe o n�mero da cidade: ");
					
					try
					{	umaCidade = cidadeAppService.recuperaUmaCidade(idCidade);
					}
					catch(CidadeNaoEncontradaException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
					String nome = Console.readLine('\n' + 
							"Informe o nome do estadio: ");
				
					umEstadio = new Estadio(nome, umaCidade);

					try
					{	estadioAppService.inclui(umEstadio);	

						System.out.println('\n' + "Est�dio adicionado com sucesso");						
					}
					catch(CidadeNaoEncontradaException e) 
					{
						System.out.println(e.getMessage());
					}
						
					break;
				}

				case 2:
				{	int resposta = Console.readInt('\n' + 
						"Digite o n�mero do estadio que voc� deseja remover: ");
									
					try
					{	umEstadio = estadioAppService.recuperaUmEstadio(resposta);
					}
					catch(EstadioNaoEncontradoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"N�mero = " + umEstadio.getId() + 
						"    nome = " + umEstadio.getNome());
																						
					String resp = Console.readLine('\n' + 
						"Confirma a remo��o do est�dio?");

					if(resp.equals("s"))
					{	try
					{	estadioAppService.exclui (umEstadio);
							System.out.println('\n' + 
								"Est�dio removido com sucesso!");
						}
						catch(EstadioNaoEncontradoException e)
						{	System.out.println(e.getMessage());
						}
					}
					else
					{	System.out.println('\n' + 
							"Estadio n�o removido.");
					}
					
					break;
				}

				case 3:
				{	List<Estadio> arrayEstadios = estadioAppService.recuperaEstadios();
									
					if (arrayEstadios.size() == 0)
					{	System.out.println('\n' + 
							"Nao h� estadios cadastrados.");
						break;
					}
										
					System.out.println("");
					for (Estadio estadio : arrayEstadios)
					{	System.out.println(	
							"N�mero = " + estadio.getId() + 
							"  Nome = " + estadio.getNome());
					}
										
					break;
				}

				case 4:
				{	
					continua = false;
					((ConfigurableApplicationContext)fabrica).close();
					break;
				}

				default:
					System.out.println('\n' + "Op��o inv�lida!");						
			}
		}		
	}
}
