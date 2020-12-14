# OthelloBot
Tournament winning bot that plays Othello using an alpha-beta algorithm written in java.  
Written by William Munsch with guidance by Dr. Mariette Cameron.

## Description
This bot uses an alpha-beta pruning algorithm with move ordering heuristics
and a hand crafted evaluation function.  
Uses a bitboard representation for maximum efficiency in the search algorithm,
one 64-bit integer (long) for white pieces and one for black pieces. A bit value of 0 represents 
an empty board space, and a bit value of 1 represents a piece of the respective color.

## How to use & input
Download the WMunschOthelloBot.jar file and navigate to the directory it's in with the console and use the command

java -jar WMunschOthelloBot.jar

All input is done via console commands.
The very first input for any game must be either  
I W  
or  
I B  
This determines which color the bot will initialize as.  
You, the player, will be the other color.
Every input after that must be a valid move of the form  
COLOR letter number  
For example, if the bot was initialized as white, a valid first move for black would be   
B e 6  
The board representation will be printed after each move.

## Evaluation function
The following is the function that scores each board state:

#### (pm-em)*73 - (pf-ef)*57 + (ps-es)*141

Where  
pm = the number of possible moves for the player  
em = the number of possible moves for the enemy  
pf = the number of frontier pieces for the player  
ef = the number of frontier pieces for the enemy  
ps = the number of stable pieces for the player  
es = the number of stable pieces for the enemy  

## Research notes and statistics
The following explains the move ordering heuristics used and how effective they are.
Each heuristic was tested given three different board states, one in an early game position, one in a mid game position, and one in a late game position. The
speed factor measures how fast it is compared to the alpha-beta search with no move ordering heuristics.  
These tests are available as unit tests in the tests folder.

### Shallow move ordering : This is the process of evaluating each move and sorting the list of possible moves based on that score (best gets searched first) before continuing on with the alpha-beta search.
#### Early game : 
Vanilla Alpha Beta:  
Time to finish depth 12 : 28915ms  
Total moves evaluated : 47,035,595  

Shallow Move Alpha Beta:  
Time to finish depth 12 : 11025ms  
Total moves evaluated : 7,257,581  
Speed factor: 2.62

#### Mid game :
Vanilla Alpha Beta:  
Time to finish depth 10 : 94203ms  
Total moves evaluated : 145,460,821  

Shallow Move Alpha Beta:  
Time to finish depth 10 : 5221ms  
Total moves evaluated : 3,201,343  
Speed factor: 18.04

#### Late game :
Vanilla Alpha Beta:  
Time to finish depth 16 : 7949ms  
Total moves evaluated : 5,827,974  

Shallow Move Alpha Beta:  
Time to finish depth 16 : 8139ms  
Total moves evaluated : 2,028,643  
Speed factor: 0.98

### Killer move heuristic : uses iterative deepening to find the best move and path of the previous furthest depth, then searches the leafs of that path first. The previous move's path is saved using pointers. Increases the amount of moves pruned without affecting accuracy.
#### Early game :
Vanilla Alpha Beta :  
Time to finish depth 12 : 31811ms  
Total moves found : 51,206,668  

Killer Move Alpha Beta :  
Time to finish depth 12 : 12815ms  
Total moves found : 20,799,797  
Speed factor : 2.48  

#### Mid game :
Vanilla Alpha Beta :  
Time to finish depth 10 : 101252ms  
Total moves found : 155,468,526   

Killer Move Alpha Beta:  
Time to finish depth 10 : 69476ms  
Total moves found : 109,888,020  
Speed factor : 1.46  

#### Late game :
Vanilla Alpha Beta :  
Time to finish depth 16 : 24095ms  
Total moves found : 20,336,377  

Killer Move Alpha Beta :  
Time to finish depth 16 : 19350ms  
Total moves found : 16,837,506    
Speed factor: 1.25   

### Transposition table : stores the evaluated board state in a hash map with the hashed board state as the key and the board and board score as the value. This saves the time of evaluating nodes by finding duplicate board states. The hash map is cleared between turns and iterations. Suffers from key collision problems.
#### Early game : 
Vanilla Alpha Beta :  
Time to finish depth 12 : 32033ms  
Total moves found : 51,206,668  

Transposition :  
Time to finish depth 12 : 29220ms  
Total moves found : 36,586,390  
Total moves added to TT : 368,636  
Total TT cuts : 23,185  
Speed factor: 1.096  

#### Mid game :
Vanilla Alpha Beta :  
Time to finish depth 10 : 103323ms  
Total moves found : 155,468,526  

Transposition :  
Time to finish depth 10 : 69442ms  
Total moves found : 90,706,977  
Total moves added to TT : 321,461  
Total TT cuts : 45,946  
Speed factor: 1.49  

#### Late game :
Vanilla Alpha Beta :  
Time to finish depth 16 : 23294ms  
Total moves found : 20,336,377  

Transposition :  
Time to finish depth 16 : 15968ms  
Total moves found : 9,594,036  
Total moves added to TT : 2,593,882  
Total TT cuts : 336,901  
Speed factor: 1.46  

### All three heuristics combined compared to the vanilla alpha-beta:
#### Early game :
Vanilla :  
Time to finish depth 12 : 31432ms  
Total moves found : 51,206,668  

All 3 heuristics :  
Time to finish depth 12 : 5993ms  
Total moves found : 3,784,678  
Total moves added to TT : 63,546  
Total TT cuts : 1,582  
Speed factor:  5.24  

#### Mid game :
Vanilla :  
Time to finish depth 10 : 100389ms  
Total moves found : 155,468,526  

All 3 heuristics :  
Time to finish depth 10 : 5205ms  
Total moves found : 2,900,364  
Total moves added to TT : 37,578  
Total TT cuts : 2,634  
Speed factor:  19.29  

#### Late game :
Vanilla :  
Time to finish depth 16 : 22996ms  
Total moves found : 20,336,377  

All 3 heuristics :   
Time to finish depth 16 : 8275ms  
Total moves found : 2,431,134  
Total moves added to TT : 900,763  
Total TT cuts : 90,636  
Speed factor :  2.78
