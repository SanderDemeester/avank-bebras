package models.question;

import java.util.HashMap;
import java.util.Map;

import models.data.Language;
import models.data.LanguageCreationException;

import org.w3c.dom.DOMException;
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

    private static final Map<QuestionType, QuestionFactory<? extends Question>>
        FACTORIES = new HashMap<QuestionType, QuestionFactory<? extends Question>>();
    static {
        FACTORIES.put(QuestionType.MULTIPLE_CHOICE, new MultipleChoiceQuestionFactory());
        FACTORIES.put(QuestionType.REGEX, new RegexQuestionFactory());
    }

    /**
     * A question will be generated based on a NodeList
     * @param nodeList a NodeList that contains the structure of questions
     * @return a new question based on contents of the NodeList
     */
    public abstract Question newQuestion(Node node) throws QuestionBuilderException;

    /**
     * A blank question will be generated based on a type
     * @param type the required question type
     * @return a new blank question of that type
     */
    public static Question newQuestion(QuestionType type) {
        return FACTORIES.get(type).newQuestion();
    }

    /**
     * A blank question will be generated
     * @return a new blank question
     */
    public abstract Question newQuestion();

    /**
     * Creates a new QuestionFactory
     */
    public QuestionFactory() {
        this.nodeActions.put("index", new IndexNodeAction());
        this.nodeActions.put("feedback", new FeedbackNodeAction());
        this.nodeActions.put("title", new TitleNodeAction());
    }

    /**
     * Helper method to solve the issue that JUnit has another definition of Node value
     * @param node
     * @return
     */
    public static String getNodeValue(Node node) {
        StringBuffer buf = new StringBuffer();
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node textChild = children.item(i);
            if (textChild.getNodeType() == Node.TEXT_NODE)
                buf.append(textChild.getNodeValue());
        }
        return buf.toString();
    }

    /**
     * Processes common elements of the question structure
     * @param question question to alter
     * @param nodeList starting nodeList
     * @throws QuestionBuilderException An exception has occurred
     */
    public void processCommonElements(T question, Node rootNode) throws QuestionBuilderException {
        // loop over the languages in the structure
        NodeList languages = rootNode.getChildNodes();
        for(int i=0; i<languages.getLength();i++) {
            Node node=languages.item(i);
            // only read language elements
            String languageCode;
            if(node.getNodeName()=="language") {
                // add the language to the question
                languageCode = node.getAttributes().getNamedItem("code").getNodeValue();
                try {
                    Language language = Language.getLanguage(languageCode);
                    question.addLanguage(language);

                    // process the child elements of the current language element
                    NodeList languageElements = node.getChildNodes();
                    for(int j=0; j<languageElements.getLength();j++) {
                        // get the NodeAction and execute it if one exists for the current Node
                        Node actionNode = languageElements.item(j);
                        NodeAction action = nodeActions.get(actionNode.getNodeName());
                        if(action!=null) {
                            action.processValue(question, language, getNodeValue(actionNode)
                                    , actionNode.getChildNodes(), actionNode.getAttributes());
                        }
                    }
                } catch(LanguageCreationException e) {
                    throw new QuestionBuilderException(e.getMessage());
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
         * @throws QuestionBuilderException An exception has occurred
         */
        public abstract void processValue(T question, Language language, String value
                , NodeList nodeList, NamedNodeMap attributes) throws QuestionBuilderException;

    }

    /**
     * An NodeAction that sets the index page
     */
    protected class IndexNodeAction extends NodeAction{

        @Override
        public void processValue(T question, Language language, String value
                , NodeList nodeList, NamedNodeMap attributes) throws QuestionBuilderException {
            question.setIndex(value, language);
        }
    }

    /**
     * An NodeAction that sets the feedback page
     */
    protected class FeedbackNodeAction extends NodeAction{

        @Override
        public void processValue(T question, Language language, String value
                , NodeList nodeList, NamedNodeMap attributes) throws QuestionBuilderException {
            question.setFeedback(value, language);
        }
    }

    /**
     * An NodeAction that sets the title
     */
    protected class TitleNodeAction extends NodeAction{

        @Override
        public void processValue(T question, Language language, String value
                , NodeList nodeList, NamedNodeMap attributes) throws QuestionBuilderException {
            question.setTitle(value, language);
        }
    }
}
