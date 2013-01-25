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
        case TextMessage(text) => {
          println(text)
          send(text.reverse)
        }
        case JsonMessage(json) =>
        case msg => println("ignoring " + msg)
      }
    }
  }
}



