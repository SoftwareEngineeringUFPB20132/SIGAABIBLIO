package br.nti.SigaaBiblio.model;

public class LivroCambridge {

	private String edition;
	private String title;

	public LivroCambridge(String edition, String title) {
		super();
		this.edition = edition;
		this.title = title;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Titulo:" + title + "\n" + (!edition.isEmpty()? edition + " Edição": "");
	}

}
