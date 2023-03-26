package ba.unsa.etf.rma.spirala

data class UserReview(
    override val username: String,
    override val timestamp: Long,
    val review: String
):UserImpression()
