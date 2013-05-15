import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "application"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,             
      "postgresql" % "postgresql" % "9.1-901.jdbc4",
      "commons-codec" % "commons-codec" % "1.7",
      "commons-logging" % "commons-logging" % "1.1.1",
      "org.bouncycastle" % "bcpkix-jdk15on" % "1.48",
      "org.bouncycastle" % "bcprov-jdk15on" % "1.48",
      "dom4j" % "dom4j" % "1.6.1",
      "org.apache.servicemix.bundles" % "org.apache.servicemix.bundles.aspectj" % "1.7.2_1",
      "stax" % "stax-api" % "1.0.1",
      "log4j" % "log4j" % "1.2.13",
      "org.apache.poi" % "poi" % "3.9",
      "org.apache.xmlbeans" % "xmlbeans" % "2.3.0",
      "javax.activation" % "activation" % "1.1.1",
      "javax.mail" % "mail" % "1.4.7"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
      javacOptions += "-Xlint:all"     
    )
}
