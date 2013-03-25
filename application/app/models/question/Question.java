package models.question;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import models.data.Language;
import models.data.Languages;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import models.user.User;

/**
 * The base class where questions for the competitions are stored in.
 * @author Ruben Taelman
 *
 */
public abstract class Question {

    /** These fields are generated on reading the XML **/
    protected String ID;
    protected Server server;
    protected QuestionType type;
    public List<Language> languages;
    protected Map<Language, String> titles;
    protected Map<Language, String> indexes;
    protected Map<Language, String> feedbacks;

    /** These fields can be altered afterwards **/
    public boolean official;
    public boolean active;
    public User author;

    /** Static fields **/
    private static final String XML_SCHEMA = "conf/questions.xsd";
    private static final Map<String, QuestionFactory> QUESTION_TYPE_NAMES = new HashMap<String, QuestionFactory>();
    static {
        QUESTION_TYPE_NAMES.put("multiple-choice-question", new MultipleChoiceQuestionFactory());
        QUESTION_TYPE_NAMES.put("regex-question", new RegexQuestionFactory());
    }

    /**
     * Don't just call this, Question need to be generated by calling the static getFromXml(String xml)
     */
    protected Question() {
        this.languages = new ArrayList<Language>();
        this.titles = new HashMap<Language, String>();
        this.indexes = new HashMap<Language, String>();
        this.feedbacks = new HashMap<Language, String>();
    }

    /**
     * Creates a new question from a certain XML input
     * @param xml  absolute URL of an xml file
     * @return a new question
     * @throws QuestionBuilderException possible things that can go wrong
     */
    public static Question getFromXml(String xml) throws QuestionBuilderException {
        // TODO: Set Server and ID upon loading the XML file
        Question question = null;
        try {
            // Parse the given XML into a DOM tree
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            // create a SchemaFactory capable of understanding our schemas
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            // Load our schema
            Schema schema = sf.newSchema(new File(XML_SCHEMA));
            //factory.setSchema(schema); </-- DO NOT USE THIS, IT WILL ADD OPTIONAL ATTRS EVERYWHERE!

            // Parse our file
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);

            // create a Validator instance to validate the give XML
            Validator validator = schema.newValidator();

            // Validate our document
            validator.validate(new DOMSource(doc));

            // Retrieve the root nodeList
            NodeList nodeList = doc.getChildNodes();
            nodeList = nodeList.item(0).getChildNodes();
            String type = nodeList.item(1).getNodeName();

            // Give the nodeList to the correct QuestionFactory to make our Question
            question = QUESTION_TYPE_NAMES.get(type).newQuestion(nodeList);

        } catch (ParserConfigurationException e) {
            throw new QuestionBuilderException("Incorrect XML, can't be parsed.");
        } catch (SAXException e) {
            throw new QuestionBuilderException("The XML is invalid."+e.getMessage());
        } catch (IOException e) {
            throw new QuestionBuilderException("Can't read the XML file.");
        /*} catch (XPathExpressionException e) {
            throw new QuestionBuilderException("Can't select the root node.");*/
        } catch (NullPointerException e) {
            throw new QuestionBuilderException("Unknown question type.");
        }

        return question;
    }

    /**
     * Is the question official
     * @return is official
     */
    public boolean isOfficial() {
        return official;
    }

    /**
     * Sets the official value
     * @param official is this question official
     */
    public void setOfficial(boolean official) {
        this.official = official;
    }

    /**
     * Is the question active
     * @return is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active value
     * @param active is this question active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the server on which this question is located
     * @return the server location of this question
     */
    public Server getServer() {
        return server;
    }

    /**
     * Sets the server on which this question is located
     * @param server the server location of this question
     */
    public void setServer(Server server) {
        this.server = server;
    }

    /**
     * Get the question type
     * @return the type of the question
     */
    public QuestionType getType() {
        return type;
    }

    /**
     * Add a possible language to the question
     * @param language a language
     */
    public void addLanguage(Language language) {
        this.languages.add(language);
    }

    /**
     * Returns a list of all available languages in this question
     * @return list of languages
     */
    public List<Language> getLanguages() {
        return languages;
    }
    
    /**
     * Returns a list of all languages that are not available in this question
     * @return list of languages
     */
    public List<Language> getNonLanguages() {
        List<Language> all = new ArrayList<Language>();
        for (Language language : Languages.LANGUAGES.values()) {
            if(!languages.contains(language))
                all.add(language);
        }
        return all;
    }

    /**
     * Returns the unique ID of this question on a certain server
     * @return the ID of a question
     */
    public String getID() {
        return ID;
    }

    /**
     * Returns the title in a certain Language
     * @param language chosen language
     * @return the title of this question in the given language
     */
    public String getTitle(Language language) {
        return this.titles.get(language);
    }

    /**
     * Sets the title for a given language
     * @param title the title for the question
     * @param language chosen language
     */
    public void setTitle(String title, Language language) {
        this.titles.put(language, title);
    }

    /**
     * Sets the index file name for a given language
     * @param title the index file name for the question
     * @param language chosen language
     */
    public void setIndex(String title, Language language) {
        this.indexes.put(language, title);
    }

    /**
     * Gets the index file name for a given language
     * @param language chosen language
     * @return the index file name for the question
     */
    public String getIndex(Language language) {
        return indexes.get(language);
    }

    /**
     * Sets the feedback file name for a given language
     * @param title the feedback file name for the question
     * @param language chosen language
     */
    public void setFeedback(String title, Language language) {
        this.feedbacks.put(language, title);
    }

    /**
     * Gets the feedback file name for a given language
     * @param language chosen language
     * @return the feedback file name for the question
     */
    public String getFeedback(Language language) {
        return feedbacks.get(language);
    }

}
