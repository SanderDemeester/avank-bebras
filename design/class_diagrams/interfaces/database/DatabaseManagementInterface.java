
import datamodels.Admin;
import datamodels.ClassGroup;
import datamodels.Competition;
import datamodels.Independent;
import datamodels.Organizer;
import datamodels.Question;
import datamodels.QuestionSet;
import datamodels.School;
import datamodels.Teacher;
import java.util.Collection;
import queries.ClassGroupQuery;
import queries.CompetitionQuery;
import queries.IndependentQuery;
import queries.OrganizerQuery;
import queries.QuestionQuery;
import queries.QuestionSetQuery;
import queries.SchoolQuery;
import queries.TeacherQuery;



/**
 *
 * @author Jens N. Rammant
 */
public interface DatabaseManagementInterface {
    
    /*
     * Queries
     */
    public Collection<Teacher> getTeachers(TeacherQuery query);
    public Collection<Independent> getIndependents(IndependentQuery query);
    public Collection<Organizer> getOrganizers(OrganizerQuery query);
    public Collection<Admin> getAdmins();
    public Collection<ClassGroup> getClassGroups(ClassGroupQuery query);
    public Collection<Competition> getCompetitions(CompetitionQuery query);
    public Collection<Question> getQuestions(QuestionQuery query);
    public Collection<QuestionSet> getQuestionSets(QuestionSetQuery query);
    public Collection<School> getSchools(SchoolQuery query);
    
    /*
     * Inserts
     */
    //TODO
}
