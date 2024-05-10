# Plan
## map
- split into parts
- each part has a designated floor and ceiling height
- implement startPos in map
- adjust points based on startPos
- get adjacent points function
- add a modifier in map file for walls that are actually portals

https://www.desmos.com/calculator/i7tboi7rrg

## camera
if point is on screen find on screen position\
else:
- check adjacent points for a point on screen
- if one point is on screen find the point where the line intersects field of view
- if both points are on screen find both intersection points
drawing vertical lines:\
- go through each pixel of the screen and find y of the upper and lower lines
- draw vertical line between these two points
- go from closer walls to farther walls and keep track of how much of screen is covered
- only draw the parts of the farther walls that are visible
- stops when entire screen is covered

alternative:
- draw points if they are on screen, or if adjacent to a point on screen

Either draw floors or draw a background on screen beforehand

movement: shift then rotate (not sure if this matters but apparently I thought it did)\
rotations: https://www.desmos.com/calculator/vmhqzac41d

use processing `Image.get()` to grab vertical columns of pixels to render textures

put things to render in a list then sort the list by distance from player (average z coord)?