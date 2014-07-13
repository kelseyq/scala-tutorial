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

      WeatherRecord(entry_city, entry_date, entry_high, entry_low)}
    weather.close
    weatherRecords
  }
}

case class WeatherRecord(city: String, date: Date, high: Int, low: Int)
