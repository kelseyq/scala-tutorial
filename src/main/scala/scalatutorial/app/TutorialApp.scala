package scalatutorial.app

import org.scalatra._
import scalatutorial.app.service.WeatherService
import scalatutorial.app.utils.TutorialServlet

class TutorialApp extends TutorialServlet {

  //SETUP
  get("/") {
    "Hello OSCON!"
  }

  //EXPRESSIONS
  get("/smalltalk") {
    val weather = params("weather")

    if (weather == "hot") {
      "Hot enough for ya?"
    } else {
      "Brrrr!"
    }
  }

  get("/moreSmalltalk") {
    val weather = params("weather")

    weather match {
      case "hot" => "Don't forget the sunscreen!"
      case "nice" => "Couldn't ask for a nicer day, huh?"
      case "rain" => "It's coming down cats & dogs"
      case "cold" => "It's colder than a polar bear's toenails out there!"
    }
  }

  /*
   * http://localhost:8080/highlow?temps=45,70 should return
   * "The high temperature is 45 and the low temperature is 70"
   */
  get("/highLow") {
    val temps: List[Int] = getParamsList(params("temps"))

    ???
  }

  //FUNCTIONS
  def fToC(fahrenheit: Double): Double = {
    (fahrenheit - 32.0) * (5.0/9.0)
  }

  get("/convertToC") {
    val f = params("temp").toDouble
    fToC(f)
  }

  def cToF(celsius: Int): Double = ???
  def cToF(celsius: Double): Double = ???

  get("/convertToF") {
    ???
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

  get("/weatherRecords") {
    WeatherService.getRecords
  }

  //FIRST CLASS FUNCTIONS
  get("/averageTemp") {
    val date = params("city")

    ???
  }

  get("/maxTemp") {
    val date = params("date")
    ???
  }

}
