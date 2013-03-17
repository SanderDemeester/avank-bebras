package models.question;

import java.util.List;

import models.data.Language;
import models.data.Languages;

import org.junit.Assert;
import org.junit.Test;

public class XmlQuestionTest {
    
    private static final String CORRECT_MC = "testincludes/correct_question_mc.xml";
    private static final String CORRECT_REGEX = "testincludes/correct_question_regex.xml";
    
    private static final String INCORRECT_MC_1 = "testincludes/incorrect_question_mc_1.xml";
    private static final String INCORRECT_MC_2 = "testincludes/incorrect_question_mc_2.xml";
    private static final String INCORRECT_MC_3 = "testincludes/incorrect_question_mc_3.xml";
    private static final String INCORRECT_MC_4 = "testincludes/incorrect_question_mc_4.xml";
    private static final String INCORRECT_MC_5 = "testincludes/incorrect_question_mc_5.xml";
    private static final String INCORRECT_MC_6 = "testincludes/incorrect_question_mc_6.xml";
    private static final String INCORRECT_MC_7 = "testincludes/incorrect_question_mc_7.xml";
    private static final String INCORRECT_MC_8 = "testincludes/incorrect_question_mc_8.xml";
    private static final String INCORRECT_MC_9 = "testincludes/incorrect_question_mc_9.xml";
    private static final String INCORRECT_REGEX_1 = "testincludes/incorrect_question_regex_1.xml";
    private static final String INCORRECT_REGEX_2 = "testincludes/incorrect_question_regex_2.xml";
    
