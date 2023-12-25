package com.rahulghag.blogapp.data.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rahulghag.blogapp.R
import com.rahulghag.blogapp.utils.Resource
import com.rahulghag.blogapp.utils.UiMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.util.Locale

suspend fun <T, V> baseApiCall(
    apiCall: suspend () -> Response<T>,
    domainMapper: (T) -> V,
    onSuccess: (V) -> Unit
): Resource<V> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val data = domainMapper(responseBody)
                    onSuccess(data)
                    Resource.Success(data = data)
                } else {
                    Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
                }
            } else {
                Resource.Error(
                    message = parseErrorResponse(response.errorBody())
                )
            }
        } catch (e: IOException) {
            Resource.Error(
                message = UiMessage.StringResource(R.string.error_no_internet_connection)
            )
        } catch (e: Exception) {
            Resource.Error(
                message = UiMessage.StringResource(R.string.error_something_went_wrong)
            )
        }
    }
}

fun parseErrorResponse(errorBody: ResponseBody? = null): UiMessage {
    val errorMessages = mutableListOf<String>()
    errorBody?.let {
        val errorResponse: JSONObject =
            JSONObject(errorBody.string()).getJSONObject("errors")
        val keys: Iterator<String> = errorResponse.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val errorJsonArray = errorResponse.getJSONArray(key)
            val typeToken = object : TypeToken<List<String>>() {}.type
            val errors =
                Gson().fromJson<List<String>>(errorJsonArray.toString(), typeToken)
            errors.forEach { value ->
                errorMessages.add(
                    "${
                        key.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        }
                    } $value"
                )
            }
        }
    }
    return if (errorMessages.isNotEmpty()) {
        UiMessage.DynamicMessage(errorMessages.joinToString("\n"))
    } else {
        UiMessage.StringResource(R.string.error_something_went_wrong)
    }
}