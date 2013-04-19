package models.dbentities;

import com.avaje.ebean.Ebean;
import models.competition.CompetitionType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.ContextTest;

import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;

/**
 * @author Kevin Stobbelaar.
 */
public class CompetitionModelTest extends ContextTest {

    @Before
    public void clear(){
        List<CompetitionModel> competitionModels = Ebean.find(CompetitionModel.class).findList();
        for(CompetitionModel competitionModel : competitionModels) competitionModel.delete();
    }

    public void saveCompetitionModel(CompetitionModel competitionModel){
        try{
            competitionModel.save();
        }catch(PersistenceException pe){
            Assert.fail(pe.toString());
        }
    }

    @Test
    public void save(){
        CompetitionModel competitionModel = new CompetitionModel();

        saveCompetitionModel(competitionModel);
        Assert.assertTrue(Ebean.find(FAQModel.class).where().findList().isEmpty());

        competitionModel.id = "1";
        competitionModel.name = "testcontest";
        competitionModel.creator = "test";
        competitionModel.starttime = new Date();
        competitionModel.endtime = new Date();
        competitionModel.active = true;
        competitionModel.type = CompetitionType.RESTRICTED;

        saveCompetitionModel(competitionModel);
        Assert.assertNotNull(Ebean.find(CompetitionModel.class).where().eq("id", "1").findUnique());
    }

}
