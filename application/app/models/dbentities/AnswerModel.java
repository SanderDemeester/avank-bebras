package models.dbentities;

/**
 * An interface for the different types of answer models
 * @author Ruben Taelman
 *
 */
public interface AnswerModel {
    /**
     * @return id
     */
    public int getID();
    /**
     * 
     * @return question id
     */
    public int getQuestionID();
    /**
     * 
     * @return question set id
     */
    public int getQuestionSetID();
    /**
     * 
     * @return the answer
     */
    public String getAnswer();
    /**
     * 
     * @return is a correct answer
     */
    public boolean isCorrect();
    /**
     * 
     * @return the language code
     */
    public String getLanguageCode();
    
    /**
     * 
     * @param question question model
     */
    public void setQuestion(QuestionModel question);
    /**
     * 
     * @param questionSet question set
     */
    public void setQuestionSet(QuestionSetModel questionSet);
    /**
     * 
     * @param answer the answer
     */
    public void setAnswer(String answer);
    /**
     * 
     * @param correct is correct
     */
    public void setCorrect(boolean correct);
    /**
     * 
     * @param languageCode the language code
     */
    public void setLanguageCode(String languageCode);
    
    /**
     * save the answer in the database
     */
    public void save();
}
