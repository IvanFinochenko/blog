package ru.example.blog

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

object Server extends App {

  implicit val actorSystem = ActorSystem("blog")
  implicit val materializer = ActorMaterializer()
  implicit val ec = actorSystem.dispatcher
  val logger = actorSystem.log

  val wsRouter: ActorRef = actorSystem.actorOf(WsRouter.props)

  val routes = new Controller(wsRouter).build

  Http()
      .bindAndHandle(routes, "localhost", 8080)
      .map(_ => logger.info("server start localhost:8080"))

}
