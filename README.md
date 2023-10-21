#Tetris 
Overview:

This project is an implementation of the  Tetris game in Java, featuring various components that make it flexible and extensible for other grid-based games like Tile Game and Tic-Tac-Toe.

Components

ArrowListener

The ArrowListener class is responsible for establishing the methods that respond to the four arrow keys: up, down, right, and left. These methods handle user input for the Tetris game.

Block

The Block class maintains information about a block. It is designed to be extremely flexible and can be adapted for use in various games such as Tile Game, Tetris, and Tic-Tac-Toe.

Location

The Location class was given and is from the AP curriculum. is included in the repository for readability and practical purposes. It helps represent the location of blocks on the grid.

MyBoundedGrid

The MyBoundedGrid class represents the grid on which the Tetris game is played. It is a rectangular grid with limited rows and columns, providing a structured environment for the Tetris blocks.

Tetrad

The Tetrad class creates a tetrad of one of seven types: I, T, O, L, J, S, Z. The Tetrad can test if it can move forward, and if it cannot, the Tetrad becomes immobile. This class adds an element of strategy to the game as players aim to position and rotate the Tetrad effectively.

Tetris

The Tetris class is the main driver for playing the Tetris game. It handles the instantiation of Tetrad types (I, T, O, L, J, S, Z) and manages the game dynamics. The up arrow key rotates the current Tetrad; the right, left, and down arrow keys move the Tetrad right, left, and down one cell, respectively. Additionally, the space bar brings the Tetrad as low down the board as possible.
