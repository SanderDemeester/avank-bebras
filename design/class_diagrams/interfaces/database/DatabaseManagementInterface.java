
import datamodels.Admin;
import datamodels.Answer;
import datamodels.ClassGroup;
import datamodels.Competition;
import datamodels.CompetitionClass;
import datamodels.Independent;
import datamodels.Organizer;
import datamodels.OrganizerCompetition;
import datamodels.Question;
import datamodels.QuestionSet;
import datamodels.QuestionSetQuestion;
import datamodels.School;
import datamodels.Teacher;
import datamodels.TeacherCompetition;
import enums.UserType;
import java.util.Collection;
import queries.AnswerQuery;
import queries.ClassGroupQuery;
import queries.CompetitionClassQuery;
import queries.CompetitionQuery;
import queries.IndependentQuery;
import queries.OrganizerCompetitionQuery;
import queries.OrganizerQuery;
import queries.QuestionQuery;
import queries.QuestionSetQuery;
import queries.QuestionSetQuestionQuery;
import queries.SchoolQuery;
import queries.TeacherCompetitionQuery;
import queries.TeacherQuery;



/**
 *
 * @author Jens N. Rammant
 * 
 * Regarding the creation of new users: to be able to be inserted in the database, the ID of the user
 * has to be unique over ALL the users of ALL types
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
     * (These all return null if all could be added, or a Collection of the elements that could not be added)
     */
    public Collection<Teacher> addTeachers(Collection<Teacher> toAdd);
    public Collection<Independent> addIndependents(Collection<Independent> toAdd);
    public Collection<Organizer> addOrganizers(Collection<Organizer> toAdd);
    public Collection<Admin> addAdmins(Collection<Admin> toAdd);
    public Collection<ClassGroup> addClassGroups(Collection<ClassGroup> toAdd);
    public Collection<Competition> addCompetitions(Collection<Competition> toAdd);
    public Collection<Question> addQuestions(Collection<Question> toAdd);
    public Collection<QuestionSet> addQuestionSets(Collection<QuestionSet> toAdd);
    public Collection<School> addSchools(Collection<School> toAdd);
    
    
    /*
     * Alter
     * (These return true if there is an entry corresponding with the primary key. Else nothing is changed and false is returned)
     */
    public boolean changeTeacher(Teacher toChange);
    public boolean changeIndependent(Independent toChange);
    public boolean changeOrganizer(Organizer toChange);
    public boolean changeAdmin(Admin toChange);
    public boolean changeClassGroup(ClassGroup toChange);
    public boolean changeCompetition(Competition toChange);
    public boolean changeQuestion(Question toChange);
    public boolean changeQuestionSet(QuestionSet toChange);
    public boolean changeSchool(School toChange);
    
    
    /*
     * Linking (add, remove, get, alter)
     */
    public Collection<Answer> addAnswers(Collection<Answer> toAdd);
    public boolean removeAnswer(Answer toRemove);
    public Collection<Answer> getAnswers(AnswerQuery query);
    public boolean ChangeAnswer(Collection<Answer> toAdd);
    
    public Collection<CompetitionClass> addCompetitionClasses(Collection<CompetitionClass> toAdd);
    public boolean removeCompetitionClass(CompetitionClass toRemove);
    public Collection<CompetitionClass> getCompetitionClasses(CompetitionClassQuery query);
    
    public Collection<OrganizerCompetition> addOrganizerCompetitions(Collection<OrganizerCompetition> toAdd);
    public boolean removeOrganizerCompetition(OrganizerCompetition toRemove);
    public Collection<OrganizerCompetition> getOrganizerCompetitions(OrganizerCompetitionQuery query);
    
    public Collection<TeacherCompetition> addTeacherCompetitions(Collection<TeacherCompetition> toAdd);
    public boolean removeTeacherCompetition(TeacherCompetition toRemove);
    public Collection<TeacherCompetition> getTeacherCompetitions(TeacherCompetitionQuery query);
    
    public Collection<QuestionSetQuestion> addQuestionSetQuestions(Collection<QuestionSetQuestion> toAdd);
    public boolean removeQuestionSetQuestion(QuestionSetQuestion toRemove);
    public Collection<QuestionSetQuestion> getQuestionSetQuestions(QuestionSetQuestionQuery query);
    public boolean ChangeQuestionSetQuestion(Collection<QuestionSetQuestion> toAdd);
    
    /*
     * Application data
     */
    
    public UserType getUserType(String userID);
    public String getSalt(String userID);
    
    /*
     * Stuff related to dirty data
     */
    
    public CompetitionResult getCompetitionResult(String id);
    public String saveCompetitionResult(CompetitionResult toSave); //returns the ID
    public String overWriteCompetitionResult(CompetitionResult toSave, String ID); //returns null if overwritten, otherwise the new ID
    public void undirtyData(String ID); //saves the dirty data to the main database and removes it from the dirty database
    
    
    
}
