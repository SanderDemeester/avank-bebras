/**
 * 
 */
package controllers.classgroups;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.PersistenceException;

import models.classgroups.ClassGroupContainer;
import models.classgroups.ClassGroupContainer.PupilRecordTriplet;
import models.data.Link;
import models.user.AuthenticationManager;

import play.cache.Cache;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.classes.uploadclass;
import views.html.commons.noaccess;
import controllers.EController;
import controllers.util.XLSXImporter;

/**
 * @author Jens N. Rammant
 * TODO emessages & decent errors & comments
 */
public class ClassGroupIOController extends EController {

	/**
	 * 
	 * @return the Upload page
	 */
	public static Result upload(){
		return uploadExisting(null);
	}
	
	/**
	 * Parses the data and shows it
	 * @return a page with the parsed data
	 */
	public static Result post(){
		return postExisting(null);
	}
	
	@SuppressWarnings("unchecked")
	public static Result save(String id){
		return saveExisting(null,id);
	}
	
	public static Result uploadExisting(Integer id){
		System.out.println("got here");//TODO
		List<Link> bc = ClassGroupController.getBreadcrumbs();
		bc.add(new Link("Upload","/classes/upload"));
		if(!ClassGroupController.isAuthorized())return ok(noaccess.render(bc));		
		return ok(uploadclass.render(bc,null,null,id));
	}
	
	public static Result postExisting(Integer classID){
		List<Link> bc = ClassGroupController.getBreadcrumbs();
		bc.add(new Link("Upload","/classes/upload"));
		
		if(!ClassGroupController.isAuthorized())return ok(noaccess.render(bc));		
		
		  MultipartFormData body = request().body().asMultipartFormData();
		  FilePart xlsx = body.getFile("xlsx");
		  try {
			List<List<String>> list = XLSXImporter.read(xlsx.getFile());
			List<ClassGroupContainer> cg;
			if(classID==null){
				cg = ClassGroupIO.listToClassGroup(list);
			}else{
				ClassGroupContainer cgc = ClassGroupIO.listToClassGroup(list, classID);
				cg = new ArrayList<ClassGroupContainer>();
				cg.add(cgc);
			}
			String id = AuthenticationManager.getInstance().getUser().getID() +"-";
			Random r = new Random();
			id = id + Integer.toString(r.nextInt(10000));
			Cache.set(id, cg);			
			return ok(uploadclass.render(bc,cg,id,classID));
		} catch (Exception e) {
			return TODO;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Result saveExisting(Integer id, String dataid){
		List<Link> bc = ClassGroupController.getBreadcrumbs();
		bc.add(new Link("Upload","/classes/upload"));		
		if(!ClassGroupController.isAuthorized())return ok(noaccess.render(bc));	
		
		List<ClassGroupContainer> cgc = null;
		
		try{
			cgc = (List<ClassGroupContainer>) Cache.get(dataid);
			cgc.getClass(); //Throws exception when null;
		}catch(Exception e){
			//TODO
			return TODO;
		}
		try{
			ClassGroupContainer.save(cgc);
			if(id==null){
				return redirect(controllers.classgroups.routes.ClassGroupController.viewClasses(0, "name", "asc", ""));
			}else{
				return redirect(controllers.classgroups.routes.ClassPupilController.viewClass(id, 0, "name", "asc", ""));
			}
		}catch(PersistenceException pe){
			//TODO
			return TODO;
		}
	}
}
