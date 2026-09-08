# CSE102L – Computer Programming II Laboratory

Akdeniz University - Department of Computer Engineering
Course: CSE102L (Computer Programming II Lab)
Language: Java

This repository contains my lab assignments for the CSE102L course. Each `Lab X` folder has the official instruction PDF and the Java source code I wrote for that lab.

## Labs

| Lab | Title | Description |
|---|---|---|
| 1 | The Silver Spiral | Fills an N×N matrix with Lucas numbers in a counter-clockwise spiral pattern and calculates the sum of the main and anti-diagonals. |
| 2 | Backup System | Simulates saving data files across multiple hard drives. Handles splitting a file across drives, skipping unhealthy or null drives, and reporting incomplete saves. |
| 3 | Tower Defense | Models a sniper tower defending against waves of enemies. Calculates hit range, damage, critical shots, ammo usage, and reloading. |
| 4 | Optical Facility Simulation | Simulates laser beams passing through a grid of optical elements (mirrors, prisms, frosted glass), tracking how each element changes the beam's angle and energy. |
| 5 | Factory Logistics Simulator | Simulates an assembly line where tech modules (servers, drones) pass through machines, then get shipped. Calculates manufacturing cost and shipping fees. |
| 6 | Library Management System | Implements a small library system: adding books, marking them as read/unread, searching by ID or type, and generating statistics per book type (Novel, Biography, TextBook). |
| 7 | Space Engine | Simulates a small universe with planets and stars: adding objects with collision detection, calculating distance and gravitational force between objects, and sorting objects by distance from the center. |

## How to Run

Each lab has a Java file with a `main` method. From inside the lab's folder:

```bash
javac *.java
java <ClassNameWithMain>
```

For example, for Lab 1:

```bash
javac SilverSpiral.java
java SilverSpiral
```
