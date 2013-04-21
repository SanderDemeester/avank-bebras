/**
 * 
 */
package controllers.classgroups;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.classgroups.ClassGroupContainer;
import models.classgroups.ClassGroupContainer.PupilRecordTriplet;
import models.data.Link;

import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.classes.uploadclass;
import controllers.EController;
import controllers.util.XLSXImporter;

/**
 * @author Jens N. Rammant
 *
 */
public class ClassGroupIOController extends EController {

	public static Result upload(){
		//TODO
		return ok(uploadclass.render(new ArrayList<Link>()));
	}
	
	public static Result post(){
		  MultipartFormData body = request().body().asMultipartFormData();
		  FilePart xlsx = body.getFile("xlsx");
		  try {
			List<List<String>> list = XLSXImporter.read(xlsx.getFile());
			List<ClassGroupContainer> cg = ClassGroupIO.listToClassGroup(list);
			System.out.println(ClassGroupContainer.save(cg));
		} catch (IOException e) {
			return null;
		}
		  return TODO;
	}
}
