package sketch

import org.scalatra._
import org.scalatra._
import org.scalatra.atmosphere._                            
import org.scalatra.json.{JValueResult, JacksonJsonSupport} 
import org.json4s._                                         
import JsonDSL._
import java.util.Date                                       
import java.text.SimpleDateFormat                           
import org.fusesource.scalate.Template                      

class SketchApp extends ScalatraServlet                
  with JValueResult                     
  with JacksonJsonSupport with SessionSupport 
  with AtmosphereSupport  {

  implicit protected val jsonFormats: Formats = DefaultFormats

  get("/") {
    "hello world"
  }

  atmosphere("/the-chat") {                                 
    new AtmosphereClient {                                  
      def receive: AtmoReceive = {                          
        case Connected =>
          println("Client %s is connected" format uuid)     
          broadcast(("author" -> "Someone") ~ ("message" -> "joined the room") ~ ("time" -> (new Date().getTime.toString )), Everyone)
        
        case Disconnected(ClientDisconnected, _) =>         
          broadcast(("author" -> "Someone") ~ ("message" -> "has left the room") ~ ("time" -> (new Date().getTime.toString )), Everyone)
            
        case Disconnected(ServerDisconnected, _) =>         
          println("Server disconnected the client %s" format uuid)     
        case _: TextMessage =>                              
          send(("author" -> "system") ~ ("message" -> "Only json is allowed") ~ ("time" -> (new Date().getTime.toString )))
        
        case JsonMessage(json) =>                           
          println("Got message %s from %s".format((json \ "message").extract[String], (json \ "author").extract[String]))
          val msg = json merge (("time" -> (new Date().getTime().toString)): JValue)
          broadcast(msg) // by default a broadcast is to everyone but self
          //  send(msg) // also send to the sender          
      }                                                     
    }
  }

}
