package scalatutorial.app

import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

class MyScalatraServlet extends ScalatraServlet with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/") {
    "{" +
      "\"hello\": \"OSCON\"" +
      "}"
  }

  get("/hello") {
    val who = params("who")

    "{" +
      "\"hello\": \"" + who + "\"" +
      "}"
  }

  case class ChitChat(opener: String)

  get("/smalltalk") {
    val weather = params("weather")

    if (weather == "hot") {
      ChitChat("Hot enough for ya?")
    } else {
      ChitChat("Brrrr!")
    }

    weather match {
      case "hot" => ChitChat("Don't forget the sunscreen!")
      case "nice" => ChitChat("Couldn't ask for a nicer day, huh?")
      case "rain" => ChitChat("It's coming down cats & dogs")
      case "cold" => ChitChat("It's colder than a polar bear's toenails out there!")
    }
  }
  
}
