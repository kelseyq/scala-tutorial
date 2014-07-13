package scalatutorial.app.utils

import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

trait TutorialServlet extends ScalatraServlet with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  def getParamsList(param:String): List[Int] = param.split(",").toList.map(_.toInt)

}
