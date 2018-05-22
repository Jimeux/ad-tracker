package tracking

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.util.Timeout
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.{DefaultFormats, Formats, Serialization}

final class TrackingRoutes(
  trackingService: TrackingService
)(
  implicit system: ActorSystem,
  timeout: Timeout
) extends Json4sSupport {

  lazy val log = Logging(system, classOf[TrackingRoutes])

  implicit val formats: Formats = DefaultFormats
  implicit val serialization: Serialization = org.json4s.jackson.Serialization

  lazy val routes: Route = pathPrefix("v1" / "track") {
    delivery   ~
    impression ~
    click
  }

  def delivery: Route = get {
    path("delivery") {
      parameter('adId.as[Int]).as(AdId) { adId =>
        onSuccess(trackingService.trackImp(adId)) { imps =>
          complete(imps.toString)
        }
      }
    }
  }
  
  def impression: Route = get {
    path("imp") {
      parameter('adId.as[Int]).as(AdId) { adId =>
        onSuccess(trackingService.trackImp(adId)) { imps =>
          complete(imps.toString)
        }
      }
    }
  }

  def click: Route = get {
    path("click") {
      parameter('adId.as[Int]).as(AdId) { adId =>
        onSuccess(trackingService.trackClick(adId)) { clicks =>
          complete(clicks.toString)
        }
      }
    }
  }

}
