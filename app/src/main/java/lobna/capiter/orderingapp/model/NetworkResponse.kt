package lobna.capiter.orderingapp.model

sealed class NetworkResponse {
    data class ErrorResponse(val code: Int, val message: String) : NetworkResponse()
    data class ExceptionResponse(val message: String?) : NetworkResponse()
    data class DataResponse<T>(val data: T) : NetworkResponse()
}