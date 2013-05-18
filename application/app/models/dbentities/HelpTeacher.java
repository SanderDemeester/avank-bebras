package models.dbentities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.dbentities.HelpTeacher.HelpTeacherPK;

import play.db.ebean.Model;

/**
 * @author Jens N. Rammant
 * Class for the db linking of Teachers to Classes (as helpteacher)
 */
@Entity
@Table(name="helpteachers", uniqueConstraints=@UniqueConstraint(columnNames={"teacherid","classid"}))
@IdClass(HelpTeacherPK.class)
public class HelpTeacher extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID of the Teacher
     */
    @Id
    public String teacherid;
    /**
     * ID of the class
     */
    @Id
    public int classid;

    /**
     * @author Jens N. Rammant
     *
     */
    public class HelpTeacherPK implements Serializable{
        private static final long serialVersionUID = 1L;

        /**
         * ID of the teacher
         */
        public String teacherid;
        /**
         * ID of the class
         */
        public int classid;

        /**
         * Constructor
         * @param teacherid ID of the Teacher
         * @param classid ID of the class
         */
        public HelpTeacherPK(String teacherid, int classid) {
            this.teacherid = teacherid;
            this.classid = classid;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + classid;
            result = prime * result
                    + ((teacherid == null) ? 0 : teacherid.hashCode());
            return result;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (!(obj instanceof HelpTeacherPK))
                return false;
            HelpTeacherPK other = (HelpTeacherPK) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (classid != other.classid)
                return false;
            if (teacherid == null) {
                if (other.teacherid != null)
                    return false;
            } else if (!teacherid.equals(other.teacherid))
                return false;
            return true;
        }

        private HelpTeacher getOuterType() {
            return HelpTeacher.this;
        }

    }

}
