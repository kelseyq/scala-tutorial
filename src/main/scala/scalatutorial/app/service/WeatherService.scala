package scalatutorial.app.service

import java.io.File

object WeatherService {
 lazy val weather = scala.io.Source.fromURL(getClass.getResource("/weather.csv"))

 lazy val weatherString: String = try {
   weather.mkString
 } finally {
   weather.close
 }

   def testing(): String = weatherString
}
