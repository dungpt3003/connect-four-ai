# connect-four-ai
A simple AI for the Four In A Row game (http://theaigames.com/competitions/four-in-a-row)

## Usage

- Compile: 
   
```
cd bot
javac Field.java BotParser.java BotStarter.java
```

- Run:

```
cd .. #(to project folder)
java bot.BotStarter
```
- Sample game data can be found in `bot/samples`

## Note

- Follow the [rules](http://theaigames.com/competitions/four-in-a-row/rules) and [getting started page](http://theaigames.com/competitions/four-in-a-row/getting-started) to understand the concept of the game.

- Indexing convention:

```
|0,0|1,0|..|  |  |  |  |
|0,1|1,1|..|  |  |  |  |
|0,2|1,2|..|  |  |  |  |
|...|...|  |  |  |  |  |
|   |   |  |  |  |  |  |
|   |   |  |  |  |  |  |
```

## Contributing
- Thing to be improved: the AI method (e.g: `minimax`).

- Write automatic tests (follow the format of the sample games).

- You can release a stable version of the bot at anytime and challenge other bots to improve it.


## Copyright
Copyright © 2017 dungpt3003 
