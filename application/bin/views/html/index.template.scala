
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.api.templates.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import com.avaje.ebean._
import play.mvc.Http.Context.Implicit._
import views.html._
/**/
object index extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(message: String):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.19*/("""

"""),_display_(Seq[Any](/*3.2*/main("Welcome to Bebras")/*3.27*/ {_display_(Seq[Any](format.raw/*3.29*/("""
    
    <p class="lead">Some fancy intro.</p>
	<p>"""),_display_(Seq[Any](/*6.6*/message)),format.raw/*6.13*/("""</p>
	<p>A wild progressbar appeared!</p>
	<div class="progress progress-striped active">
	  <div class="bar" style="width: 20%;"></div>
	</div>
""")))})),format.raw/*11.2*/("""
"""))}
    }
    
    def render(message:String) = apply(message)
    
    def f:((String) => play.api.templates.Html) = (message) => apply(message)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Mar 11 10:47:16 CET 2013
                    SOURCE: /home/sander/school/3e/VOPRO/avank-bebras/application/app/views/index.scala.html
                    HASH: f62fe6ce471d57951f4f104aec4717026021f730
                    MATRIX: 755->1|849->18|886->21|919->46|958->48|1045->101|1073->108|1250->254
                    LINES: 27->1|30->1|32->3|32->3|32->3|35->6|35->6|40->11
                    -- GENERATED --
                */
            