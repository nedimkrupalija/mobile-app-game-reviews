package ba.unsa.etf.rma.spirala

import java.sql.Date
import java.sql.Time


class GameData {
    companion object{
        fun getAll(): List<Game>{
            return listOf(
                Game("CS:GO",
                    "PC, PS, XBOX", "21.08.2012", 9.3,
                    "csgo_cover", "Mature","Valve",
                    "Steam","Tactical Shooter","Counter-Strike: Global Offensive (CS:GO) " +
                            "is a 2012 multiplayer tactical first-person shooter developed by Valve and Hidden Path Entertainment. " +
                            "It is the fourth game in the Counter-Strike series. Developed for over two years, " +
                            "Global Offensive was released for OS X, PlayStation 3, Windows, " +
                            "and Xbox 360 in August 2012, and for Linux in 2014. " +
                            "Valve still regularly updates the game, both with smaller " +
                            "balancing patches and larger content additions",
                    mutableListOf(
                        UserRating("csgoGamer", Date.valueOf("2023-03-03").time,7.0),
                        UserReview("top gun", Date.valueOf("2023-03-28").time,"I've been playing this since Half Life, " +
                                "and it's only got better along the journey. Nothing like a quick on-line session to relieve some of the daily stress after work!"),
                        UserRating("Nedim Krupalija",Date.valueOf("2020-01-21").time,10.0),
                        UserReview("generic name", Date.valueOf("2013-09-05").time,"This game is wonderful mostly because it " +
                                "can range for the perfect game for low skilled player, but also has" +
                                " the depth for a professional player. If you want to " +
                                "introduce anyone to gaming, this should be the game of" +
                                " your choice for 90% of the time. "),
                        UserRating("Another generic name", Date.valueOf("2018-01-01").time,4.0)
                    )),
                Game("Call of Duty: Warzone",
                    "PC, PS, XBOX", "10.03.2020", 9.2,
                    "warzone_cover", "Mature","Raven Software, Infinity Ward",
                    "Activision","FPS Battle Royale","The Battle Royale mode" +
                            " is similar to other titles in the genre where players compete in a" +
                            " continuously shrinking map to be the last player remaining. Players " +
                            "parachute onto a large game map, where they encounter other players. " +
                            "As the game progresses and players are eliminated, the playable area " +
                            "shrinks, forcing the remaining players into tighter spaces. In Warzone, " +
                            "the non-playable areas become contaminated with a yellow gas that depletes" +
                            " health and eventually kills the player if they do not return to the safe" +
                            " playable area", mutableListOf(
                            UserReview("Greg Gregor",Date.valueOf("2021-03-20").time,"Call Of Duty is a " +
                                    "fantastic and revolutionary first instalment in the forever changing" +
                                    " and advancing Call Of Duty series. This game is in fact a true and " +
                                    "undeniable legend in the gaming industry"),
                            UserRating("GG",Date.valueOf("2022-03-05").time,2.0),
                            UserReview("Cameron", Date.valueOf("2022-01-06").time,"It has f" +
                                    "inally been proven and confirmed that the makers of COD Modern Warfare " +
                                    "Warzone suppress XBox players by giving PS4 and PC players a large " +
                                    "advantage in the game. "),
                            UserReview("Alex", Date.valueOf("2021-06-06").time,"Warzone is " +
                                    "clearly a popular game, however I've laid out some pros and cons to " +
                                    "allow you to make a decision of whether you want to play this game" +
                                    " or not."),
                            UserRating("Nikolaj", Date.valueOf("2023-03-05").time,9.5)
                            )),
                        Game("Need For Speed: Most Wanted",
                "PC, PS, XBOX", "11.11.2005", 8.2,
                "nfsmw_cover", "Mild Violence","Electronic Arts",
                "Electronic Arts","Racing","In the game, players take part " +
                                    "in illegal street races across Most Wanted's setting, utilizing" +
                                    " a variety of licensed real-world cars (available at the time of" +
                                    " the game's development and release) that can be upgraded and" +
                                    " customized with new parts while contending with the involvement " +
                                    "of the police in their efforts to impede the player.",emptyList()),
                Game("PlayerUnkown's Battlegrounds",
                    "PC", "23.03.2017", 8.5,
                    "pubg_cover", "Violent 16+","PUBG Studios",
                    "KRAFTON, Tencent Games","FPS Battle Royale","PUBG is a " +
                            "player versus player shooter game in which up to one hundred players " +
                            "fight in a battle royale, a type of large-scale last man standing deathmatch " +
                            "where players fight to remain the last alive. Players can choose" +
                            " to enter the match solo, duo, or with a small team of up to four people." +
                            " The last person or team alive wins the match.",emptyList()),
                Game("League of Legends",
                    "PC", "27.10.2009", 7.6,
                    "lol_cover", "Teen","Riot Games",
                    "Riot Games","Action role-playing game","eague of Legends " +
                            "is a multiplayer online battle arena (MOBA) game in which the player" +
                            " controls a character (\"champion\") with a set of unique abilities from " +
                            "an isometric perspective.[2][3] As of 2023, there are over 160 champions" +
                            " available to play.[4] Over the course of a match, champions gain levels" +
                            " by accruing experience points (XP) through killing enemies. Items can" +
                            " be acquired to increase champions' strength,[6] and are bought with gold," +
                            " which players accrue passively over time and earn actively by " +
                            "defeating the opposing team's minions,champions, or defensive structures.",emptyList()),
                Game("World of Tanks",
                    "PC, PS, XBOX", "12.08.2010", 9.0,
                    "wot_cover", "Teen","Wargaming",
                    "Wargaming","Action role-playing game","The player takes " +
                            "control of a single armored tank or self-propelled artillery vehicle of " +
                            "their choice and is placed into a battle on a random map. The player has" +
                            " control over the vehicle's movement, firing, and can communicate with" +
                            " allied players and all the other players through typed or voice chat. " +
                            "A simple random match is won either by destroying all vehicles on the " +
                            "opposing team or capturing the opposing team's base by staying in it " +
                            "for long enough without being damaged by another tank. ",emptyList()),
                Game("Counter-Strike 1.6",
                    "PC", "12.09.2003", 9.7,
                    "cs_cover", "Teen","Valve",
                    "Valve Corp.","First Person Shooter","Counter-Strike " +
                            "is an objective-based, multiplayer tactical first-person shooter." +
                            " Two opposing teams—the Terrorists and the Counter Terrorists—compete " +
                            "in game modes to complete objectives, such as securing a location to " +
                            "plant or defuse a bomb and rescuing or guarding hostages",emptyList()),
                Game("Fortnite",
                    "PC, PS, XBOX", "21.07.2021", 8.6,
                    "fortnite_cover", "Teen","Epic Games",
                    "Epic Games, Warner Bros","Battle Royale","Fortnite is an " +
                            "online video game developed by Epic Games and released in 2017. It is" +
                            " available in three distinct game mode versions that otherwise share" +
                            " the same general gameplay and game engine: Fortnite Battle Royale, " +
                            "a free-to-play battle royale game in which up to 100 players fight to " +
                            "be the last person standing; Fortnite: Save the World, a cooperative " +
                            "hybrid tower defense-shooter and survival game in which up to four players" +
                            " fight off zombie-like creatures and defend objects with traps and " +
                            "fortifications they can build; and Fortnite Creative, in which players" +
                            " are given complete freedom to create worlds and battle arenas.",emptyList()),
                        Game("Among Us",
                "PC, PS, XBOX", "15.06.2018", 9.0,
                "amoungus_cover", "Everyone 10+","InnerSloth LLC.",
                "InnerSloth LLC.","Survival party game","Among Us is a " +
                                    "multiplayer game for four to fifteen players.[12] Up " +
                                    "to three players are randomly[13] and secretly chosen to" +
                                    " be the Impostor(s) each round. As of 2021, four playable" +
                                    " maps are available, a spaceship called \"The Skeld\", " +
                                    "an office building called \"MIRA HQ\", a planet base called" +
                                    " \"Polus\",[14] or \"The Airship\", a setting from Innersloth's " +
                                    "Henry Stickmin series.[15][16] The Crewmates can win the game one " +
                                    "of two ways; either by completing all assigned tasks or by ejecting" +
                                    " all Impostors. Impostors can likewise win in two ways; either " +
                                    "by killing or ejecting all Crewmates,[e] or by sabotaging a" +
                                    " critical system on the map (provided the Crewmates do not " +
                                    "resolve it in time).",emptyList()),
                Game("Rocket League",
                    "PC, PS, XBOX", "15.06.2018", 9.0,
                    "rl_cover", "Everyone","Psyonix",
                    "Psyonix .","Racing football game","Rocket League's " +
                            "gameplay is largely the same as that of its predecessor, Supersonic " +
                            "Acrobatic Rocket-Powered Battle-Cars. Players control a rocket-powered " +
                            "car and use it to hit a ball that is much larger than the cars towards " +
                            "the other team's goal area to score goals, in a way that resembles indoor " +
                            "soccer, with elements reminiscent of a demolition derby.",emptyList())



            )
        }
        /*
        val games: ArrayList<Game> = arrayListOf()
        games.addAll(getAll())
        val game = games.find {game -> name == game.title}
         */
        fun getDetails(title: String): Game?{
            return getAll().find { game -> game.title == title }
        }


    }
}