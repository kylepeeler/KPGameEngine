clean:
	rm -rf ./bin
build: clean $(res)
	mkdir ./bin
	javac -d ./bin ./src/io/kylepeeler/GameEngine/*.java ./src/io/kylepeeler/GameEngine/gfx/*.java ./src/io/kylepeeler/Game/*.java
	cp -r ./res/ ./bin/
run: build
	java -cp bin io.kylepeeler.Game.BasicGame