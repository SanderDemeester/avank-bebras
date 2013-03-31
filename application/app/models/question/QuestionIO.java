package models.question;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import models.user.UserID;

import org.codehaus.jackson.JsonNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import play.Play;
import play.libs.Json;

public class QuestionIO {
    
    private static final String XML_SCHEMA = "conf/questions.xsd";
    private static final Map<String, QuestionFactory> QUESTION_TYPE_NAMES = new HashMap<String, QuestionFactory>();
    static {
        QUESTION_TYPE_NAMES.put("multiple-choice-question", new MultipleChoiceQuestionFactory());
        QUESTION_TYPE_NAMES.put("regex-question", new RegexQuestionFactory());
    }
    
    /**
     * Validate a json formatted question
     * @param json json formatted question
     * @throws QuestionBuilderException any error that can occur
     * @return the question in the encoded json
     */
    public static Question validateJson(String json) throws QuestionBuilderException {
        JsonNode input = Json.parse(json);
        QuestionPack pack = jsonToQuestionPack(input);
        return getFromXml(pack.getXmlDocument());// The return is not catched because we only have to validate
    }
    
    /**
     * Export the  file encoded with json to a File
     * @param json json formatted question
     * @param userID the user id for the authenticated user
     * @return The compressed question file
     * @throws QuestionBuilderException any error that can occur
     */
    public static File export(String json, UserID userID, String userDownloadLocation) throws QuestionBuilderException {
        try {
            // Make hash
            MessageDigest mdEnc = MessageDigest.getInstance("MD5"); 
            mdEnc.update(json.getBytes(), 0, json.length());
            String hash = new BigInteger(1, mdEnc.digest()).toString(16) + userID.getUserID();
            
            JsonNode input = Json.parse(json);
            String downloadLocation = Play.application().configuration().getString("questioneditor.download");
            QuestionPack pack = jsonToQuestionPack(input, downloadLocation, hash, userDownloadLocation);
            getFromXml(pack.getXmlDocument());// The return is not catched because we only have to validate
            
            return pack.export(userID);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Internal server error.", e);
        }
    }
    
