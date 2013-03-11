// @SOURCE:/home/sander/school/3e/VOPRO/avank-bebras/application/conf/routes
// @HASH:96cfb1819d3be4961a256ce6a4bb1399ff8eeb61
// @DATE:Mon Mar 11 10:47:11 CET 2013

import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString


// @LINE:13
// @LINE:12
// @LINE:9
// @LINE:6
package controllers {

// @LINE:6
class ReverseApplication {
    


 
// @LINE:6
def index() = {
   Call("GET", "/")
}
                                                        

                      
    
}
                            

// @LINE:13
// @LINE:12
// @LINE:9
class ReverseAssets {
    


 
// @LINE:13
// @LINE:12
// @LINE:9
def at(file:String) = {
   (file) match {
// @LINE:9
case (file) if true => Call("GET", "/assets/" + implicitly[PathBindable[String]].unbind("file", file))
                                                                
// @LINE:12
case (file) if file == "/images/bootstrap/glyphicons-halflings-white.png" => Call("GET", "/assets/img/glyphicons-halflings-white.png")
                                                                
// @LINE:13
case (file) if file == "/images/bootstrap/glyphicons-halflings.png" => Call("GET", "/assets/img/glyphicons-halflings.png")
                                                                    
   }
}
                                                        

                      
    
}
                            
}
                    


// @LINE:13
// @LINE:12
// @LINE:9
// @LINE:6
package controllers.javascript {

// @LINE:6
class ReverseApplication {
    


 
// @LINE:6
def index = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"/"})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:13
// @LINE:12
// @LINE:9
class ReverseAssets {
    


 
// @LINE:13
// @LINE:12
// @LINE:9
def at = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      if (true) {
      return _wA({method:"GET", url:"/assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
      if (file == """ + implicitly[JavascriptLitteral[String]].to("/images/bootstrap/glyphicons-halflings-white.png") + """) {
      return _wA({method:"GET", url:"/assets/img/glyphicons-halflings-white.png"})
      }
      if (file == """ + implicitly[JavascriptLitteral[String]].to("/images/bootstrap/glyphicons-halflings.png") + """) {
      return _wA({method:"GET", url:"/assets/img/glyphicons-halflings.png"})
      }
      }
   """
)
                                                        

                      
    
}
                            
}
                    


// @LINE:13
// @LINE:12
// @LINE:9
// @LINE:6
package controllers.ref {

// @LINE:6
class ReverseApplication {
    


 
// @LINE:6
def index() = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq())
)
                              

                      
    
}
                            

// @LINE:13
// @LINE:12
// @LINE:9
class ReverseAssets {
    


 
// @LINE:9
def at(path:String, file:String) = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]))
)
                              

                      
    
}
                            
}
                    
                