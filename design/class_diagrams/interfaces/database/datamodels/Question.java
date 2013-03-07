
package datamodels;

import enums.QuestionType;

/**
 *
 * @author Jens N. Rammant
 */
public class Question {
    private String id;
    private String officialID;
    private QuestionType type;
    
    private String dutchName;
    private String frenchName;
    private String germanName;
    
    private String dutchAnswer;
    private String frenchAnswer;
    private String germanAnswer;
    
    private String serverID;
    private String path;

    public Question(String id, String officialID, QuestionType type, String dutchName, String frenchName, String germanName, String dutchAnswer, String frenchAnswer, String germanAnswer, String serverID, String path) {
        this.id = id;
        this.officialID = officialID;
        this.type = type;
        this.dutchName = dutchName;
        this.frenchName = frenchName;
        this.germanName = germanName;
        this.dutchAnswer = dutchAnswer;
        this.frenchAnswer = frenchAnswer;
        this.germanAnswer = germanAnswer;
        this.serverID = serverID;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public String getOfficialID() {
        return officialID;
    }

    public QuestionType getType() {
        return type;
    }

    public String getDutchName() {
        return dutchName;
    }

    public String getFrenchName() {
        return frenchName;
    }

    public String getGermanName() {
        return germanName;
    }

    public String getDutchAnswer() {
        return dutchAnswer;
    }

    public String getFrenchAnswer() {
        return frenchAnswer;
    }

    public String getGermanAnswer() {
        return germanAnswer;
    }

    public String getServerID() {
        return serverID;
    }

    public String getPath() {
        return path;
    }
    
    
}
