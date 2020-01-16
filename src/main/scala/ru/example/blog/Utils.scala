package ru.example.blog

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, ResponseEntity, StatusCode, StatusCodes}

object Utils {

  def buildSuccesResponse(data: String) = {
    builHttpResponse(StatusCodes.OK, data)
  }

  def buildErrorResonse(data: String) = {
    builHttpResponse(StatusCodes.InternalServerError, data)
  }


  private def builHttpResponse(status: StatusCode, data: String) = {
    HttpResponse(
      status = status,
      entity = HttpEntity(
        contentType = ContentTypes.`text/plain(UTF-8)`,
        data
      )
    )
  }


}
