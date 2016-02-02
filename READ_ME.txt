Emulator: Android 5.1" WVGA 
	Screen : 5.1", 480 x 800, Large mdpi
	RAM: 512 MiB

- Flight Control
  - space craft are able to move around the screen using on screen buttons
  - left button will fire the right thrusters, move to right
  - right button will fire the let thrusters, move to left
  - up button wil fire the rocket up, move up 
  - fuel decrease everytime thrusters is fired
  - space craft will accelerate down

- Terrain and Collision Detection
  - Terrain is drawn from code
  - space craft detects ground from collision detection.
    (Stops if fuel is 0, bouncing if fuel is more than 0)

- Fuel Gauge, Score, Crash Site
  - Fuel is setup to decrease everytime a thruster is fired.
  - Thrusters are disabled when fuel is 0
  - Space craft will crash when land if the fuel is 0 while still in orbit.
    (Explosion effect will take place)

- Extras
  - background music starts playing when game starts.
  - explosion sound effects when space craft crashes.
