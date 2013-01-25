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

  get("/hello") {
    "hello world"
  }
}
