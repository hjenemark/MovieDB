package home.moviedb.mdb;

public class MovieDBEntry {
	private String title;
	private String director;
	private String lead;
	private String secondary;
	private int runtime;

	private String imdburl;
	//private int	id;
	private String comments;
		
	
	public MovieDBEntry() {
		super();
		this.setTitle(null);
		this.setDirector(null);
		this.setLead(null);
		this.setSecondary(null);
		this.setRuntime(-1);
		this.setImdburl(null);
		this.setComments(null);
		//this.id = id;
	}
/*
	public int getId() {
		return id;
	}*/
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getLead() {
		return lead;
	}
	public void setLead(String lead) {
		this.lead = lead;
	}
	public String getSecondary() {
		return secondary;
	}
	public void setSecondary(String secondary) {
		this.secondary = secondary;
	}
	public String getImdburl() {
		return imdburl;
	}
	public void setImdburl(String imdburl) {
		this.imdburl = imdburl;
	}
	
	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
