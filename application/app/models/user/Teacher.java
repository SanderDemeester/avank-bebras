
package models.user;

import java.awt.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;

import controllers.user.Type;
import play.mvc.Content;
import play.mvc.Result;
import views.html.landingPages.TeacherLandingPage;

/**
 * @author Sander Demeester
 * @author Jens N. Rammant
 */

@Entity
public class Teacher extends SuperUser{

    /**
     * The constructor of teacher.
     */
    public Teacher(UserID id, Type loginType, String name){
    	super(id,loginType,name);

    }

    public void scheduleUnrestrictedCompetition(){

    }

    /**
     * @param regex A regex for filtering.
     * Applys a seach filter for the teacher to Filter through all students in the System
     */
    public void searchStudents(String regex){
        //TODO: Need to add some filtering system
    }


    /**
     * @return A view to manageClassGroups.
     */
    public Result manageClasses(){
        return null;
    }

    /**
     * @return A view to manageCompetitions.
     */
    public Result manageCompetitions(){
        return null;
    }

	@Override
	public Result showLandingPage() {
		// TODO lots of stuff
		//return ok(TeacherLandingPage.render("Test", new ArrayList<ClassGroup>()));
		return null;
	}
	
	/*
	 * Creates the personalized landing page for this instance of Teacher.
	 * @return Personalized landing page for this instance of teacher
	 */
	public Content getLandingPage(){		
		java.util.List<ClassGroup> cc = new ArrayList<>();		
		java.util.List<ClassGroup> pc = new ArrayList<>();
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR, -12);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date today = c.getTime();
		System.out.println(today.toString());
		for (ClassGroup cl : this.getClasses()){
			System.out.println(cl.expdate.toString());
			if(cl.expdate.before(today))pc.add(cl);
			else cc.add(cl);
		}
		
		return TeacherLandingPage.render(this.id, cc, pc);
	}

	@Override
	public Result showStatistics() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * Queries the database for all Classes that this Teacher is main teacher of
	 * @return List of all ClassGroups this Teacher is main Teacher of
	 */
	public Collection<ClassGroup> getClasses(){
		
		java.util.List<ClassGroup> res = Ebean.find(ClassGroup.class).where()
				.eq("teacherid", this.id).findList();
		
		return res;
	}

}
