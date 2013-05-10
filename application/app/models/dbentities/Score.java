package models.dbentities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.validation.NotNull;

import play.Logger;
import play.db.ebean.Model;
/**
 * The score of a competition by a pupil
 * @author Ruben Taelman
 *
 */
@Entity
@Table(name="competitionScore")
public class Score extends Model{

    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    @NotNull
    @JoinColumn(name="uID")
    public UserModel user;
    
    @ManyToOne
    @NotNull
    @JoinColumn(name="qsID")
    public QuestionSetModel questionset;
    
    public int score;
    
    @Override
    public void save() {
        // If an old score exists, overwrite it
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uID", this.user.id);
        map.put("qsID", this.questionset.id);
        
        Score scoreModel = Ebean.find(Score.class).where().allEq(map).findUnique();
        if(scoreModel != null){
            //Ebean.delete(scoreModel); <--- This does not work, because ebean is very dumb, so we have to do it the nasty way, I'm crying blood as I write this...
            // Nasty stuff!!!
            Ebean.createSqlUpdate("delete from competitionScore where uID='"+scoreModel.user.id+"' and qsID="+scoreModel.questionset.id).execute();
        }
        super.save();
    }
}
