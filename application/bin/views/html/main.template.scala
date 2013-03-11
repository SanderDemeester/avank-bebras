
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
object main extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[String,Html,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(title: String)(content: Html):play.api.templates.Html = {
        _display_ {import commons._


Seq[Any](format.raw/*1.32*/("""
"""),format.raw/*3.1*/("""
<!DOCTYPE html>

<html>
    <head>
        <title>"""),_display_(Seq[Any](/*8.17*/title)),format.raw/*8.22*/("""</title>
        <link rel="shortcut icon" type="image/png" href=""""),_display_(Seq[Any](/*9.59*/routes/*9.65*/.Assets.at("images/favicon.png"))),format.raw/*9.97*/("""">
        <link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*10.54*/routes/*10.60*/.Assets.at("stylesheets/bootstrap/bootstrap.min.css"))),format.raw/*10.113*/("""" />
		<link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*11.48*/routes/*11.54*/.Assets.at("stylesheets/main.css"))),format.raw/*11.88*/("""">
		<link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*12.48*/routes/*12.54*/.Assets.at("stylesheets/bootstrap/responsive.min.css"))),format.raw/*12.108*/("""" />
        <script src=""""),_display_(Seq[Any](/*13.23*/routes/*13.29*/.Assets.at("javascripts/jquery-1.7.1.min.js"))),format.raw/*13.74*/("""" type="text/javascript"></script>
        <script src=""""),_display_(Seq[Any](/*14.23*/routes/*14.29*/.Assets.at("javascripts/bootstrap/bootstrap.min.js"))),format.raw/*14.81*/("""" type="text/javascript"></script>
    </head>
    <body>
    	<div id="wrap">
		    """),_display_(Seq[Any](/*18.8*/navigation())),format.raw/*18.20*/("""
		    <div class="container">
			    """),_display_(Seq[Any](/*20.9*/{header(title)})),format.raw/*20.24*/("""
			    <div id="content">"""),_display_(Seq[Any](/*21.27*/content)),format.raw/*21.34*/("""</div>
			    <div id="push"></div>
		    </div>
	    </div>
	    <div id="footer">
	      <div class="container">
	        """),_display_(Seq[Any](/*27.11*/footer())),format.raw/*27.19*/("""
	      </div>
	    </div>
    </body>
</html>
"""))}
    }
    
    def render(title:String,content:Html) = apply(title)(content)
    
    def f:((String) => (Html) => play.api.templates.Html) = (title) => (content) => apply(title)(content)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Mar 11 10:47:16 CET 2013
                    SOURCE: /home/sander/school/3e/VOPRO/avank-bebras/application/app/views/main.scala.html
                    HASH: c26e3743080719a0e0bf8afe05570dc710db0a0d
                    MATRIX: 759->1|883->31|910->50|997->102|1023->107|1125->174|1139->180|1192->212|1284->268|1299->274|1375->327|1463->379|1478->385|1534->419|1620->469|1635->475|1712->529|1775->556|1790->562|1857->607|1950->664|1965->670|2039->722|2160->808|2194->820|2268->859|2305->874|2368->901|2397->908|2558->1033|2588->1041
                    LINES: 27->1|31->1|32->3|37->8|37->8|38->9|38->9|38->9|39->10|39->10|39->10|40->11|40->11|40->11|41->12|41->12|41->12|42->13|42->13|42->13|43->14|43->14|43->14|47->18|47->18|49->20|49->20|50->21|50->21|56->27|56->27
                    -- GENERATED --
                */
            