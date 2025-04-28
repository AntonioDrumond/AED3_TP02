package visao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import entidades.Episodio;
import entidades.Serie;
import entidades.Ator;
import modelo.ArquivoEpisodios;
import modelo.ArquivoSeries;
import modelo.ArquivoAtores;

public class MenuSeries 
{    
    ArquivoSeries arqSeries;
    ArquivoEpisodios arqEpisodios;
    ArquivoAtores arqAtores;

    private static Scanner console = new Scanner (System.in);

    public MenuSeries() throws Exception 
	{
        arqSeries = new ArquivoSeries();

        //para checar se tem episodios na serie antes de apaga-la
        arqEpisodios = new ArquivoEpisodios();

        arqAtores = new ArquivoAtores();
    }

    public void menu() 
	{
        int opcao;
        do 
		{
            System.out.println("\n\nAEDsIII");
            System.out.println("-------");
            System.out.println("> Inicio > Series");
            System.out.println("\n1 - Buscar");
            System.out.println("2 - Incluir");
            System.out.println("3 - Alterar");
            System.out.println("4 - Excluir");
            System.out.println("5 - Listar todos episodios da serie");
            System.out.println("6 - Listar episodios por temporada");
            System.out.println("7 - Listar todas as series cadastradas");
            System.out.println("8 - Listar todos os atores de uma serie");
            System.out.println("0 - Voltar");

            System.out.print("\nOpcao: ");
            try 
			{
                opcao = Integer.valueOf(console.nextLine());
            } 
			catch (NumberFormatException e) 
			{
                opcao = -1;
            }

            switch (opcao) 
			{
                case 1:
                    buscarSerie();
                    break;
                case 2:
                    incluirSerie();
                    break;
                case 3:
                    alterarSerie();
                    break;
                case 4:
                    excluirSerie();
                    break;
                case 5:
                    // Listar todos os episodios
                    listarEpisodiosPorSerie();
                    break;
                case 6:
                    // Listar episodios por temporada
                    listarEpisodiosPorTemporada();
                    break;
                case 7:
                    //Listar todas as series cadastradas
                    listarTodasSeries();
                    break;
                case 8:
                    // Listar todos os atores de uma serie
                    listarAtoresPorSerie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }

        } while (opcao != 0);
    }

    public void buscarSerie () 
	{
        System.out.println("\nBusca de serie");
        String nome;

		System.out.print("\nNome: ");
		nome = console.nextLine();  // Lê o Nome digitado pelo usuario

        try 
		{
            Serie[] series = arqSeries.readNome(nome);  // Chama o metodo de leitura da classe Arquivo
            
            if (series != null && series.length > 0) {

                for (Serie serie : series) {
                    mostraSerie(serie);  // Exibe os detalhes de cada serie encontrada
                }

            } else {

                System.out.println("Serie nao encontrada.");

            }
        } 
		catch (Exception e) 
		{
            System.out.println ("Erro do sistema. Nao foi possivel buscar o serie!");
            e.printStackTrace();
        }
    }

