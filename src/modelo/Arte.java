/**
 * 
 */
package modelo;

/**
 * @author JGFM
 *
 */
public class Arte {

	//Atributos
	
	private String id;
	private String museo;
	//private String obra;
	private String autor;
	private String siglo;
	
	
	/**
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return museo
	 */
	
	public String getMuseo() {
		return museo;
	}
	
	/**
	 * 
	 * @param museo
	 */
	public void setMuseo(String museo) {
		this.museo = museo;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getAutor() {
		return autor;
	}
	/**
	 * 
	 * @param autor
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}
	/**
	 * 
	 * @return
	 */
	public String getSiglo() {
		return siglo;
	}
	
	/**
	 * 
	 * @param siglo
	 */
	public void setSiglo(String siglo) {
		this.siglo = siglo;
	}
	
	
	/**
	 * 
	 * @param id
	 * @param museo
	 * @param autor
	 * @param siglo
	 */
	
	public Arte(String id, String museo, String autor, String siglo) {
		super();
		this.id = id;
		this.museo = museo;
		this.autor = autor;
		this.siglo = siglo;
	}
/**
 * 
 */
	@Override
	public String toString() {
		return "Arte [id=" + id + ", museo=" + museo + ", autor=" + autor + ", siglo=" + siglo + "]";
	}
	
	
	
	
	
}
