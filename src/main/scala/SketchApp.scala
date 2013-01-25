package sketch

import org.scalatra._
import org.scalatra.atmosphere._
import org.scalatra.json.{ JValueResult, JacksonJsonSupport }
import org.json4s._
import JsonDSL._

class SketchApp extends ScalatraServlet 
  with JValueResult 
  with JacksonJsonSupport with SessionSupport 
  with AtmosphereSupport {

  implicit protected val jsonFormats: Formats = DefaultFormats

  atmosphere("/") {
    new AtmosphereClient {
      def receive = {
        case TextMessage(json) => {
          broadcast(json)
          send(json)
          println("broadcasted: " + json)
        }
        case msg => println("ignoring " + msg)
      }
    }
  }
}



