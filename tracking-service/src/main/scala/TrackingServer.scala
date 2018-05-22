import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.redis.RedisClient
import org.json4s.{DefaultFormats, Formats, Serialization}
import tracking.{TrackingRoutes, TrackingService}

import scala.concurrent.duration.{Duration, _}
import scala.concurrent.{Await, ExecutionContext}

object TrackingServer extends App {

  implicit val system: ActorSystem = ActorSystem("trackingServer")
  implicit val dispatcher: ExecutionContext = system.dispatcher
  implicit val timeout: Timeout = Timeout(5.seconds)
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  implicit val formats: Formats = DefaultFormats
  implicit val serialization: Serialization = org.json4s.jackson.Serialization

  val client = new RedisClient("localhost", 6380)
  val trackingService = new TrackingService(client)

  val trackingRoutes = new TrackingRoutes(trackingService).routes

  Http().bindAndHandle(trackingRoutes, "localhost", 8000)

  println(s"Server online at http://localhost:8000/")

  Await.result(system.whenTerminated, Duration.Inf)

}
