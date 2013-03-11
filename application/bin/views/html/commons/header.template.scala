
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
object header extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(title: String):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.17*/("""
<div id="page-header">
	<h1>"""),_display_(Seq[Any](/*3.7*/title)),format.raw/*3.12*/("""</h1>
</div>"""))}
    }
    
    def render(title:String) = apply(title)
    
    def f:((String) => play.api.templates.Html) = (title) => apply(title)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Mar 11 10:47:16 CET 2013
                    SOURCE: /home/sander/school/3e/VOPRO/avank-bebras/application/app/views/commons/header.scala.html
                    HASH: 0d599d139c5621091b8c75984ebca1728cb3d621
                    MATRIX: 764->1|856->16|920->46|946->51
                    LINES: 27->1|30->1|32->3|32->3
                    -- GENERATED --
                */
            