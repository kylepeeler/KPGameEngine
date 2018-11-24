# KP Game Engine v0.1
### Written by Kyle Peeler

Basic game engine written in Java with minimal dependencies written for my CSCI 437 Game Development class. Games are implemented by implementing methods defined in AbstractGame class. See BasicGame in the Game directory for an example game. Source code of the GameEngine is found within `src/io.kylepeeler/GameEngine`. Resources for the game are found in the `/res` directory.

## Example Game Screenshot:
![example game screenshot](ExampleGameScreenshot.png)

## Compiling the example game
If you only need to run the game, it is advised you skip this step as compiling can be a tad bit complicated...

In order to compile this project, we use a java builder called [ant](https://ant.apache.org/). It can be downloaded using a system package manager (e.g. `apt install ant` or `brew install ant`).

Once you are sure ant is installed (you can test this with `ant -v`), `cd` into the base directory of the project and simply run `ant`. It should generate a `.jar` file within the `/out/artifacts/KPGameEngine_jar` directory as `KPGameEngine.jar`.

## Running the example game

To run the game without compiling, you can use the `.jar` file included in the `/out/artifacts/KPGameEngine_jar` directory using the command `java -jar /out/artifacts/KPGameEngine_jar/KPGameEngine.jar`