// @SOURCE:/home/sander/school/3e/VOPRO/avank-bebras/application/conf/routes
// @HASH:96cfb1819d3be4961a256ce6a4bb1399ff8eeb61
// @DATE:Mon Mar 11 10:47:11 CET 2013

import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString

object Routes extends Router.Routes {


// @LINE:6
val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart("/"))))
                    

// @LINE:9
val controllers_Assets_at1 = Route("GET", PathPattern(List(StaticPart("/assets/"),DynamicPart("file", """.+"""))))
                    

// @LINE:12
val controllers_Assets_at2 = Route("GET", PathPattern(List(StaticPart("/assets/img/glyphicons-halflings-white.png"))))
                    

// @LINE:13
val controllers_Assets_at3 = Route("GET", PathPattern(List(StaticPart("/assets/img/glyphicons-halflings.png"))))
                    
def documentation = List(("""GET""","""/""","""controllers.Application.index()"""),("""GET""","""/assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""),("""GET""","""/assets/img/glyphicons-halflings-white.png""","""controllers.Assets.at(path:String = "/public", file:String = "/images/bootstrap/glyphicons-halflings-white.png")"""),("""GET""","""/assets/img/glyphicons-halflings.png""","""controllers.Assets.at(path:String = "/public", file:String = "/images/bootstrap/glyphicons-halflings.png")"""))
             
    
def routes:PartialFunction[RequestHeader,Handler] = {        

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil))
   }
}
                    

// @LINE:9
case controllers_Assets_at1(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(_root_.controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String])))
   }
}
                    

// @LINE:12
case controllers_Assets_at2(params) => {
   call(Param[String]("path", Right("/public")), Param[String]("file", Right("/images/bootstrap/glyphicons-halflings-white.png"))) { (path, file) =>
        invokeHandler(_root_.controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String])))
   }
}
                    

// @LINE:13
case controllers_Assets_at3(params) => {
   call(Param[String]("path", Right("/public")), Param[String]("file", Right("/images/bootstrap/glyphicons-halflings.png"))) { (path, file) =>
        invokeHandler(_root_.controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String])))
   }
}
                    
}
    
}
                