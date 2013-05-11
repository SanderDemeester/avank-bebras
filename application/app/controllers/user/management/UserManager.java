package controllers.user.management;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.dbentities.UserModel;
import models.management.FieldType;
import models.management.Manager;
import models.management.ModelState;
import play.mvc.Call;

import com.avaje.ebean.ExpressionList;

public class UserManager extends Manager<UserModel> {
	
	ExpressionList<UserModel> UMDataSet;

	public UserManager(ModelState state) {
		super(UserModel.class, state, "type", "type");
	}

	@Override
	public Call getAddRoute() {
		return routes.UserManagerController.createUser();
	}

	@Override
	public Call getEditRoute(String id) {
		return routes.UserManagerController.editUser(id);
	}

	@Override
	public Call getListRoute(int page, String orderBy, String order, String filter) {
		return routes.UserManagerController.showUsers(page, orderBy, order, filter);
	}

	@Override
	public String getMessagesPrefix() {
		return "users";
	}

	@Override
	public Call getRemoveRoute(String id) {
		return routes.UserManagerController.removeUser(id);
	}

	@Override
	public play.api.mvc.Call getSaveRoute() {
		return routes.UserManagerController.saveUser();
	}

	@Override
	public play.api.mvc.Call getUpdateRoute() {
		return routes.UserManagerController.updateUser();
	}
	
	@Override
    public List<String> getColumnHeaders() {
        List<String> headers = new ArrayList<String>();
        headers.add("id");
        for(String key : fields.keySet()) {
        	if(!key.equals("blocked")){
            	headers.add(key);	
        	}
        }
        return headers;
    }
	
	@Override
	public Map<String, FieldType> getFields() {
        Map<String, FieldType> newFields = new LinkedHashMap<String, FieldType>();
        for(String key : fields.keySet()) {
        	if(!(key.equals("blockeduntil") || key.equals("blocked"))){
        		newFields.put(key, fields.get(key));
        	}
        }
        return newFields;
	}
	
	@Override
	public Iterator<String> getFieldNames() {
		Set<String> keyset = fields.keySet();
		keyset.remove("blockeduntil");
		keyset.remove("blocked");
		return keyset.iterator();
	}
	
	@Override
	protected ExpressionList<UserModel> getDataSet(){
		return UMDataSet;
	}
	
	public void setDataSet(String userType){
		if(userType.equals("ADMINISTRATOR")){
			UMDataSet = getFinder().where();
		}else if(userType.equals("ORGANIZER")){
			ArrayList<String> typeList = new ArrayList<String>();
			typeList.add("TEACHER");
			typeList.add("AUTHOR");
			typeList.add("PUPIL_OR_INDEP");
			typeList.add("ANON");
			UMDataSet = this.getFinder().where().in("type",typeList);
		}
	}
}
