package ru.example.blog.model

import java.sql.Timestamp

case class Post(id: Int, userId: Int, text: String, dateTime: Timestamp)
