package ba.unsa.etf.rma.spirala

data class UserReview(
    val username: String,
    override val timestamp: Long,
    val review: String
):UserImpression(username, timestamp)
