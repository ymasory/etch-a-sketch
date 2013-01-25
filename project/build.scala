import sbt._
import Keys._

object EtchasketchBuild extends Build {
  val Organization = "sketch"
  val Name = "etch-a-sketch"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.9.2"
  val ScalatraVersion = "2.2.0-RC3"

  import java.net.URL
  import com.github.siasia.PluginKeys.port
  import com.github.siasia.WebPlugin.{container, webSettings}

  lazy val project = Project (
    "etch-a-sketch",
    file("."),
    settings = Defaults.defaultSettings ++ webSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers ++= Seq(
        "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
        "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
      ),
      libraryDependencies ++= Seq(
        "org.scalatra" % "scalatra-atmosphere" % "2.2.0-RC3",
        "org.scalatra" % "scalatra-json" % "2.2.0-RC3",
        "org.json4s"   %% "json4s-jackson" % "3.1.0",
        "org.eclipse.jetty" % "jetty-websocket" % "8.1.8.v20121106" % "container",
        "org.scalatra" % "scalatra" % ScalatraVersion,
        "org.scalatra" % "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" % "scalatra-specs2" % ScalatraVersion % "test",
        "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "8.1.8.v20121106" % "container",
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar"))
      ),
      browseTask
    )
  )


  val browse = TaskKey[Unit]("browse", "open web browser to localhost on container:port")
  val browseTask = browse <<= (streams, port in container.Configuration) map { (streams, port) =>
    import streams.log
    val url = new URL("http://localhost:%s" format port)
    try {
      log info "Launching browser."
      java.awt.Desktop.getDesktop.browse(url.toURI)
    }
    catch {
      case _ => {
        log info { "Could not open browser, sorry. Open manually to %s." format url.toExternalForm }
      }
    }
  }
}
