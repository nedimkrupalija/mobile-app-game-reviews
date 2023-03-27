package ba.unsa.etf.rma.spirala

data class UserReview(
    val username: String,
    override val timestamp: Long,
    override val review: String?
):UserImpression(username, timestamp, review, null)
