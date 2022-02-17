package ru.deepthreads.app

import android.app.Activity
import android.graphics.Color
import android.widget.Button
import androidx.lifecycle.LifecycleOwner
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.showProgress
import com.squareup.moshi.Moshi
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.deepthreads.app.models.*
import ru.deepthreads.app.utils.ResponseCallback
import java.io.File

class APIHolder(private val activity: Activity, private val moshi: Moshi) {
    private val httpClient = OkHttpClient.Builder()
                    .addInterceptor(RequestRewriter())
                    .protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
                    .followRedirects(false)
                    .retryOnConnectionFailure(true)
                    .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://deepthreads.ru/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(httpClient)
        .build()
    private val apiService = retrofit.create(APIService::class.java)

    fun getClient(): OkHttpClient {
        return httpClient
    }

    fun login(
        deepId: String,
        password: String,
        button: Button,
        onLoad: (AccountResponse) -> Unit
    ) {
        with (activity as LifecycleOwner) {
            bindProgressButton(button)
            button.attachTextChangeAnimator()
            button.showProgress {
                buttonTextRes = R.string.loading
                progressColor = Color.BLUE
            }
        }
        val body = LoginRequest(deepId, password)
        apiService.login(body).enqueue(ResponseCallback(activity, moshi, onLoad, button))
    }

    fun register(
        nickname: String,
        deepId: String,
        password: String,
        pictureResource: String,
        button: Button,
        onLoad: (AccountResponse) -> Unit
    ) {
        with (activity as LifecycleOwner) {
            bindProgressButton(button)
            button.attachTextChangeAnimator()
            button.showProgress {
                buttonTextRes = R.string.loading
                progressColor = Color.BLUE
            }
        }
        val body = RegisterRequest(nickname, deepId, password, pictureResource)
        apiService.register(body).enqueue(ResponseCallback(activity, moshi, onLoad, button))
    }

    fun uploadImage(
        file: File,
        onLoad: (UploadFileResponse) -> Unit
    ) {
        val mimeType = if (file.extension == "jpg") {
            MediaType.get("image/jpeg")
        } else {
            MediaType.get("image/${file.extension}")
        }
        val body = MultipartBody.Part.createFormData(
            "file",
            file.name,
            RequestBody.create(mimeType, file)
        )
        apiService.upload(file.extension, body).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun loadChatList(
        type: Int,
        skip: Int,
        limit: Int,
        onLoad: (ChatListResponse) -> Unit
    ) {
        apiService.getChats(type, skip, limit).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun getChat(
        chatId: String,
        onLoad: (ChatResponse) -> Unit
    ) {
        apiService.getChat(chatId).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun startPublicChat(
        title: String,
        iconResource: String,
        backgroundResource: String,
        onLoad: (ChatResponse) -> Unit
    ) {
        val body = PublicChatCreatingRequest(title, iconResource, backgroundResource)
        apiService.startPublicChat(body).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun startPrivateChat(
        invitedUserIds: List<String>,
        initialMessage: String,
        onLoad: (ChatResponse) -> Unit
    ) {
        val body = PrivateChatCreatingRequest(invitedUserIds, initialMessage)
        apiService.startPrivateChat(body).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun sendMessage(
        chatId: String,
        content: String,
        type: Int,
        errorHandler: ((APIException) -> Unit)?,
        onLoad: (MessageResponse) -> Unit,
    ) {
        val body = MessageSendingRequest(content, type)
        apiService.sendMessage(chatId, body).enqueue(ResponseCallback(activity, moshi, onLoad, errorHandler = errorHandler))
    }

    fun getMessages(
        chatId: String,
        skip: Int,
        limit: Int,
        onLoad: (MessageListResponse) -> Unit
    ) {
        apiService.getMessages(chatId, skip, limit).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun getMessage(
        chatId: String,
        messageId: String,
        onLoad: (MessageResponse) -> Unit
    ) {
        apiService.getMessage(chatId, messageId).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun replyMessage(
        chatId: String,
        messageId: String,
        content: String,
        type: Int,
        onLoad: (MessageResponse) -> Unit
    ) {
        val body = MessageSendingRequest(content, type)
        apiService.replyMessage(chatId, messageId, body).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun joinChat(
        chatId: String,
        onLoad: (EmptyOKResponse) -> Unit
    ) {
        apiService.joinChat(chatId).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun leaveChat(
        chatId: String,
        onLoad: (EmptyOKResponse) -> Unit
    ) {
        apiService.leaveChat(chatId).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun getUser(
        userId: String,
        onLoad: (UserProfileResponse) -> Unit
    ) {
        apiService.getUser(userId).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun getMe(
        onLoad: (UserProfileResponse) -> Unit
    ) {
        apiService.getMe().enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun getPosts(
        skip: Int,
        limit: Int,
        onLoad: (PostListResponse) -> Unit
    ) {
        apiService.getPosts(skip, limit).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun createPost(
        title: String,
        content: String,
        coverResource: String,
        backgroundResource: String,
        onLoad: (PostResponse) -> Unit
    ) {
        val body = PostCreatingRequest(title, content, coverResource, backgroundResource)
        apiService.createPost(body).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun getPost(
        postId: String,
        onLoad: (PostResponse) -> Unit
    ) {
        apiService.getPost(postId).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun getLikes(
        postId: String,
        skip: Int,
        limit: Int,
        onLoad: (LikeListResponse) -> Unit
    ) {
        apiService.getLikes(postId, skip, limit).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun getLike(
        postId: String,
        likeId: String,
        onLoad: (LikeResponse) -> Unit
    ) {
        apiService.getLike(postId, likeId).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun commentPost(
        postId: String,
        content: String,
        onLoad: (CommentResponse) -> Unit
    ) {
        val body = CommentingRequest(content)
        apiService.commentPost(postId, body).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun likePost(
        postId: String,
        onLoad: (LikeResponse) -> Unit
    ) {
        apiService.likePost(postId).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun unlikePost(
        postId: String,
        onLoad: (EmptyOKResponse) -> Unit
    ) {
        apiService.unlikePost(postId).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun getComments(
        postId: String,
        skip: Int,
        limit: Int,
        onLoad: (CommentListResponse) -> Unit
    ) {
        apiService.getComments(postId, skip, limit).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun getComment(
        postId: String,
        commentId: String,
        onLoad: (CommentResponse) -> Unit
    ) {
        apiService.getComment(postId, commentId).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun likeComment(
        postId: String,
        commentId: String,
        onLoad: (LikeResponse) -> Unit
    ) {
        apiService.likeComment(postId, commentId).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun unlikeComment(
        postId: String,
        commentId: String,
        onLoad: (EmptyOKResponse) -> Unit
    ) {
        apiService.unlikeComment(postId, commentId).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun getCommentLikes(
        postId: String,
        commentId: String,
        skip: Int,
        limit: Int,
        onLoad: (LikeListResponse) -> Unit
    ) {
        apiService.getCommentLikes(postId, commentId, skip, limit).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun getCommentLike(
        postId: String,
        commentId: String,
        likeId: String,
        onLoad: (LikeResponse) -> Unit
    ) {
        apiService.getCommentLike(postId, commentId, likeId).enqueue(ResponseCallback(activity, moshi, onLoad))
    }

    fun lifecycleCallback(
        activityName: String,
        activitySimpleName: String,
        lifecycleCallbackType: String) {
        val body = EventLogRequest(
            activityName,
            activitySimpleName,
            lifecycleCallbackType
        )
        apiService.lifecycleCallback(body).enqueue(ResponseCallback(activity, moshi, {}))
    }

}