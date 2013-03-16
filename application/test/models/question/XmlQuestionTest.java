package models.question;

import java.util.List;

import models.data.Language;
import models.data.Languages;

import org.junit.Assert;
import org.junit.Test;

public class XmlQuestionTest {
    
    private static final String CORRECT_MC = "testincludes/correct_question_mc.xml";
    private static final String CORRECT_REGEX = "testincludes/correct_question_regex.xml";
    
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

}
