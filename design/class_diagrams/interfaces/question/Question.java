package question;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Ruben Taelman
 *
 */
public abstract class Question {
	protected QuestionType type;
	private List<Language> languages;
	private String ID;
	private Map<Language, String> titles;
	private boolean official;
	private boolean active;
	private Server server;
	
	protected Question() {
		
	}
	
	public static Question getFromXml(String xml) {
		return null;
	}
	
	public boolean isOfficial() {
		return official;
	}
	
	public void setOfficial(boolean official) {
		this.official = official;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Server getServer() {
		return server;
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	public QuestionType getType() {
		return type;
	}
	
	public List<Language> getLanguages() {
		return languages;
	}
	
	public String getID() {
		return ID;
	}
	
	public Map<Language, String> getTitles() {
		return titles;
	}
}
