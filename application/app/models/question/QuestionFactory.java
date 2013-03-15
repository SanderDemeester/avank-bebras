package models.question;

import java.util.HashMap;
import java.util.Map;

import models.data.Language;
import models.data.Languages;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Abstract class for Question factories
 * @author Ruben Taelman
 *
 */
public abstract class QuestionFactory<T extends Question> {
    
    // NodeActions that will be called when calling processCommonElements
    protected Map<String, NodeAction> nodeActions = new HashMap<String, NodeAction>();
    
    /**
     * A question will be generated based on a NodeList
     * @param nodeList a NodeList that contains the structure of questions
     * @return a new question based on contents of the NodeList
     */
    public abstract Question newQuestion(NodeList nodeList);
    
    /**
     * Creates a new QuestionFactory
     */
    public QuestionFactory() {
        this.nodeActions.put("index", new IndexNodeAction());
        this.nodeActions.put("feedback", new FeedbackNodeAction());
        this.nodeActions.put("title", new TitleNodeAction());
    }
    
    /**
     * Processes common elements of the question structure
     * @param question question to alter
     * @param nodeList starting nodeList
     */
    public void processCommonElements(T question, NodeList nodeList) {
        // loop over the languages in the structure
        Node rootNode = nodeList.item(1);
        NodeList languages = rootNode.getChildNodes();
        for(int i=0; i<languages.getLength();i++) {
            Node node=languages.item(i);
            // only read language elements
            if(node.getNodeName()=="language") {
                // add the language to the question
                String languageCode = node.getAttributes().getNamedItem("code").getNodeValue();
                Language language = Languages.getLanguage(languageCode);
                question.addLanguage(language);
                
                // process the child elements of the current language element
                NodeList languageElements = node.getChildNodes();
                for(int j=0; j<languageElements.getLength();j++) {
                    // get the NodeAction and execute it if one exists for the current Node
                    Node actionNode = languageElements.item(j);
                    NodeAction action = nodeActions.get(actionNode.getNodeName());
                    if(action!=null) {
                        action.processValue(question, language, actionNode.getNodeValue()
                                , actionNode.getChildNodes(), actionNode.getAttributes());
                    }
                }
            }
        }
    }
    
    /**
     * An action that can be called from Nodes when calling processCommonElements()
     */
    protected abstract class NodeAction {
        
        /**
         * Processes a value for a Node
         * @param question   the question to be altered
         * @param language   the current language
         * @param value      the value of this Node
         * @param nodeList   the subNodes of this Node
         * @param attributes the attributes of this Node
         */
        public abstract void processValue(T question, Language language, String value
                , NodeList nodeList, NamedNodeMap attributes);
        
    }
    
    /**
     * An NodeAction that sets the index page
     */
    protected class IndexNodeAction extends NodeAction{
        
        @Override
        public void processValue(T question, Language language, String value
                , NodeList nodeList, NamedNodeMap attributes) {
            question.setIndex(value, language);
        }
    }
    
    /**
     * An NodeAction that sets the feedback page
     */
    protected class FeedbackNodeAction extends NodeAction{
        
        @Override
        public void processValue(T question, Language language, String value
                , NodeList nodeList, NamedNodeMap attributes) {
            question.setFeedback(value, language);
        }
    }
    
    /**
     * An NodeAction that sets the title
     */
    protected class TitleNodeAction extends NodeAction{
        
        @Override
        public void processValue(T question, Language language, String value
                , NodeList nodeList, NamedNodeMap attributes) {
            question.setTitle(value, language);
        }
    }
}
