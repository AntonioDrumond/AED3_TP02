package modelo;

import entidades.Ator;
import registro.*;

import java.io.File;
import java.util.ArrayList;

public class ArquivoAtores extends Arquivo<Ator> {

  Arquivo<Ator> arqAtores;

  //índice do nome do ator
  ArvoreBMais<ParNomeId> indiceNome;

  //índice de relacionamento ator-série
  ArvoreBMais<ParIdId> indiceRelacaoSerieAtor;

  public ArquivoAtores() throws Exception {

    super("episodios", Ator.class.getConstructor());

    File directory = new File("./dados/ator");
    if (!directory.exists()) {
        directory.mkdirs(); // Create the directories if they don't exist
    }

    indiceNome = new ArvoreBMais<>(
    ParNomeId.class.getConstructor(), 5, "./dados/ator" + "/indiceNome.db");

    indiceRelacaoSerieAtor = new ArvoreBMais<>(
    ParIdId.class.getConstructor(), 5, "./dados/ator" + "/indiceRelacaoSerieAtor.db");

  }

  @Override
  public int create (Ator at) throws Exception {

    int id = super.create(at);

    indiceNome.create(new ParNomeId(at.getNomeAtor(), id));

    //adicionar indice de relacionamento
    indiceRelacaoSerieAtor.create(new ParIdId(at.getIDSerie(), id));

    return id;

  }

  public Ator[] readNome (String nome) throws Exception {

    if(nome.length() == 0) return null;

    ArrayList<ParNomeId> pares = indiceNome.read(new ParNomeId(nome, -1));

    if(!pares.isEmpty()){

      Ator[] atores = new Ator[pares.size()];

      int i = 0;

      for(ParNomeId par : pares){

        atores[i++] = read(par.getId());

      }

      return atores;

    } else {

      return null;
    } 

  }

  @Override
  public boolean delete (int id) throws Exception{

    Ator at = read(id);

    if (at != null){

      if(super.delete(id)){

        return indiceNome.delete(new ParNomeId(at.getNomeAtor(), id)) && indiceRelacaoSerieAtor.delete(new ParIdId(at.getIDSerie(), id));
      
      }

    }

    return false;

  }

  @Override
  public boolean update (Ator novoAtor) throws Exception {

    Ator at = read(novoAtor.getID());

    if(at != null){

      if(super.update(novoAtor)){

        if(!at.getNomeAtor().equals(novoAtor.getNomeAtor())){

          indiceNome.delete(new ParNomeId(at.getNomeAtor(), at.getID()));
          indiceNome.create(new ParNomeId(novoAtor.getNomeAtor(), novoAtor.getID()));

        }
        return true;
      }

    }
    return false;
  }

  //retorna todos os atores linkados a determinada serie
  public Ator[] readPorSerie(int idSerie) throws Exception {

    // Search in the B+ tree for all pairs matching (idSerie, -1)
    ArrayList<ParIdId> pares = indiceRelacaoSerieAtor.read(new ParIdId(idSerie, -1));

    if (pares.isEmpty()) {
      return null;
    }

    //criamos um vetor do tamanho do número de pares encontrados
    Ator[] atores = new Ator[pares.size()];
    int i = 0;

    //para cada par encontrado, buscamos o ator correspondente e guardamos no vetor
    for (ParIdId par : pares) {

      atores[i++] = read(par.getId2());
      
    }

    return atores;

  }

}