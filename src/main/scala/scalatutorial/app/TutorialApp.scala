package scalatutorial.app

import org.scalatra._
import scalatutorial.app.service.{WeatherRecord, WeatherService}
import scalatutorial.app.utils.TutorialServlet

class TutorialApp extends TutorialServlet {

  /*
   * Big thanks to Tony (https://github.com/t-pleasure) for contributing the answers!
   */

  //REPL work
  //TASKS:
  //  Store your own name as a value
      //  scala> val myName = "Tony"
      //  myName: String = Tony

  //  Store the name of the person sitting next to you as a value
      //  scala> val neighbor = "Kelsey"
      //  neighbor: String = Kelsey

  //  Store your names + your neighbors' names in a list
      //  scala> val listOfNeighbors = List("Tony", "Kelsey", "Jason")
      //  listOfNeighbors: List[String] = List(Tony, Kelsey, Jason)

  //  Ask two people sitting near you what age they started programming
  //  Find the average age that all of you started programming, without rounding
      //  scala> val averageAge: Double = (12 + 21 + 16)/3.0
      //  averageAge: Double = 16.333333333333332

  //  Create a map of name to age
      //  scala> val mapOfNeighbors = Map("Tony" -> 12, "Kelsey" -> 21, "Jason" -> 16)
      //  mapOfNeighbors: scala.collection.immutable.Map[String,Int] = Map(Tony -> 12, Kelsey -> 21, Jason -> 16)

  //SETUP
  //TASK: change "Hello OSCON" to hello something else
  get("/") {
    "Hello completed answers!"
  }

  //EXPRESSIONS
  //TASK: Modify the /smalltalk endpoint code to operate on an integer representing the temperature
  get("/smalltalk") {
    val weather = params("weather").toInt

    if (weather > 70) {
      "Hot enough for ya?"
    } else {
      "Brrrr!"
    }
  }

  //TASK: Add a default to /moreSmalltalk that returns "Yup, sure is FOO"
  get("/moreSmalltalk") {
    val weather = params("weather")

    weather match {
      case "hot" => "Don't forget the sunscreen!"
      case "nice" => "Couldn't ask for a nicer day, huh?"
      case "rain" => "It's coming down cats & dogs"
      case "cold" => "It's colder than a polar bear's toenails out there!"
      case other => "Yup, sure is " + other
    }
  }

  /*
   * http://localhost:8080/highlow?temps=45,70 should return
   * "The low temperature is 45 and the high temperature is 70"
   *
   * TASK: Implement/highLow
   * Use pattern matching with value binding to extract the high & low temp
   * Use guards to validate the data
   */
  get("/highLow") {
    val temps: List[Int] = getParamsList(params("temps"))

    // safe guard to ensure that we know which value is the high and which is the low.
    temps match {
      case List(low, high) if high >= low => "The low temperature is " + low + " and the high temperature is " + high
      case _ => "High and low temperature not found"
    }
  }

  //FUNCTIONS
  def fToC(fahrenheit: Double): Double = {
    (fahrenheit - 32.0) * (5.0/9.0)
  }

  get("/convertToC") {
    val f = params("temp").toDouble
    fToC(f)
  }

  def cToF(celsius: Double): Double = {
    (celsius * 9.0/5.0) + 32
  }

  //TASK: Complete the convertToF endpoint
  get("/convertToF") {
    val f = params("temp").toDouble
    cToF(f)
  }

  //TASK: Refactor your code from the /smalltalk endpoint to call a function
  // get("/smalltalk") can now just call getSmalltalk(weather)
  def getSmalltalk(weather: Int): String = {
    if (weather > 70) {
      "Hot enough for ya?"
    } else {
      "Brrrr!"
    }
  }

  //CASE CLASS
  //TASK: Add a field of your choosing to the Cloud case class
  //TASK: Create an object outside of TutorialApp with a convertFeetToMeters method
  //   See below

  case class Cloud(name: String, heightInFeet: Int, looksLike: String) {
    //TASK: Add a method to Cloud that uses the object's method to return the cloud's height in meters
    def meters = Converter.convertFeetToMeters(heightInFeet)
  }

  get("/clouds") {
    val cirrus = Cloud("cirrus", 18000, "a bunny")
    val altostratus = Cloud("altostratus", 10000, "Richard Nixon")
    val cumulus = Cloud("cumulus", 6000, "South America")

    val allClouds = List(cirrus, altostratus, cumulus)

   //TASK:  Change /clouds to print out all the cloud names in uppercase using .map
    allClouds.map(_.name.toUpperCase)
  }

  get("/weatherRecords") {
    WeatherService.getRecords
  }

  //FIRST CLASS FUNCTIONS
  // TASK: Implement /averageTemp:city
  get("/averageTemp") {
    val city = params("city")
    // obtain records for the specified city
    val records =  WeatherService.getRecords.filter{r: WeatherRecord => r.city == city}
    // obtain the number of records associated with the city
    val nRecords = records.length.toDouble
    // compute average high and low temperatures
    val averageHigh = records.map{r: WeatherRecord => r.high}.sum / nRecords
    val averageLow = records.map{r: WeatherRecord => r.low}.sum / nRecords

    Map("average_high"->averageHigh, "average_low"->averageLow)
  }

  /**
   * TASK: Implement /maxTemp (maximum temperature for a given date)
   */
  get("/maxTemp") {
    // obtain date input
    val date = params("date")
    // parse the date input into an actual Date class
    val dateObj = WeatherService.dateFormat.parse(date)
    // obtain records that match the target date
    val records = WeatherService.getRecords.filter{r: WeatherRecord => r.date.equals(dateObj)}

    // return the record with the maximum temperature by finding the record with the largest high temp.
    // (note: we can ignore low temps)
    records.maxBy{r: WeatherRecord => r.high}
  }

  /**
   * TASK: Come up with a query of your choice on the weather data and implement an end point for it.
   * NOTE: Here we are implementing the /stdTemp end point which computes the standard deviation of
   * high and low temperatures.
   */
  def std(xs: List[Int]): Double = {
    val n = xs.length.toDouble
    val mu = xs.sum / n
    Math.sqrt((xs.map(x => (x-mu)*(x-mu)).sum)/n)
  }

  get("/stdTempDev") {
    val city = params("city")
    // obtain records for the specified city
    val records = WeatherService.getRecords.filter{r: WeatherRecord => r.city == city}
    // obtain high temperatures and low temperatures of records
    val highs = records.map{r: WeatherRecord => r.high}
    val lows = records.map{r: WeatherRecord => r.low}

    Map("std of lows"->std(lows), "std of highs"->std(highs))
  }
}

//TASK: Create an object outside of TutorialApp with a convertFeetToMeters method
object Converter {
  def convertFeetToMeters(feet: Int): Double = feet * 0.3048
}
