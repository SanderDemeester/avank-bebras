package models.competition;


import java.util.Date;
import java.util.Map.Entry;

import models.data.Language;
import models.dbentities.AnswerModel;
import models.dbentities.AnswerModelGenerator;
import models.dbentities.Score;
import models.question.Answer;
import models.question.AnswerGeneratorException;
import models.question.Question;
import models.question.QuestionFeedback;
import models.question.QuestionFeedbackGenerator;
import models.question.QuestionSet;
import models.user.User;

import org.codehaus.jackson.JsonNode;

import play.Logger;
import play.libs.Json;

/**
 * Competition user state class.
 *
 * @author Ruben Taelman
 * @author Kevin Stobbelaar
 */

public class CompetitionUserState {

    private Date startTime;
    private Date endTime;
    private QuestionFeedback feedback;
    private User user;
    private QuestionSet questionSet;
    private String languageCode;

    /**
     * Constructor
     * @param user corresponding user
     * @param questionSet corresponding competition
     * @param languageCode the language of the competition
     */
    public CompetitionUserState(User user, QuestionSet questionSet, String languageCode){
        this.feedback = null;
        this.startTime = new Date();
        this.user = user;
        this.questionSet = questionSet;
        this.languageCode = languageCode;
    }

    /**
     * Updates the current competition user state by setting the feedback
     * @param Feedback
     */
    public void setResults(QuestionFeedback feedback){
        this.feedback = feedback;
        this.endTime = new Date();
    }

    /**
     * Updates the current competition user state by setting the json encoded input
     * @param json answers by the user encoded in json
     * @param language the language the question were answered in
     * @throws AnswerGeneratorException if something went wrong while parsing the answers
     */
    public void setResults(String json, Language language) throws AnswerGeneratorException {
        JsonNode input = Json.parse(json);
        QuestionFeedback feedback = QuestionFeedbackGenerator.generateFromJson(input, language);
        setResults(feedback);
    }

    /**
     * Returns the feedback of this state
     * @return the feedback of the answers, will return null if the setResults(...) wasn't called
     * successfuly.
     */
    public QuestionFeedback getFeedback() {
        return this.feedback;
    }

    /**
     * Save the answers and score for this competition user state in the database
     */
    public void save() {
        if(feedback != null) {
            for(Entry<Question, Answer> entry : feedback.getAnswers().entrySet()) {
                AnswerModel answer = AnswerModelGenerator.make(user);
                Answer a = entry.getValue();
                answer.setAnswer(a.getTextValue());
                answer.setCorrect(a.isCorrect());
                answer.setLanguageCode(languageCode);
                answer.setQuestion(entry.getKey().getData());
                answer.setQuestionSet(questionSet.getData());
                answer.save();
            }

            if(!user.isAnon()) {
                Logger.debug("savd");
                Score score = new Score();
                score.questionset = questionSet.getData();
                score.user = user.data;
                score.score = feedback.getScore();
                score.save();
            }
        }
    }

    /**
     * Checks if this state has a feedback and therefore has already been finished
     * @return if this user is ready with the competition
     */
    public boolean isFinished() {
        return this.feedback != null;
    }

}
