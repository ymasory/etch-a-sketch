package sketch

import org.scalatra._

class SketchApp extends ScalatraServlet {

  get("/") {
    "hello world"
  }
}
