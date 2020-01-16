package ru.example.blog

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import ru.example.blog.WsRouter.{Disconnect, NewConnect, NewPost}
import ru.example.blog.model.Post

import scala.collection.mutable

class WsRouter extends Actor with ActorLogging {

  type UserId = Int

  type ConnectionId = Int

  private val connections = mutable.Map.empty[UserId, Map[ConnectionId, WsSource]]

  override def receive: Receive = {
    case NewConnect(userId, connectionId,wsSource) =>
      log.info(s"new connection for user $userId")
      val newCon = connections
          .get(userId)
          .map(c => c + (connectionId -> wsSource))
          .getOrElse(Map(connectionId -> wsSource))
      connections.update(userId, newCon)

    case Disconnect(userId, connectionId) =>
      log.info(s"disconnect user=$userId")
      val connection = connections.get(userId)
      connection.foreach(userCon =>
            userCon.get(connectionId).foreach(_.killSwitch.shutdown())
          )
      connection
          .map(x => x - connectionId)
          .foreach(x => connections.update(userId, x))

    case NewPost(post) =>
      connections
          .get(post.userId)
          .foreach(_.foreach(_._2.source ! post))
  }
}

object WsRouter {

  def props = Props(new WsRouter())

  case class NewConnect(userId: Int, connectionId: Int, wsSource: WsSource)

  case class Disconnect(userId: Int, connectionId: Int)

  case class NewPost(post: Post)

}
