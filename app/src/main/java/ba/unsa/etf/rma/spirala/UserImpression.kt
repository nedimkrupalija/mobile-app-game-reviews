package ba.unsa.etf.rma.spirala

abstract class UserImpression(
    open val userName: String,
    open val timestamp: Long,
    open val review: String?,
    open val rating: Double?
)