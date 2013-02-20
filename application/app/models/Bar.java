package models;

import play.db.ebean.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: sander
 * Date: 20-2-13
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Bar extends Model {

    @Id
    public String id;
    public String name;




}