    /**
     * Creates a new question from a certain XML input
     * @param xml  absolute URL of an xml file
     * @return a new question
     * @throws QuestionBuilderException possible things that can go wrong
     */
    public static Question getFromXml(String xml) throws QuestionBuilderException {
        // TODO: Set server and ID upon loading the XML file
        Question question = null;
        try {
            // Parse the given XML into a DOM tree
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
    
            // Parse our file
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);
            
            return getFromXml(doc);
        } catch (ParserConfigurationException e) {
            throw new QuestionBuilderException("Incorrect XML, can't be parsed.");  
        } catch (SAXException e) {
            throw new QuestionBuilderException("The XML is invalid."+e.getMessage());
        } catch (IOException e) {
            throw new QuestionBuilderException("Can't read the xml file.");
        }
    }

    /**
     * Creates a new question from a certain XML input
     * @param xml  input stream of an xml file
     * @return a new question
     * @throws QuestionBuilderException possible things that can go wrong
     */
    public static Question getFromXml(Document doc) throws QuestionBuilderException {
        // TODO: Set Server and ID upon loading the XML file
        Question question = null;
        try {
            // create a SchemaFactory capable of understanding our schemas
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            // Load our schema
            Schema schema = sf.newSchema(new File(XML_SCHEMA));
            //factory.setSchema(schema); </-- DO NOT USE THIS, IT WILL ADD OPTIONAL ATTRS EVERYWHERE!

            // create a Validator instance to validate the give XML
            Validator validator = schema.newValidator();

            // Validate our document
            validator.validate(new DOMSource(doc));

            // Retrieve the root nodeList
            NodeList nodeList = doc.getChildNodes();
            nodeList = nodeList.item(0).getChildNodes();
            Node typeNode = nodeList.item(1);
            if(typeNode==null)
                typeNode = nodeList.item(0);
            String type = typeNode.getNodeName();

            // Give the nodeList to the correct QuestionFactory to make our Question
            question = QUESTION_TYPE_NAMES.get(type).newQuestion(typeNode);        
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
    
    public static QuestionPack jsonToQuestionPack(JsonNode json) throws QuestionBuilderException {
        return jsonToQuestionPack(json, null, null, null);
    }
    
    /**
     * Convert a json formatted question to an xml document
     * @param json json formatted question
     * @return a question in xml format
     * @throws QuestionBuilderException possible things that can go wrong
     */
    public static QuestionPack jsonToQuestionPack(JsonNode json, String location, String hash, String userDownloadLocation) throws QuestionBuilderException {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            
            QuestionPack pack = new QuestionPack(doc);
            if(location != null)
                pack.setTempDownloadLocation(location);
            if(hash != null)
                pack.setHash(hash);
            if(userDownloadLocation != null)
                pack.setUserDownloadLocation(userDownloadLocation);
            
            Element root = doc.createElementNS("bebras:Question", "root");
            
            //doc.createElement("root");
            //root.setAttributeNS("http://www.w3.org/2000/xmlns/" ,"xmlns","bebras:Question");
            
            QuestionType type = QuestionType.valueOf(json.get("type").asText());
            if(type == null)
                throw new QuestionBuilderException("Invalid question type.");
            Element questionNode = doc.createElementNS("bebras:Question", type.getXmlElement());
            
            JsonNode languages = json.get("languages");
            
            for(int i=0;i<languages.size();i++) {
                JsonNode language = languages.get(i);
                
                Element lang = doc.createElementNS("bebras:Question", "language");
                String langCode = language.get("language").getTextValue();
                lang.setAttribute("code", langCode);
                
                Element index = doc.createElementNS("bebras:Question", "index");
                if(location != null && hash != null)
                    index.setTextContent(pack.addIndex(langCode, language.get("index").getTextValue()));
                else
                    index.setTextContent("validating");
                lang.appendChild(index);
                
                Element feedback = doc.createElementNS("bebras:Question", "feedback");
                if(location != null && hash != null)
                    feedback.setTextContent(pack.addFeedback(langCode, language.get("feedback").getTextValue()));
                else
                    feedback.setTextContent("validating");
                lang.appendChild(feedback);
                
                Element title = doc.createElementNS("bebras:Question", "title");
                title.setTextContent(language.get("title").getTextValue());
                lang.appendChild(title);
                
                if(QuestionType.MULTIPLE_CHOICE.equals(type)) {
                    Element answers = doc.createElementNS("bebras:Question", "answers");
                    
                    JsonNode answerNodes = language.get("answers");
                    for(int j=0;j<answerNodes.size();j++) {
                        JsonNode answerNode = answerNodes.get(j);
                        
                        Element answer = doc.createElementNS("bebras:Question", "answer");
                        answer.setTextContent(answerNode.get("content").getTextValue());
                        if(answerNode.get("correct").asBoolean())
                            answer.setAttribute("correct", "true");
                        answers.appendChild(answer);
                    }
                    
                    lang.appendChild(answers);
                } else if(QuestionType.REGEX.equals(type)) {
                    Element input = doc.createElementNS("bebras:Question", "input");
                    input.setAttribute("regex", language.get("regex").getTextValue());
                    lang.appendChild(input);
                }
                
                questionNode.appendChild(lang);
            }
            
            root.appendChild(questionNode);
            doc.appendChild(root);
            
            return pack;
            
        } catch (ParserConfigurationException e) {
            throw new QuestionBuilderException("An unexpected internal error occured while parsing.");
        } catch (IOException e) {
            throw new QuestionBuilderException("An unexpected internal error occured while saving.");
        }
    }
    
    public static String getUserUploadLocation(UserID userID) {
        String rootLocation = Play.application().configuration().getString("questioneditor.upload");
        File rootDirectory = new File(rootLocation);
        if(!rootDirectory.exists())
            rootDirectory.mkdir();
        
        String location = Play.application().configuration().getString("questioneditor.upload")+"/"+userID.getUserID();
        File directory = new File(location);
        if(!directory.exists())
            directory.mkdir();
        return location;
    }
    
    public static File addTempFile(String location, String name) {
        //TODO: add this file to the daemon to be deleted after a certain time
        
        return new File(location, name);
    }
}
