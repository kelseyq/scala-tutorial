package scalatutorial.app.service

import java.io.File
import java.util.Date
import java.text.SimpleDateFormat

object WeatherService {
  val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")

  def getRecords(): List[WeatherRecord] = {
    lazy val weather = scala.io.Source.fromURL(getClass.getResource("/weather.csv"))

    val weatherRecords = weather.getLines().toList.map{ line =>

      val entries = line.split(",").map{ field => field.trim }

      val entry_city = entries(0)
      val entry_date = dateFormat.parse(entries(1))
      val entry_high = entries(2).toInt
      val entry_low = entries(3).toInt

      /*
       * TASK: Modify WeatherService to handle both csv entries with rainfall field and without
       * Note: I'm going to assume that csv rows with 5 elements will have their last element
       * represent rainfall data. Also, for rows with no rainfall data, we will create WeatherRecords
       * with -1 assigned to rainfallInInches.
       */
      val entry_rainfallInInches = if(entries.length == 5) entries(4).toDouble else -1.0

      WeatherRecord(entry_city, entry_date, entry_high, entry_low, entry_rainfallInInches)}
    weather.close
    weatherRecords
  }
}

case class WeatherRecord(city: String, date: Date, high: Int, low: Int, rainfall: Double)