    private Question testAFile(String file) {
        Question q = null;
        try {
            q = Question.getFromXml(file);
        } catch (QuestionBuilderException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertNotNull(q);
        
        // Check the languages
        Assert.assertTrue(q.getLanguages().contains(Languages.getLanguage("en")));
        Assert.assertTrue(q.getLanguages().contains(Languages.getLanguage("nl")));
        
        // Check the titles
        Assert.assertEquals(q.getTitle(Languages.getLanguage("en")), "A Question");
        Assert.assertEquals(q.getTitle(Languages.getLanguage("nl")), "Een vraag");
        
        // Check the indexes
        Assert.assertEquals(q.getIndex(Languages.getLanguage("en")), "index_en.html");
        Assert.assertEquals(q.getIndex(Languages.getLanguage("nl")), "index_nl.html");
        
        // Check the feedback
        Assert.assertEquals(q.getFeedback(Languages.getLanguage("en")), "feedback_en.html");
        Assert.assertEquals(q.getFeedback(Languages.getLanguage("nl")), "feedback_nl.html");
        
        return q;
    }

    /**
     * Test if a correct MC Xml question file doesn't give errors
     */
    @Test
    public void correctMultipleChoiceFile() {
        MultipleChoiceQuestion q = (MultipleChoiceQuestion) testAFile(CORRECT_MC);
        Assert.assertEquals(q.getType(), QuestionType.MULTIPLE_CHOICE);
        
        // Check the answer contents
        List<MultipleChoiceElement> elements_en = q.getElements(Languages.getLanguage("en"));
        Assert.assertEquals(elements_en.get(0).getContent(), "Wrong");
        Assert.assertEquals(elements_en.get(1).getContent(), "Correct");
        Assert.assertEquals(elements_en.get(2).getContent(), "Wrong again");
        
        // Check the correct answer
        Assert.assertEquals(elements_en.get(1), q.getCorrectElement(Languages.getLanguage("en")));
        
        // Check the answer contents
        List<MultipleChoiceElement> elements_nl = q.getElements(Languages.getLanguage("nl"));
        Assert.assertEquals(elements_nl.get(0).getContent(), "Verkeerd");
        Assert.assertEquals(elements_nl.get(1).getContent(), "Juist");
        Assert.assertEquals(elements_nl.get(2).getContent(), "Weer verkeerd");
        
        // Check the correct answer
        Assert.assertEquals(elements_nl.get(1), q.getCorrectElement(Languages.getLanguage("nl")));
    }
    
    /**
     * Test if a correct Regex Xml question file doesn't give errors
     */
    @Test
    public void correctRegexFile() {
        RegexQuestion q = (RegexQuestion) testAFile(CORRECT_REGEX);
        Assert.assertEquals(q.getType(), QuestionType.REGEX);
        
        // Check regex contents
        Assert.assertEquals(q.getRegex(Languages.getLanguage("en")), "a wo+rd");
        Assert.assertEquals(q.getRegex(Languages.getLanguage("nl")), "een wo+rd");
    }
    
    /**
     * Test if an incorrect Multiple Choice Xml question file without a correct answer doesn't get allowed
     * @throws QuestionBuilderException The exception that is excepted to be thrown
     */
    @Test(expected=QuestionBuilderException.class)
    public void incorrectMultipleChoiceFile1() throws QuestionBuilderException {
        Question q = null;
        q = Question.getFromXml(INCORRECT_MC_1);
    }
    
    /**
     * Test if an incorrect Multiple Choice Xml question file without answers doesn't get allowed
     * @throws QuestionBuilderException The exception that is excepted to be thrown
     */
    @Test(expected=QuestionBuilderException.class)
    public void incorrectMultipleChoiceFile2() throws QuestionBuilderException {
        Question q = null;
        q = Question.getFromXml(INCORRECT_MC_2);
    }
    
    /**
     * Test if an incorrect Multiple Choice Xml question file with more than one correct answer doesn't get allowed
     * @throws QuestionBuilderException The exception that is excepted to be thrown
     */
    @Test(expected=QuestionBuilderException.class)
    public void incorrectMultipleChoiceFile3() throws QuestionBuilderException {
        Question q = null;
        q = Question.getFromXml(INCORRECT_MC_3);
    }
    
    /**
     * Test if an incorrect Multiple Choice Xml question file missing the index tag doesn't get allowed
     * @throws QuestionBuilderException The exception that is excepted to be thrown
     */
    @Test(expected=QuestionBuilderException.class)
    public void incorrectMultipleChoiceFile4() throws QuestionBuilderException {
        Question q = null;
        q = Question.getFromXml(INCORRECT_MC_4);
    }
    
    /**
     * Test if an incorrect Multiple Choice Xml question file missing the feedback tag doesn't get allowed
     * @throws QuestionBuilderException The exception that is excepted to be thrown
     */
    @Test(expected=QuestionBuilderException.class)
    public void incorrectMultipleChoiceFile5() throws QuestionBuilderException {
        Question q = null;
        q = Question.getFromXml(INCORRECT_MC_5);
    }
    
    /**
     * Test if an incorrect Multiple Choice Xml question file missing the title tag doesn't get allowed
     * @throws QuestionBuilderException The exception that is excepted to be thrown
     */
    @Test(expected=QuestionBuilderException.class)
    public void incorrectMultipleChoiceFile6() throws QuestionBuilderException {
        Question q = null;
        q = Question.getFromXml(INCORRECT_MC_6);
    }
    
    /**
     * Test if an incorrect Multiple Choice Xml question file missing the answer tag doesn't get allowed
     * @throws QuestionBuilderException The exception that is excepted to be thrown
     */
    @Test(expected=QuestionBuilderException.class)
    public void incorrectMultipleChoiceFile7() throws QuestionBuilderException {
        Question q = null;
        q = Question.getFromXml(INCORRECT_MC_7);
    }
    
    /**
     * Test if an incorrect Multiple Choice Xml question file with an incorrect language code doesn't get allowed
     * @throws QuestionBuilderException The exception that is excepted to be thrown
     */
    @Test(expected=QuestionBuilderException.class)
    public void incorrectMultipleChoiceFile8() throws QuestionBuilderException {
        Question q = null;
        q = Question.getFromXml(INCORRECT_MC_8);
    }
    
    /**
     * Test if an incorrect Multiple Choice Xml question file withhout the language code tag doesn't get allowed
     * @throws QuestionBuilderException The exception that is excepted to be thrown
     */
    @Test(expected=QuestionBuilderException.class)
    public void incorrectMultipleChoiceFile9() throws QuestionBuilderException {
        Question q = null;
        q = Question.getFromXml(INCORRECT_MC_9);
    }
    
    /**
     * Test if an incorrect Regex Xml question file without a regular expression input doesn't get allowed
     * @throws QuestionBuilderException The exception that is excepted to be thrown
     */
    @Test(expected=QuestionBuilderException.class)
    public void incorrectRegexFile1() throws QuestionBuilderException {
        Question q = null;
        q = Question.getFromXml(INCORRECT_REGEX_1);
    }
    
    /**
     * Test if an incorrect Regex Xml question file without an input doesn't get allowed
     * @throws QuestionBuilderException The exception that is excepted to be thrown
     */
    @Test(expected=QuestionBuilderException.class)
    public void incorrectRegexFile2() throws QuestionBuilderException {
        Question q = null;
        q = Question.getFromXml(INCORRECT_REGEX_2);
    }

}
