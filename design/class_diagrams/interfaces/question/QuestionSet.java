package question;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Ruben Taelman
 *
 */
public class QuestionSet{
	private Set<Question> questions;
	private boolean active;
	private Competition competition;
	private Grade grade;
	private Map<Question, Difficulty> difficulties;
	
	public List<Language> getLanguages() {
		return null;
	}
	
	public boolean canActivate() {
		return false;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Competition getCompetition() {
		return competition;
	}
	
	public void setCompetition(Competition competition) {
		this.competition = competition;
	}
	
	public Grade getGrade() {
		return grade;
	}
	
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	public Difficulty getDifficulty() {
		return null;
	}
	
	public void setDifficulty(Difficulty difficulty) {
		
	}

}
