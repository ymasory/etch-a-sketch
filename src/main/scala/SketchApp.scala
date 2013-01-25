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
        case JsonMessage(json) => {
          broadcast(json)
          send("ack json! " + json)
          println("broadcasted json: " + json)
        }
        case TextMessage(txt) => {
          broadcast(txt)
          send("ack txt! " + txt)
          println("broadcasted txt: " + txt)
        }
        case msg => println("ignoring " + msg)
      }
    }
  }
}



