# Maze solving
## Introduction
This is a Java implementation of several algorithms with the purpose of solving mazes.
It is based upon [Mike Pound's mazesolving](https://github.com/mikepound/mazesolving) application, which is written in Python.
This implementation is written without any knowlege about the Python original, but does use the same example input files.
I was inspired to try this myself after watching his excellent [Youtube video on the subject](https://www.youtube.com/watch?v=rop0W4QDOUI).
You should definitly check out the [Computerphile Youtube channel](https://www.youtube.com/user/Computerphile) for more intersting videos.
2 of the algorithms used here are explained very well in videos by him (Dijkstra and A*).

## Algorithms used
At the time of writing there are 3 basic algorithms I implemented for solving mazes:
1. Recursive
1. Dijkstra
1. A*

There are 4 different resolvers:
1. recursive solver
1. recursive solver for nodes
1. Dijkstra solver for nodes
1. A* solver for nodes

The reasoning behind the 'nodes' is explained in the original Youtube video.

## System Requirements
This application requires a JDK 1.8, although no Java 8 specific features are used, so you could compile and run using Java 7.
I used OpenJDK 1.8, 64-bit.
I tried to make the implementation as fast as possible, while also consuming as little RAM as possible.
I'm sure more improvements can be made, though.
The larger mazes however, do require you to set a 3GB heap size (-Xmx3072m).
The application will also write the results as images to disk, all combined this generates an additional 262MB woth of image files (PNG).
On my machine it takes 419 seconds in total to run through every image, most of that time is spent on setting up the nodes and writing the result.

## Pitfalls I encountered
First of all: the standard implementation for writing PNG images is *slow*, this is especially noticable on larger images.
This is because the highest level of compression is used, which cannot be changed.
To speed up the writing of the output images, I needed to lower the compression.
So I had to turn to an alternative library.
Implementing this naively takes up a *lot* of memory, I cut that down by quite a large amount by being a bit smarter about how to keep the maze.

## More algorithms
I'll add more algorithms and improve the code when I feel like it.
I tink I'll let this rest for a while now and do some other things.
