package ba.unsa.etf.rma.spirala

class GameData {
    companion object{
        fun getAll(): List<Game>{
            return listOf(
                Game("CS:GO",
                    "PC, PS, XBOX", "21.08.2012", 9.3,
                    "csgoCover", "Mature","Valve",
                    "Steam","Tactical Shooter","Counter-Strike: Global Offensive (CS:GO) " +
                            "is a 2012 multiplayer tactical first-person shooter developed by Valve and Hidden Path Entertainment. " +
                            "It is the fourth game in the Counter-Strike series. Developed for over two years, " +
                            "Global Offensive was released for OS X, PlayStation 3, Windows, " +
                            "and Xbox 360 in August 2012, and for Linux in 2014. " +
                            "Valve still regularly updates the game, both with smaller " +
                            "balancing patches and larger content additions",null),
                Game("Call of Duty: Warzone",
                    "PC, PS, XBOX", "10.03.2020", 9.2,
                    "warzoneCover", "Mature","Raven Software, Infinity Ward",
                    "Activision","FPS Battle Royale","The Battle Royale mode" +
                            " is similar to other titles in the genre where players compete in a" +
                            " continuously shrinking map to be the last player remaining. Players " +
                            "parachute onto a large game map, where they encounter other players. " +
                            "As the game progresses and players are eliminated, the playable area " +
                            "shrinks, forcing the remaining players into tighter spaces. In Warzone, " +
                            "the non-playable areas become contaminated with a yellow gas that depletes" +
                            " health and eventually kills the player if they do not return to the safe" +
                            " playable area",null),
                        Game("Need For Speed: Most Wanted",
                "PC, PS, XBOX", "11.11.2005", 8.2,
                "nfsmwCover", "Mild Violence","Electronic Arts",
                "Electronic Arts","Racing","In the game, players take part " +
                                    "in illegal street races across Most Wanted's setting, utilizing" +
                                    " a variety of licensed real-world cars (available at the time of" +
                                    " the game's development and release) that can be upgraded and" +
                                    " customized with new parts while contending with the involvement " +
                                    "of the police in their efforts to impede the player.",null),
                Game("PlayerUnkown's Battlegrounds",
                    "PC", "23.03.2017", 8.5,
                    "pubgCover", "Violent 16+","PUBG Studios",
                    "KRAFTON, Tencent Games","FPS Battle Royale","PUBG is a " +
                            "player versus player shooter game in which up to one hundred players " +
                            "fight in a battle royale, a type of large-scale last man standing deathmatch " +
                            "where players fight to remain the last alive. Players can choose" +
                            " to enter the match solo, duo, or with a small team of up to four people." +
                            " The last person or team alive wins the match.",null),
                Game("League of Legends",
                    "PC", "27.10.2009", 7.6,
                    "lolCover", "Teen","Riot Games",
                    "Riot Games","Action role-playing game","eague of Legends " +
                            "is a multiplayer online battle arena (MOBA) game in which the player" +
                            " controls a character (\"champion\") with a set of unique abilities from " +
                            "an isometric perspective.[2][3] As of 2023, there are over 160 champions" +
                            " available to play.[4] Over the course of a match, champions gain levels" +
                            " by accruing experience points (XP) through killing enemies. Items can" +
                            " be acquired to increase champions' strength,[6] and are bought with gold," +
                            " which players accrue passively over time and earn actively by " +
                            "defeating the opposing team's minions,champions, or defensive structures.",null),
                Game("World of Tanks",
                    "PC, PS, XBOX", "12.08.2010", 9.0,
                    "wotCover", "Teen","Wargaming",
                    "Wargaming","Action role-playing game","The player takes " +
                            "control of a single armored tank or self-propelled artillery vehicle of " +
                            "their choice and is placed into a battle on a random map. The player has" +
                            " control over the vehicle's movement, firing, and can communicate with" +
                            " allied players and all the other players through typed or voice chat. " +
                            "A simple random match is won either by destroying all vehicles on the " +
                            "opposing team or capturing the opposing team's base by staying in it " +
                            "for long enough without being damaged by another tank. ",null),
                Game("Counter-Strike 1.6",
                    "PC", "12.09.2003", 9.7,
                    "cs1.6Cover", "Teen","Valve",
                    "Valve Corp.","First Person Shooter","Counter-Strike " +
                            "is an objective-based, multiplayer tactical first-person shooter." +
                            " Two opposing teams—the Terrorists and the Counter Terrorists—compete " +
                            "in game modes to complete objectives, such as securing a location to " +
                            "plant or defuse a bomb and rescuing or guarding hostages",null),
                Game("Fortnite",
                    "PC, PS, XBOX", "21.07.2021", 8.6,
                    "fortniteCover", "Teen","Epic Games",
                    "Epic Games, Warner Bros","Battle Royale","Fortnite is an " +
                            "online video game developed by Epic Games and released in 2017. It is" +
                            " available in three distinct game mode versions that otherwise share" +
                            " the same general gameplay and game engine: Fortnite Battle Royale, " +
                            "a free-to-play battle royale game in which up to 100 players fight to " +
                            "be the last person standing; Fortnite: Save the World, a cooperative " +
                            "hybrid tower defense-shooter and survival game in which up to four players" +
                            " fight off zombie-like creatures and defend objects with traps and " +
                            "fortifications they can build; and Fortnite Creative, in which players" +
                            " are given complete freedom to create worlds and battle arenas.",null),
                        Game("Among Us",
                "PC, PS, XBOX", "15.06.2018", 9.0,
                "amoungusCover", "Everyone 10+","InnerSloth LLC.",
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
                                    "resolve it in time).",null),
                Game("Rocket League",
                    "PC, PS, XBOX", "15.06.2018", 9.0,
                    "rlCover", "Everyone","Psyonix",
                    "Psyonix .","Racing football game","Rocket League's " +
                            "gameplay is largely the same as that of its predecessor, Supersonic " +
                            "Acrobatic Rocket-Powered Battle-Cars. Players control a rocket-powered " +
                            "car and use it to hit a ball that is much larger than the cars towards " +
                            "the other team's goal area to score goals, in a way that resembles indoor " +
                            "soccer, with elements reminiscent of a demolition derby.",null)



            )
        }


    }
}