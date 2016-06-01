package modelo;

public class ObraArte extends Arte {
	
	private String obra;

	public ObraArte(String id, String museo, String autor, String siglo, String obra) {
		super(id, museo, autor, siglo);
		this.obra = obra;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		super.setId(id);
	}

	@Override
	public String getMuseo() {
		// TODO Auto-generated method stub
		return super.getMuseo();
	}

	@Override
	public void setMuseo(String museo) {
		// TODO Auto-generated method stub
		super.setMuseo(museo);
	}

	@Override
	public String getAutor() {
		// TODO Auto-generated method stub
		return super.getAutor();
	}

	@Override
	public void setAutor(String autor) {
		// TODO Auto-generated method stub
		super.setAutor(autor);
	}

	@Override
	public String getSiglo() {
		// TODO Auto-generated method stub
		return super.getSiglo();
	}

	@Override
	public void setSiglo(String siglo) {
		// TODO Auto-generated method stub
		super.setSiglo(siglo);
	}
	

	public String getObra() {
		return obra;
	}

	public void setObra(String obra) {
		this.obra = obra;
	}

	@Override
	public String toString() {
		
		return "Arte [id=" + getId() + ", museo=" + getMuseo() + ", autor=" + getAutor() + ", siglo=" + getSiglo() + ", obra= "+obra  +"]";
	}


	
}
