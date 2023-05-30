package ba.etf.rma23.projekat.data.repositories.responses

enum class AgeRatingEnum(val value: Int) {
    RP(6),
    EC(7),
    E(8),
    E10(9),
    T(10),
    M(11),
    AO(12),
    Three(1),
    Seven(2),
    Twelve(3),
    Sixteen(4),
    Eighteen(5);
     companion object {
         fun enumFromInt(value : Int) = AgeRatingEnum.values().first {it.value == value}
     }


}