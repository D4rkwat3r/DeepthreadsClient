package ru.deepthreads.app

import android.os.Build
import okhttp3.*
import okio.Buffer
import ru.deepthreads.app.dts.DTSFeature
import ru.deepthreads.app.repo.AccountRepository
import java.lang.StringBuilder
import java.util.*

class RequestRewriter : Interceptor {

    private fun createUserAgent(): String {
        val agentBuilder = StringBuilder()
        agentBuilder.append("Deepthreads/")
        agentBuilder.append(BuildConfig.VERSION_CODE)
        agentBuilder.append("-")
        agentBuilder.append(BuildConfig.VERSION_NAME)
        agentBuilder.append("; ")
        agentBuilder.append("Dalvik ")
        agentBuilder.append(System.getProperty("java.vm.version"))
        agentBuilder.append("; ")
        agentBuilder.append("API ")
        agentBuilder.append(Build.VERSION.SDK_INT)
        agentBuilder.append("; ")
        agentBuilder.append("Release ")
        agentBuilder.append(Build.VERSION.RELEASE)
        agentBuilder.append("; ")
        agentBuilder.append("Name ")
        agentBuilder.append("\"")
        agentBuilder.append(Build.VERSION.CODENAME)
        agentBuilder.append("\"")
        return agentBuilder.toString()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = rewriteHeaders(chain.request(), UUID.randomUUID().toString())
        return chain.proceed(rewriteBody(request))
    }

    private fun rewriteHeaders(request: Request, nonce: String): Request {
        val changedRequest = request.newBuilder()
                                .addHeader("User-Agent", createUserAgent())
                                .addHeader("Android-Agent", System.getProperty("http.agent")!!)
                                .addHeader("DTSTime", System.currentTimeMillis().toString())
                                .addHeader("DTSNonce", nonce)
                                .addHeader("DTSContext", DTSFeature.ContextSignature.generateNew(request, nonce))
        if (AccountRepository.get() != null) {
            changedRequest.addHeader("AuthToken", AccountRepository.get()!!.authToken)
        }
        return changedRequest.build()
    }

    private fun rewriteBody(request: Request): Request {
        val changedRequest = request.newBuilder()
        val oldBody = request.body()
        if (request.method() == "POST" && request.body() == null) {
            changedRequest.method(
                request.method(),
                RequestBody.create(
                    MediaType.get("application/x-www-form-urlencoded"),
                    byteArrayOf()
                )
            )
        }
        if (oldBody != null && oldBody.contentType().toString().startsWith("application/json")) {
            changedRequest.method(request.method(), RequestBody.create(MediaType.get(oldBody.contentType().toString()), addExtensionField(oldBody)))
        }
        return changedRequest.build()
    }

    private fun addExtensionField(body: RequestBody): String {
        val bodyBytes = Buffer()
        val resultBytes = Buffer()
        body.writeTo(bodyBytes)
        val content = String(bodyBytes.readByteArray(), Charsets.UTF_8).toCharArray()
        val braceIndex = content.indexOf(content.findLast { it == '}' }!!)
        content[braceIndex] = ','
        resultBytes.write(content.concatToString().toByteArray(Charsets.UTF_8))
        resultBytes.write("\"dtsExtension\": \"${DTSFeature.ExtensionSignature.generateNew(bodyBytes.readByteArray())}\"}".toByteArray(Charsets.UTF_8))
        return String(resultBytes.readByteArray(), Charsets.UTF_8)
    }

}