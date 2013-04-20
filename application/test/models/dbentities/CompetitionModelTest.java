package models.dbentities;

import com.avaje.ebean.Ebean;
import junit.framework.Assert;
import models.competition.CompetitionType;
import org.junit.Before;
import org.junit.Test;
import test.ContextTest;

import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;

/**
 * @author Kevin Stobbelaar
 */
public class CompetitionModelTest extends ContextTest {

    @Before
    public void clear(){
        List<CompetitionModel> competitionModels = Ebean.find(CompetitionModel.class).findList();
        for(CompetitionModel competitionModel : competitionModels) competitionModel.delete();
    }

    public void save(CompetitionModel competitionModel){
        try{
            competitionModel.save();
        } catch(PersistenceException p){
            Assert.fail("Something went wrong while saving.");
        }
    }

    public CompetitionModel getCompetitionModel(){
        CompetitionModel competitionModel = new CompetitionModel();
        competitionModel.id = "1";
        competitionModel.creator = "test";
        competitionModel.active = true;
        competitionModel.type = CompetitionType.UNRESTRICTED;
        competitionModel.name = "test";
        competitionModel.starttime = new Date();
        competitionModel.endtime = new Date();
        return competitionModel;
    }

    @Test
    public void testSave(){
        CompetitionModel competitionModel = getCompetitionModel();
        competitionModel.save();
        Assert.assertNotNull(Ebean.find(CompetitionModel.class).where().ieq("id", "1").findUnique());
    }

    @Test(expected=PersistenceException.class)
    public void duplicateTest(){
        CompetitionModel competitionModel1 = getCompetitionModel();
        CompetitionModel competitionModel2 = getCompetitionModel();
        competitionModel1.save();
        competitionModel2.save();
    }

}
