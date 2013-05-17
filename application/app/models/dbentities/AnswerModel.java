package models.dbentities;

/**
 * An interface for the different types of answer models
 * @author Ruben Taelman
 *
 */
public interface AnswerModel {
    public int getID();
    public int getQuestionID();
    public int getQuestionSetID();
    public String getAnswer();
    public boolean isCorrect();
    public String getLanguageCode();

    public void setQuestion(QuestionModel question);
    public void setQuestionSet(QuestionSetModel questionSet);
    public void setAnswer(String answer);
    public void setCorrect(boolean correct);
    public void setLanguageCode(String languageCode);

    public void save();
}
