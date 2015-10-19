# Kalah

Program a (web) application that runs a game of 6-stone Kalah.

This application should enable to let 2 human players play the game; there is no AI required.
It doesn't need a fancy web interface or front end.

Each of the two players has six pits in front of him/her. To the right of the six pits, each player
has a larger pit, his Kalah or house. At the start of the game, six stones are put In each pit.

The player who begins picks up all the stones in any of their own pits, and sows the stones on to
the right, one in each of the following pits, including his own Kalah. No stones are put in the
opponent's' Kalah. If the players last stone lands in his own Kalah, he gets another turn. This can
be repeated any number of times before it's the other player's turn.
when the last stone lands in an own empty pit, the player captures this stone and all stones in
the opposite pit (the other players' pit) and puts them in his own Kalah.

The game is over as soon as one of the sides run out of stones. The player who still has stones
in his/her pits keeps them and puts them in his/hers Kalah. The winner of the game is the player
who has the most stones in his Kalah.
