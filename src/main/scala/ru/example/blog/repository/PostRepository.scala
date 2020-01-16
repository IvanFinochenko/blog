package ru.example.blog.repository

import ru.example.blog.model.Post

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object PostRepository {

  private val posts = ListBuffer[Post]()

  def getAllUserPosts(userId: Int): mutable.Seq[Post] = {
    posts.filter(_.userId == userId)
  }

  def insertPost(post: Post): Unit = {
    posts.append(post)
  }

  def getPost(postId: Int): Option[Post] = {
    posts.find(_.id == postId)
  }

}
