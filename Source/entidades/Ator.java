package entidades;
import registro.EntidadeArquivo;

import java.time.LocalDate;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Ator implements EntidadeArquivo 
{
	// Atributos

    private int ID;
    private int IDSerie;
    private String nomeAtor;
    private int idadeAtor;
	private boolean genero;

	// true = feminino, false = masculino
	

	// Construtores
    
    public Ator (int ID, int IDSerie, String nomeAtor, int idadeAtor, boolean genero)
	{
		this.ID = ID;
        this.IDSerie = IDSerie;
        this.nomeAtor = nomeAtor;
        this.idadeAtor = idadeAtor;
		this.genero = genero;
    }

	public Ator (int IDSerie, String nomeAtor, int idadeAtor, boolean genero)
	{
		this (-1, IDSerie, nomeAtor, idadeAtor, genero);
	}

	public Ator ()
	{
		this (-1, -1, "", -1, false);
	}

	// Métodos Get
	public int getID() {
		return this.ID;
	}

	public int getIDSerie() {
		return this.IDSerie;
	}

	public String getNomeAtor() {
		return this.nomeAtor;
	}

	public int getIdadeAtor() {
		return this.idadeAtor;
	}

	public boolean getGenero() {
		return this.genero;
	}

	// Métodos Set
	public void setID(int ID) {
		this.ID = ID;
	}

	public void setIDSerie(int IDSerie) {
		this.IDSerie = IDSerie;
	}

	public void setNomeAtor(String nomeAtor) {
		this.nomeAtor = nomeAtor;
	}

	public void setIdadeAtor(int idadeAtor) {
		this.idadeAtor = idadeAtor;
	}

	public void setGenero(boolean genero) {
		this.genero = genero;
	}

	// Metodos ByteArray

	public byte[] toByteArray () throws Exception
	{
		ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
		DataOutputStream DOS = new DataOutputStream (BAOS);

		DOS.writeInt (this.ID);
		DOS.writeInt (this.IDSerie);
		DOS.writeUTF (this.nomeAtor);
		DOS.writeInt (this.idadeAtor);
		DOS.writeBoolean(genero);

		return (BAOS.toByteArray());
	}

	public void fromByteArray (byte[] BA) throws Exception
	{
		if (BA == null)
		{
			throw new Exception ("ERRO: O vetor de bytes fornecido e null!");
		}
		else
		{
			ByteArrayInputStream BAIS = new ByteArrayInputStream (BA);
			DataInputStream DIS = new DataInputStream (BAIS);

			this.ID = DIS.readInt();
			this.IDSerie = DIS.readInt();
			this.nomeAtor = DIS.readUTF();
			this.idadeAtor = DIS.readInt();
			this.genero = DIS.readBoolean();
		}
	}
}
