package ru.example.blog

import akka.actor.ActorRef
import akka.stream.KillSwitch

case class WsSource(source: ActorRef, killSwitch: KillSwitch)
