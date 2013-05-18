import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "avank"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,             
      "postgresql" % "postgresql" % "9.1-901.jdbc4",
      "commons-codec" % "commons-codec" % "1.7",
      "commons-logging" % "commons-logging" % "1.1.1",
      "org.bouncycastle" % "bcpkix-jdk15on" % "1.48",
      "org.bouncycastle" % "bcprov-jdk15on" % "1.48",
      "dom4j" % "dom4j" % "1.6.1",
      "it.sauronsoftware.ftp4j" % "ftp4j" % "1.7.2",
      "org.apache.geronimo.specs" % "geronimo-stax-api_1.0_spec" % "1.0.1",
      "log4j" % "log4j" % "1.2.13",
      "org.apache.poi" % "poi" % "3.9",
      "org.apache.poi" % "poi-ooxml" % "3.9",
      "org.apache.poi" % "poi-ooxml-schemas" % "3.9",
      "org.apache.xmlbeans" % "xmlbeans" % "2.3.0",
      "javax.activation" % "activation" % "1.1.1",
      "javax.mail" % "mail" % "1.4.7"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
      resolvers += Resolver.url("mvn-repo", url("https://raw.github.com/bastengao/mvn-repository/master/releases"))(Resolver.ivyStylePatterns),
      resolvers += "mvn-repo" at "https://raw.github.com/bastengao/mvn-repository/master/releases/",
      javacOptions += "-Xlint:all"     
    )


}

