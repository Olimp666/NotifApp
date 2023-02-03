package com.example.notifapp.presentation


//[notificationid, userid, description, status(approved, declined, pending)]

@kotlinx.serialization.Serializable
data class Notification(
    val notificationId: Int,
    val userId: Int,
    val description: String,
    val status: String
)
//
//interface Subscriber {
//    fun Update(json: String)
//}
//
//class StatusListener : Subscriber {
//    var text = "[]"
//    override fun Update(json: String) {
//        text = json
//    }
//}
//
//class NotifPublisher() {
//    var subs = mutableListOf<Subscriber>()
//    var text = "[]"
//    fun addSubscriber(sub: Subscriber) {
//        subs.add(sub)
//    }
//
//    fun notifySubscribers() {
//        subs.forEach {
//            it.Update(text)
//        }
//    }
//}