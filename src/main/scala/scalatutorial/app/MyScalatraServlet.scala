package scalatutorial.app

import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import scalatutorial.app.service.WeatherService

class MyScalatraServlet extends ScalatraServlet with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  //SETUP
  get("/") {
    "{" +
      "\"hello\": \"OSCON\"" +
      "}"
  }

  //CALLING A METHOD
  get("/hello") {
    val who = params("who")

    "{" +
      "\"hello\": \"" + who + "\"" +
      "}"
  }

  //COLLECTIONS
  get("/cloud") {
    val allClouds = List("cirrus", "altostratus", "cumulus")
    allClouds(0)
  }

  //CASE CLASS
  case class Cloud(name: String, heightInFeet: Int)

  get("/clouds") {
    val cirrus = Cloud("cirrus", 18000)
    val altostratus = Cloud("altostratus", 10000)
    val cumulus = Cloud("cumulus", 6000)

    val allClouds = List(cirrus, altostratus, cumulus)
    allClouds
  }

  case class ChitChat(opener: String)

  //EXPRESSIONS
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

  //FUNCTIONS
  def fToC(fahrenheit: Int): Double = {
    (fahrenheit - 32.0) * (5.0/9.0)
  }

  get("/convertToCelsius") {
    val f = params("temp").toInt
    fToC(f)
  }

  def cToF(celsius: Int): Double = ???

  get("/convertToCelsius") {
    ???
  }

  get("/weatherRecords") {
    WeatherService.getRecords
  }

  //FIRST CLASS FUNCTIONS
  get("/averageTemp/:city") {
    ???
  }

  get("/maxTemp") {
    val date = params("date")
    ???
  }

}
