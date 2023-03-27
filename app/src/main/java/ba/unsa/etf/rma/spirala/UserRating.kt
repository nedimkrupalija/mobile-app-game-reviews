package ba.unsa.etf.rma.spirala

data class UserRating(
    val username: String,
    override val timestamp: Long,
    override val rating: Double?
):UserImpression(username, timestamp,null, rating)

