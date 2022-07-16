# java-jepdy
Jeopardy inspired Java Quiz Game

## Getting started 

### Building & running 

This game is built using Quarkus. 

To build you need maven and a modern Java JDK.  Say Java 17

To run you need Docker since dev mode uses Quarkus test containers

Running the game locally is straight foward.
It's a matter of issuing either 

`quarkus dev` or `mvn quarkus:dev` 

Once the message ' Listening on: http://localhost:8080' has appeared the game can be played.

go to [http://localhost:8080]() to visit the welcome page.

It's important to understand that this game is intended to eventually run in the cloud
and it's intended to be one of several games. For now there's one game 
and its in development.  *(thems the breaks!)*  So select the nice blue button in the middle
and hopefully you'll see the main Java JEP'dy landing page 

At the moment there is one choice so take the next button and go **On to the Games** and Welcome to Java JEP'dy

## How to play

Note you need something to provide a buzzer.
We've had great success with this: [multibuzz](https://www.multibuzz.app/)


#### 1 Start by adding a new game.  

Take the *Add New Game* button on [http://localhost:8080/jepdy/public/games]()
and in the form that is displayed make some choices.

| Field | Description |
| --- | --- |
| Game Name | The name of the game - not particulary important |
| Rounds |  The range is 1-6. Generally leave it as 6 |
| Categories per Round | This is asking how many rows of questions to show | 
| Teams | Number of teams. Each team will have a letter of the alphabet assigned |
| Seed | This is used by a random number generator inside the clue engine to pick categories and clues|

The end of game mechanics are not enabled yet so it's better to
keep the number of rounds to 6 and stop when you want. 

#### 2 Refresh the page 
Now your game should show on the list. Note its two-word short code. 
For now, you're in control but when we get the game hosted this will become important.

#### 3 Select 'Watch'
This should open up a new window or at least a tab.
This page is the one the audience and the participants will
see. It will get updated as you run the game. 
Move this tab/window on the the screen being projected. 

#### 4 Go back to the game list and select 'Host'

This window is where most of the action takes place.
The rounds start from 0 BTW.

Determine the first category / score some how.
When ready click the corrisponding blue button and a 
panel should appear.  

#### 5 To show the Clue to the audience click the *reveal clue* button

This will show the clue on the Watch panel. 
When it's time to show the answer ..

#### 6 Click the *Reveal Answer* button

You can go back and forth with either the Show clue or Show answer button,
At some point a team has answered correctly or incorrectly
(it's best to keep it simple and not allow another team to guess too)

#### 7 Click the Team button in the winner or loser row so it remains highlighted
If there is no winner or loser select appropriately
Once you're ready to score the answer 

#### 8 Click submit button 
The clue chosen has had its score box removed and team
points have been adjusted accordingly. Now back at the host panel you can repeat this process until
you've exhausted the round or no one wants to guess anymore.

#### 8 Click the next round button 

Hopefully you get the idea!






## Technical details 

The database behind the game (In fact there are two) are spun up by Quarkus dev mode.
All the clue data is loaded into a postgres DB at startup via `enigma4j.jepdy.engine.PrepDB`.
This class loads the contents of the `clues.txt` file in the root **data** directory

### Clue Format - adding new clues etc

The clue file is simple. Empty lines are used to seperate clues and categories
Categories come first and all clues are added to the category until then next one is encountered.

To add a new Clue to a category make sure it looks like the others

`answer: What is JavaOne
attrib: anon
score: 100
clue: This west coast conference was the place to be until 2017`

Note that it's one line per attribute - no line wrapping!
Also note that *attrib* is short for attribution. 
Scores are used as a guide to ordering but are not taken *asis*