    public void listarEpisodiosPorSerie() {

        System.out.println("\nListagem de episodios");
        String nome;

		System.out.print("\nNome da serie: ");
		nome = console.nextLine();  // Lê o Nome digitado pelo usuario

        try {

            Serie[] series = arqSeries.readNome(nome);

            if (series == null || series.length == 0) {

                System.out.println("Serie nao encontrada.");
                return;

            }

            Serie s = series[0]; // Assuming the first match is the one we want
            System.out.println("Serie encontrada:");

            int idSerie = s.getID();  // Get the ID of the found series

            // Use the readPorSerie method to get all episodes linked to the series
            Episodio[] episodios = arqEpisodios.readPorSerie(idSerie);
    
            if (episodios == null || episodios.length == 0) {
                System.out.println("Nenhum episodio encontrado para esta serie.");
                return;
            }
    
            System.out.println("\nEpisodios da serie:");
            for (Episodio episodio : episodios) {
                System.out.println("----------------------------");
                System.out.println("Nome: " + episodio.getNome());
                System.out.println("Temporada: " + episodio.getTemporada());
                System.out.println("Duracao: " + episodio.getDuracao() + " minutos");
                System.out.println("Data de Lancamento: " + episodio.getLancamento());
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar episodios da serie!");
            e.printStackTrace();
        }
    }

    public void listarAtoresPorSerie() {

        System.out.println("\nListagem de atores por serie");
        String nome;
    
        System.out.print("\nNome da serie: ");
        nome = console.nextLine();  // Lê o Nome digitado pelo usuario
    
        try {
    
            Serie[] series = arqSeries.readNome(nome);
    
            if (series == null || series.length == 0) {
                System.out.println("Serie nao encontrada.");
                return;
            }
    
            Serie s = series[0]; //assuming the first match is the one we want
            System.out.println("Serie encontrada:");
            mostraSerie(s);
    
            int idSerie = s.getID();  //get the ID of the found series
    
            //use the readPorSerie method to get all actors linked to the series
            Ator[] atores = arqAtores.readPorSerie(idSerie);
    
            if (atores == null || atores.length == 0) {
                System.out.println("Nenhum ator encontrado para esta serie.");
                return;
            }
    
            System.out.println("\nAtores da serie:");
            for (Ator ator : atores) {
                System.out.println("----------------------------");
                System.out.println("Nome: " + ator.getNomeAtor());
                System.out.println("Idade: " + ator.getIdadeAtor());
                System.out.println("Genero: " + (ator.getGenero() ? "Feminino" : "Masculino"));
            }
    
        } catch (Exception e) {
            System.out.println("Erro ao listar atores da serie!");
            e.printStackTrace();
        }
    }

    public void listarEpisodiosPorTemporada() {

        System.out.println("\nListagem de episodios por temporada");
        String nomeSerie;
    
        // Ask the user for the series name
        System.out.print("\nNome da serie: ");
        nomeSerie = console.nextLine();
    
        try {
            // Retrieve the series by name
            Serie[] series = arqSeries.readNome(nomeSerie);
    
            if (series == null || series.length == 0) {
                System.out.println("Serie nao encontrada.");
                return;
            }
    
            Serie serie = series[0]; // Assuming the first match is the desired series
            System.out.println("Serie encontrada:");
            mostraSerie(serie);
    
            // Ask the user for the desired season
            System.out.print("\nDigite o número da temporada desejada: ");
            int temporadaDesejada = Integer.parseInt(console.nextLine());
    
            // Get all episodes of the series
            Episodio[] episodios = arqEpisodios.readPorSerie(serie.getID());
    
            if (episodios == null || episodios.length == 0) {
                System.out.println("Nenhum episodio encontrado para esta serie.");
                return;
            }
    
            // Filter episodes by the desired season
            System.out.println("\nEpisodios da temporada " + temporadaDesejada + ":");
            boolean encontrouEpisodios = false;
            for (Episodio episodio : episodios) {
                if (episodio.getTemporada() == temporadaDesejada) {
                    System.out.println("----------------------------");
                    System.out.println("Nome: " + episodio.getNome());
                    System.out.println("Temporada: " + episodio.getTemporada());
                    System.out.println("Duracao: " + episodio.getDuracao() + " minutos");
                    System.out.println("Data de Lancamento: " + episodio.getLancamento());
                    encontrouEpisodios = true;
                }
            }
    
            if (!encontrouEpisodios) {
                System.out.println("Nenhum episodio encontrado para a temporada " + temporadaDesejada + ".");
            }
    
        } catch (Exception e) {
            System.out.println("Erro ao listar episodios da temporada!");
            e.printStackTrace();
        }
    }

    public void incluirSerie () 
	{
        String nome = "";
		String sinopse = "";
		String streaming = "";
        LocalDate dataLancamento = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("\nInclusao de serie");
		
		// Ler nome
        do {
            System.out.print("\nNome (min. de 3 letras ou vazio para cancelar): ");
            nome = console.nextLine();
            if (nome.length() == 0) {
                return;
            }
            if (nome.length() < 3) {
                System.err.println("O nome da serie deve ter no minimo 3 caracteres.");
            }
        } while (nome.length() < 3);


        try {

            Serie[] series = arqSeries.readNome(nome); 

            if (series != null && series.length > 0) {
                System.err.println("Uma serie com esse nome ja existe");
                return;
            }
            else
            {
                // Ler sinopse
                do 
                {
                    System.out.print("Sinopse (no minimo 10 digitos): ");
    
                    sinopse = console.nextLine();
                    if (sinopse.length() < 10)
                    {
                        System.err.println ("A sinopse deve ter no minimo 10 digitos.");
                    }
    
                } while (sinopse.length() < 10);
    
                // Ler streaming
                do 
                {
                    System.out.print("Streaming: (no minimo 3 digitos): ");

                    streaming = console.nextLine();

                    if (streaming.length() < 3)
                    {
                        System.err.println ("O streaming deve ter no minimo 3 digitos.");
                    }
                    
    
                } while (streaming.length() < 3);
    
                // Ler data de lancamento
                boolean dadosCorretos = false;
                do 
                {
                    System.out.print("Data de lancamento (DD/MM/AAAA): ");
                    String dataStr = console.nextLine();
    
                    try 
                    {
                        dataLancamento = LocalDate.parse(dataStr, formatter);
                        dadosCorretos = true;
                    } 
                    catch (Exception e) 
                    {
                        System.err.println ("Data invalida! Use o formato DD/MM/AAAA.");
                    }
    
                } while (!dadosCorretos);
    
                // Confirmar inclusao
                System.out.print("\nConfirma a inclusao da serie? (S/N) ");
    
                char resp = console.nextLine().charAt(0);
    
                if (resp == 'S' || resp == 's') 
                {
                    try 
                    {
                        Serie s = new Serie (nome, dataLancamento, sinopse, streaming);
                        arqSeries.create (s);
                        System.out.println ("Serie incluida com sucesso.");
                    } 
                    catch (Exception e) 
                    {
                        System.out.println("Erro do sistema. Nao foi possivel incluir a serie!");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Erro do sistema. Nao foi possivel buscar a serie!");
            e.printStackTrace();
        }

        
    }

    public void alterarSerie() 
	{
        System.out.println("\nAlteracao de serie");
        String nome = "";
        boolean nomeValido = false;

        do 
		{
            System.out.print("\nNome (3 digitos): ");
            nome = console.nextLine();  // Lê o nome digitado pelo usuario

            if (nome.isEmpty())
			{
                return; 
			}

            // Validacao do nome
            if (nome.length() > 2) 
			{
                nomeValido = true;  // Nome valido
            } 
			else 
			{
                System.out.println("Nome invalido. O nome deve conter no minimo 3 digitos.");
            }

        } while (!nomeValido);

        try 
		{
            // Tenta ler a serie com o ID fornecido
            Serie[] s = arqSeries.readNome(nome);

            if (s == null || s.length == 0){
                return;
            }

            Serie serie = s[0];

            if (serie != null) 
			{
                System.out.println ("Serie encontrada:");
                mostraSerie(serie);  //exibe os dados do serie para confirmacao

                //checar se ha episodios vinculados a essa serie
                Episodio[] epVinculados = arqEpisodios.readPorSerie(serie.getID());

                //NOVO PASSO: Checar se ha atores vinculados a essa serie
                Ator[] atoresVinculados = arqAtores.readPorSerie(serie.getID());

                if ((epVinculados != null && epVinculados.length > 0) || (atoresVinculados != null && atoresVinculados.length > 0)) {

                    // Se houver episodios ou atores vinculados, nao permitir a exclusao
                    if (epVinculados != null && epVinculados.length > 0) {
                        System.out.println("Nao e possivel alterar o nome da serie pois existem episodios ligados a ela.");
                        System.out.println("Exclua primeiro todos os episodios dessa serie no menu EPISODIOS.");
                    }
                    
                    if (atoresVinculados != null && atoresVinculados.length > 0) {
                        System.out.println("Nao e possivel alterar o nome da serie pois existem atores ligados a ela.");
                        System.out.println("Exclua primeiro todos os atores dessa serie no menu ATORES.");
                    }

                }else{

                    //Alteracao de nome
                    System.out.print("\nNovo nome (deixe em branco para manter o anterior): ");
                    String novoNome = console.nextLine();

                    //NOVO PASSO: Checar se o novo nome ja está registrado
                    if (!novoNome.isEmpty()) 
                    {
                        // Verificar se o novo nome ja está registrado
                        Serie[] seriesComMesmoNome = arqSeries.readNome(novoNome);
                        if (seriesComMesmoNome != null && seriesComMesmoNome.length > 0) 
                        {
                            System.out.println("Erro: Ja existe uma serie registrada com este nome.");
                            return;
                        }

                        serie.setNome(novoNome);  // Atualiza o nome se fornecido
                    }
                }

                // Alteracao de sinopse
                System.out.print("Nova sinopse (deixe em branco para manter o anterior): ");
                String novaSinopse = console.nextLine();

                if (!novaSinopse.isEmpty()) 
				{
                    serie.setSinopse(novaSinopse);  
                }

                System.out.print("Novo streaming (deixe em branco para manter o anterior): ");
                String novoStreaming = console.nextLine();

                if (!novoStreaming.isEmpty()) 
				{
                    serie.setStreaming(novoStreaming);
                }

                // Alteracao de data de lancamento
                System.out.print("Nova data de lancamento (DD/MM/AAAA) (deixe em branco para manter a anterior): ");
                String novaDataLancamento = console.nextLine();

                if (!novaDataLancamento.isEmpty()) 
				{
                    try 
					{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        serie.setLancamento(LocalDate.parse(novaDataLancamento, formatter));
                    } 
					catch (Exception e) 
					{
                        System.err.println("Data invalida. Valor mantido.");
                    }
                }

                // Confirmacao da alteracao
                System.out.print("\nConfirma as alteracoes? (S/N) ");
                
                char resp = console.nextLine().charAt(0);

                if (resp == 'S' || resp == 's') 
				{
                    // Salva as alteracoes no arquivo
                    //System.out.println("Attempting to update the series...");
                    boolean alterado = arqSeries.update(serie);
                    //System.out.println("Update completed.");

                    if (alterado) 
					{
                        System.out.println("Serie alterada com sucesso.");
                    } 
					else 
					{
                        System.out.println("Erro ao alterar a serie.");
                    }
                } 
				else 
				{
                    System.out.println("Alteracoes canceladas.");
                }
            } 
			else 
			{
                System.out.println("Serie nao encontrada.");
            }

        } 
		catch (Exception e) 
		{
            System.out.println("Erro do sistema. Nao foi possivel alterar o serie!");
            e.printStackTrace();
        }
    }

    public void excluirSerie() {
        System.out.println("\nExclusao de serie");
        String nome;
        boolean nomeValido = false;

        do {
            System.out.print("\nNome (3 digitos): ");
            nome = console.nextLine();
            if (nome.isEmpty()) {
                return;
            }
            if (nome.length() > 2) {
                nomeValido = true;
            } else {
                System.out.println("Nome invalido. O nome deve conter no minimo 3 digitos.");
            }
        } while (!nomeValido);

        try {
            // Tenta ler a serie com o nome fornecido
            Serie[] s = arqSeries.readNome(nome);
            if (s == null || s.length == 0) {
                System.out.println("Serie nao encontrada.");
                return;
            }

            Serie serie = s[0];

            //Checar se ha episodios vinculados a essa serie
            Episodio[] epVinculados = arqEpisodios.readPorSerie(serie.getID());

            //NOVO PASSO: Checar se ha atores vinculados a essa serie
            Ator[] atoresVinculados = arqAtores.readPorSerie(serie.getID());

            if ((epVinculados != null && epVinculados.length > 0) || (atoresVinculados != null && atoresVinculados.length > 0)) {

                // Se houver episodios ou atores vinculados, nao permitir a exclusao
                if (epVinculados != null && epVinculados.length > 0) {
                    System.out.println("Nao e possivel excluir a serie pois existem episodios ligados a ela.");
                    System.out.println("Exclua primeiro todos os episodios dessa serie no menu EPISODIOS.");
                }

                if (atoresVinculados != null && atoresVinculados.length > 0) {
                    System.out.println("Nao e possivel excluir a serie pois existem atores ligados a ela.");
                    System.out.println("Exclua primeiro todos os atores dessa serie no menu ATORES.");
                }
                
                return;
            }

            System.out.println("Serie encontrada:");
            mostraSerie(serie);

            System.out.print("\nConfirma a exclusao da serie? (S/N) ");

            char resp = console.nextLine().charAt(0);

            if (resp == 'S' || resp == 's') {
                boolean excluido = arqSeries.delete(serie.getID());
                if (excluido) {
                    System.out.println("Serie excluida com sucesso.");
                } else {
                    System.out.println("Erro ao excluir a serie.");
                }
            } else {
                System.out.println("Exclusao cancelada.");
            }

        } catch (Exception e) {
            System.out.println("Erro do sistema. Nao foi possivel excluir o serie!");
            e.printStackTrace();
        }
    }

    public void mostraSerie (Serie serie) 
	{
        if (serie != null) 
		{
            System.out.println ("\nDetalhes da Serie:");
            System.out.println ("----------------------");
            System.out.printf  ("Nome.........: %s\n", serie.getNome());
            System.out.printf  ("ID...........: %d\n", serie.getID());
            System.out.printf  ("Streaming....: %s\n", serie.getStreaming());
            System.out.printf  ("Sinopse......: %s\n", serie.getSinopse());
            System.out.printf  ("Lancamento: %s\n", serie.getLancamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println ("----------------------");
        }
    }

    public void listarTodasSeries() {
        System.out.println("\nListagem de todas as series:");

        try {
            // Use the generic readAll method to get all series
            List<Serie> series = arqSeries.readAll();

            if (series.isEmpty()) {
                System.out.println("Nenhuma serie encontrada.");
                return;
            }

            for (Serie serie : series) {
                mostraSerie(serie); // Display each series
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar todas as series!");
            e.printStackTrace();
        }
    }
}
