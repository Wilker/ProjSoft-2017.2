
import java.util.List;

import modelo.Estadio;
import modelo.Cidade;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import servico.CidadeAppService;
import corejava.Console;
import excecao.CidadeNaoEncontradaException;

public class PrincipalCidade {
	public static void main(String[] args) {
		String nome;
		Cidade umaCidade;

		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

		CidadeAppService cidadeAppService = (CidadeAppService) fabrica.getBean("cidadeAppService");

		boolean continua = true;
		while (continua) {
			System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Cadastrar uma cidade");
			System.out.println("2. Alterar uma cidade");
			System.out.println("3. Remover uma cidade");
			System.out.println("4. Listar uma cidade e seus estádios");
			System.out.println("5. Listar todos as cidades e seus estadios");
			System.out.println("6. Sair");

			int opcao = Console.readInt('\n' + "Digite um número entre 1 e 6:");

			switch (opcao) {
			case 1: {
				nome = Console.readLine('\n' + "Informe o nome da cidade: ");

				Cidade cidade = new Cidade(nome);

				long numero = cidadeAppService.inclui(cidade);

				System.out.println('\n' + "Cidade número " + numero + " incluído com sucesso!");

				break;
			}

			case 2: {
				int resposta = Console.readInt('\n' + "Digite o número da cidade que você deseja alterar: ");

				try {
					umaCidade = cidadeAppService.recuperaUmaCidade(resposta);
				} catch (CidadeNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Número = " + umaCidade.getId() + "    Nome = " + umaCidade.getNome());

				System.out.println('\n' + "O que você deseja alterar?");
				System.out.println('\n' + "1. Nome");
				// System.out.println("2. Descrição");

				int opcaoAlteracao = Console.readInt('\n' + "Digite um número 1:");

				switch (opcaoAlteracao) {
				case 1:
					String novoNome = Console.readLine("Digite o novo nome: ");
					umaCidade.setNome(novoNome);

					try {
						cidadeAppService.altera(umaCidade);

						System.out.println('\n' + "Alteração de nome efetuada com sucesso!");
					} catch (CidadeNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;
				/*
				 * case 2: Talvez adicionar outra coluna 
				 * no banco pra poder modificar aqui
				 * break;
				 */

				default:
					System.out.println('\n' + "Opção inválida!");
				}

				break;
			}

			case 3: {
				int resposta = Console.readInt('\n' + "Digite o número da cidade que você deseja remover: ");

				try {
					umaCidade = cidadeAppService.recuperaUmaCidade(resposta);
				} catch (CidadeNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Número = " + umaCidade.getId() + "    Nome = " + umaCidade.getNome());

				String resp = Console.readLine('\n' + "Confirma a remoção da cidade?");

				if (resp.equals("s")) {
					try {
						cidadeAppService.exclui(umaCidade);
						System.out.println('\n' + "Cidade removida com sucesso!");
					} catch (CidadeNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}
				} else {
					System.out.println('\n' + "Cidade não removida.");
				}

				break;
			}

			case 4: {
				long numero = Console.readInt('\n' + "Informe o número da cidade: ");

				try {
					umaCidade = cidadeAppService.recuperaUmaCidadeEEstadios(numero);
				} catch (CidadeNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Id = " + umaCidade.getId() + "  Nome = " + umaCidade.getNome());

				List<Estadio> cidades = umaCidade.getEstadios();

				for (Estadio estadio : cidades) {
					System.out.println(
							'\n' + "      Estadio número = " + estadio.getId() + "  Nome = " + estadio.getNome());
				}

				break;
			}

			case 5: {
				List<Cidade> cidades = cidadeAppService.recuperaCidadesEEstadios();

				if (cidades.size() != 0) {
					System.out.println("");

					for (Cidade cidade : cidades) {
						System.out
								.println('\n' + "Cidade numero = " + cidade.getId() + "  Nome = " + cidade.getNome());

						List<Estadio> estadios = cidade.getEstadios();

						for (Estadio estadio : estadios) {
							System.out.println('\n' + "      Estádio número = " + estadio.getId() + "  Nome = "
									+ estadio.getNome());
						}
					}
				} else {
					System.out.println('\n' + "Não há cidades cadastradas com esta descrição.");
				}

				break;
			}

			case 6: {
				continua = false;
				((ConfigurableApplicationContext) fabrica).close();
				break;
			}

			default:
				System.out.println('\n' + "Opção inválida!");
			}
		}
	}
}
