package tracking

import com.redis.RedisClient
import com.redis.serialization.Parse.Implicits._

import scala.concurrent.{ExecutionContext, Future}

final class TrackingService(client: RedisClient)(implicit ec: ExecutionContext) {
  
  def impKey(adId: AdId): String = s"ad${adId.value}:imp"
  def clickKey(adId: AdId): String = s"ad${adId.value}:click"
  val rankingKey = "ranking"

  def trackImp(adId: AdId): Future[Long] = Future {
    val imps = client.incr(impKey(adId)) getOrElse 0L
    if (imps % 10 == 0) updateCtrOnImp(adId, imps)
    imps
  }

  def trackClick(adId: AdId): Future[Long] = Future {
    val clicks = client.incr(clickKey(adId)) getOrElse 0L
    updateCtrOnClick(adId, clicks)
    clicks
  }

  private def updateCtrOnImp(adId: AdId, imps: Long) = Future {
    val clicks = client.get[Long](clickKey(adId)) getOrElse 0L
    val ctr = clicks / (imps + 0.01)
    client.zadd(rankingKey, ctr, adId.value)
  }

  private def updateCtrOnClick(adId: AdId, clicks: Long) = Future {
    val imps = client.get[Long](impKey(adId)) getOrElse 0L
    val ctr = clicks / (imps + 0.01)
    client.zadd(rankingKey, ctr, adId.value)
  }

}
