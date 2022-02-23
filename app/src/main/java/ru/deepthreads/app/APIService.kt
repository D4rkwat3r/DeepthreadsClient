package ru.deepthreads.app

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import ru.deepthreads.app.models.*

interface APIService {
    @POST("v1/auth/login")
    fun login(@Body body: LoginRequest): Call<AccountResponse>
    @POST("v1/auth/register")
    fun register(@Body body: RegisterRequest): Call<AccountResponse>
    @POST("v1/auth/g-login")
    fun gLogin(@Body body: GLoginRequest): Call<AccountResponse>
    @POST("v1/upload-media") @Multipart
    fun upload(@Query("extension") type: String, @Part file: MultipartBody.Part): Call<UploadFileResponse>
    @GET("v1/chats")
    fun getChats(
        @Query("type") type: Int,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Call<ChatListResponse>
    @POST("v1/chats/public")
    fun startPublicChat(
        @Body body: PublicChatCreatingRequest
    ): Call<ChatResponse>
    @POST("v1/chats/private")
    fun startPrivateChat(@Body body: PrivateChatCreatingRequest): Call<ChatResponse>
    @GET("v1/chats/{objectId}")
    fun getChat(@Path("objectId") objectId: String): Call<ChatResponse>
    @POST("v1/chats/{objectId}/message")
    fun sendMessage(
        @Path("objectId") objectId: String,
        @Body body: MessageSendingRequest
    ): Call<MessageResponse>
    @GET("v1/chats/{objectId}/messages")
    fun getMessages(
        @Path("objectId") objectId: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Call<MessageListResponse>
    @GET("v1/chats/{objectId}/messages/{messageId}")
    fun getMessage(
        @Path("objectId") objectId: String,
        @Path("messageId") messageId: String
    ): Call<MessageResponse>
    @POST("v1/chats/{objectId}/messages/{messageId}/reply")
    fun replyMessage(
        @Path("objectId") objectId: String,
        @Path("messageId") messageId: String,
        @Body body: MessageSendingRequest
    ): Call<MessageResponse>
    @POST("v1/chats/{objectId}/me")
    fun joinChat(@Path("objectId") objectId: String): Call<EmptyOKResponse>
    @DELETE("v1/chats/{objectId}/me")
    fun leaveChat(@Path("objectId") objectId: String): Call<EmptyOKResponse>
    @GET("v1/users/{objectId}")
    fun getUser(@Path("objectId") objectId: String): Call<UserProfileResponse>
    @GET("v1/users/me")
    fun getMe(): Call<UserProfileResponse>
    @GET("v1/wall")
    fun getPosts(@Query("skip") skip: Int, @Query("limit") limit: Int): Call<PostListResponse>
    @POST("av1/wall/post")
    fun createPost(@Body body: PostCreatingRequest): Call<PostResponse>
    @GET("v1/wall/{objectId}")
    fun getPost(@Path("objectId") objectId: String): Call<PostResponse>
    @GET("v1/wall/{objectId}/likes")
    fun getLikes(
        @Path("objectId") objectId: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Call<LikeListResponse>
    @GET("v1/wall/{objectId}/likes/{likeId}")
    fun getLike(
        @Path("objectId") objectId: String,
        @Path("likeId") likeId: String
    ): Call<LikeResponse>
    @POST("v1/wall/{objectId}/like")
    fun likePost(
        @Path("objectId") objectId: String
    ): Call<LikeResponse>
    @DELETE("v1/wall/{objectId}/like")
    fun unlikePost(
        @Path("objectId") objectId: String
    ): Call<EmptyOKResponse>
    @POST("v1/event-log/activity-lifecycle-callback")
    fun lifecycleCallback(@Body body: EventLogRequest): Call<EmptyOKResponse>
    @GET("v1/users/{objectId}/wall")
    fun getUserPosts(
        @Path("objectId") objectId: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Call<PostListResponse>
    @GET("v1/me/wall")
    fun getMyPosts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Call<PostListResponse>
    @GET("v1/{objectId}/subscribers")
    fun getSubscribers(
        @Path("objectId") objectId: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Call<UserProfileListResponse>
    @GET("v1/me/subscribers")
    fun getMySubscribers(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Call<UserProfileListResponse>
    @GET("v1/{objectId}/subscribed")
    fun getSubscribed(
        @Path("objectId") objectId: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Call<UserProfileListResponse>
    @GET("v1/me/subscribed")
    fun getMySubscribed(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Call<UserProfileListResponse>
    @GET("v1/blocked-full-list")
    fun getBlocked(): Call<UserProfileListResponse>
    @GET("v1/blockers-full-list")
    fun getBlockers(): Call<UserProfileListResponse>
    @POST("v1/{objectId}/block")
    fun blockUser(
        @Path("objectId") objectId: String
    ): Call<EmptyOKResponse>
    @DELETE("v1/{objectId}/block")
    fun unblockUser(
        @Path("objectId") objectId: String
    ): Call<EmptyOKResponse>
    @POST("v1/users/{objectId}/subscription")
    fun subscribeUser(
        @Path("objectId") objectId: String
    ): Call<EmptyOKResponse>
    @DELETE("v1/users/{objectId}/subscription")
    fun unsubscribeUser(
        @Path("objectId") objectId: String
    ): Call<EmptyOKResponse>
    @POST("v1/comments/{objectId}")
    fun createComment(
        @Body body: CommentingRequest,
        @Path("objectId") objectId: String,
        @Query("type") type: Int
    ): Call<CommentResponse>
    @GET("v1/comments/{objectId}/{commentId}")
    fun getComment(
        @Path("objectId") objectId: String,
        @Path("commentId") commentId: String,
        @Query("type") type: Int
    ): Call<CommentResponse>
    @GET("v1/comments/{objectId}")
    fun getComments(
        @Path("objectId") objectId: String,
        @Query("type") type: Int,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Call<CommentListResponse>
    @POST("v1/comments/{objectId}/{commentId}/like")
    fun likeComment(
        @Path("objectId") objectId: String,
        @Path("commentId") commentId: String,
        @Query("type") type: Int
    ): Call<LikeResponse>
    @DELETE("v1/comments/{objectId}/{commentId}/like")
    fun unlikeComment(
        @Path("objectId") objectId: String,
        @Path("commentId") commentId: String,
        @Query("type") type: Int
    ): Call<EmptyOKResponse>
    @GET("v1/comments/{objectId}/{commentId}/likes/{likeId}")
    fun getCommentLike(
        @Path("objectId") objectId: String,
        @Path("commentId") commentId: String,
        @Path("likeId") likeId: String,
        @Query("type") type: Int
    ): Call<LikeResponse>
    @GET("v1/comments/{objectId}/{commentId}/likes")
    fun getCommentLikes(
        @Path("objectId") objectId: String,
        @Path("commentId") commentId: String,
        @Query("type") type: Int,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Call<LikeListResponse>
}