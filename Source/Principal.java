import visao.*;

import java.util.Scanner;

import entidades.*;

import registro.Arquivo;

public class Principal 
{
    public static void main(String[] args) 
	{
        Scanner sc;
        Arquivo<Serie> arqSeries = null; 
        Arquivo<Episodio> arqEpisodios = null; 
		Arquivo<Ator> arqAtores = null;

        try 
		{   
            arqSeries = new Arquivo<>("series", Serie.class.getConstructor());
            arqEpisodios = new Arquivo<>("episodios", Episodio.class.getConstructor());
            arqAtores = new Arquivo<>("atores", Ator.class.getConstructor());

            sc = new Scanner(System.in);
            int opcao;
            do 
			{

            System.out.println("PUCFlix 1.0\n" +
                               "-----------\n" +
                               "> Inicio\n\n" +
                               "1) Series\n" + 
                               "2) Episodios\n" + 
                               "3) Atores\n" + 
                               "0) Sair\n");

                System.out.print("\nOpcao: ");
                try 
				{
                    opcao = Integer.valueOf(sc.nextLine());
                } 
				catch(NumberFormatException e) 
				{
                    opcao = -1;
                }

                switch (opcao) 
				{
                    case 1:
                        new MenuSeries().menu();
                    break;

                    case 2:
                        new MenuEpisodios().menu();
                    break;

                    case 3:
                        new MenuAtores().menu();
                    break;

                    case 0:
                    break;

                    default:
                        System.out.println("Opção inválida!");
                    break;
                }

            } while (opcao != 0);

        } 
		catch (Exception e) 
		{
            e.printStackTrace();

        }
    }

}
