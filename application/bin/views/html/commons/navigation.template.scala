
package views.html.commons

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
object navigation extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template0[play.api.templates.Html] {

    /**/
    def apply():play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.1*/("""<div id="navigation" class="navbar">
	<div class="navbar-inner">
		<div class="container">
			<a class="brand" href='"""),_display_(Seq[Any](/*4.28*/routes/*4.34*/.Application.index)),format.raw/*4.52*/("""'>Bebras</a>
			<ul class="nav">
				<li class="active"><a href='"""),_display_(Seq[Any](/*6.34*/routes/*6.40*/.Application.index)),format.raw/*6.58*/("""'>Link1</a></li>
				<li><a href='"""),_display_(Seq[Any](/*7.19*/routes/*7.25*/.Application.index)),format.raw/*7.43*/("""'>Link2</a></li>
			</ul>
			
			<ul class="nav pull-right">
	            <li><a href='#'>Sign Up</a></li>
	            <li class="divider-vertical"></li>
	            <li class="dropdown">
		            <a class="dropdown-toggle" href="#" data-toggle="dropdown">Sign In <strong class="caret"></strong></a>
		            <div class="dropdown-menu" style="padding: 15px; padding-bottom: 0px;">
		              Fancy login form!
		            </div>
	            </li>
	        </ul>
			
		</div>
	</div>
</div>"""))}
    }
    
    def render() = apply()
    
    def f:(() => play.api.templates.Html) = () => apply()
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Mar 11 10:47:16 CET 2013
                    SOURCE: /home/sander/school/3e/VOPRO/avank-bebras/application/app/views/commons/navigation.scala.html
                    HASH: c0f2511b511443130f781e686a80556f3eaac6e4
                    MATRIX: 832->0|985->118|999->124|1038->142|1139->208|1153->214|1192->232|1262->267|1276->273|1315->291
                    LINES: 30->1|33->4|33->4|33->4|35->6|35->6|35->6|36->7|36->7|36->7
                    -- GENERATED --
                */
            