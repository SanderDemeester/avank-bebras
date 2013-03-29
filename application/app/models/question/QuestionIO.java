package models.question;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.codehaus.jackson.JsonNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
        Document doc = JsonToXml(input);
        return getFromXml(doc);// The return is not catched because we only have to validate
    }
    
    /**
     * Export the  file encoded with json to a File
     * @param json json formatted question
     * @return The compressed question file
     * @throws QuestionBuilderException any error that can occur
     */
    public static File export(String json) throws QuestionBuilderException {
        JsonNode input = Json.parse(json);
        Document doc = JsonToXml(input);
        getFromXml(doc);// The return is not catched because we only have to validate
        try {
            // Make hash
            MessageDigest mdEnc = MessageDigest.getInstance("MD5"); 
            mdEnc.update(json.getBytes(), 0, json.length());
            String hash = new BigInteger(1, mdEnc.digest()).toString(16);
            
            String zipfile = hash+".zip";
            String xmlfile = hash+".xml";
            
            FileOutputStream fout = new FileOutputStream(zipfile);
            ZipOutputStream zout = new ZipOutputStream(fout);
            
            Source source = new DOMSource(doc);
            
            // Prepare the xml output file
            File file = new File(xmlfile);
            Result result = new StreamResult(file);
 
            // Write the DOM document to the file
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            xformer.setOutputProperty(OutputKeys.METHOD, "xml");
            xformer.setOutputProperty(OutputKeys.INDENT, "yes");
            xformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            xformer.transform(source, result);
            
            // Add xml file to zip
            ZipEntry ze = new ZipEntry("question.xml");
            zout.putNextEntry(ze);
            byte[] buffer = new byte[1024];
            FileInputStream in = new FileInputStream(xmlfile);
            int len;
            while ((len = in.read(buffer)) > 0) {
                zout.write(buffer, 0, len);
            }
 
            in.close();
            
            zout.closeEntry();
            
            zout.close();
            
            return new File(zipfile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Invalid file.", e);
        } catch (IOException e) {
            throw new RuntimeException("IO error.", e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException("Transformation server error.", e);
        } catch (TransformerFactoryConfigurationError e) {
            throw new RuntimeException("Transformation server error.", e);
        } catch (TransformerException e) {
            throw new RuntimeException("Transformation error.", e);
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
    
    public static Document JsonToXml(JsonNode json) throws QuestionBuilderException {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
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
                lang.setAttribute("code", language.get("language").getTextValue());
                
                Element index = doc.createElementNS("bebras:Question", "index");
                index.setTextContent(language.get("index").getTextValue());
                lang.appendChild(index);
                
                // TODO TMP
                Element feedback = doc.createElementNS("bebras:Question", "feedback");
                feedback.setTextContent(language.get("feedback").getTextValue());
                lang.appendChild(feedback);
                
                // TODO TMP
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
            
            return doc;
            
        } catch (ParserConfigurationException e) {
            throw new QuestionBuilderException("An unexpected internal error occured.");
        }
    }
}
