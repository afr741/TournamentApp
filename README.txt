# CS3716
3716 Project Volleyball Tournament Manager


There is also .zip folder called project.zip which contains all source files as well

Group Members:

-Ryan Ley
-Donald Ryan
-Stephen Pelley
-Anush Rakhmatov
-Caelan Hunt


About our program:

The main objective of our program is to facilitate tournament organizers and coaches with keeping track of their teams and tournaments.
The program has 4 different user modes, Organizer, Coach, Referee, and Spectator. All 4 have different permissions within the program.
Oraganizers basically have access and have the ability to create/delete anything they would like. This includes tournaments, teams, players and generating brackets for a specific tournament. 

Coaches have the ability to register their teams for tournaments that have already been created by an organizer. After that they have the ability to view a tournament (View the bracket) if it has been generated for that specific tournament. They also can remove teams from tournaments.

Referees only have the ability to view Brackets and update scores.

Spectators only have the ability to view Brackets, they will not be able to update scores.


How to run our program?

It's very easy to run our program! You have three options to choose from, you can run the volleyballProgram.jar which is located in the main folder of the github repository. In your favorite IDE, you can compile all the classes and run volleyBallProgramRunner.java which is located in the common package. This will run the program while pulling data that has been saved to file. The third and final option is to run volleyballTour.java, this class inlcudes a main method that will run the entire program while creating a mock data tournament (The first tournament will already be initialized with teams and players). This is a great option for testing without having to create and add more tournaments and teams.

To actually use the program, you are presented with a login screen at the beginning where you can choose your user. You can always return to this screen by clicking "logout" in the file menu. "Save" and "Quit" in the file menu do exactly as they sound, quit also saves before it exits the program. Once you are presented to your homepage, the active tournaments are on the left hand side and your available options are on the right. Depending on the option you choose, you may need to select torunament from the list on the left first. Don't worry, it will let you kmow if you forget!

To save any changes you want to make, just click the option in the file menu.


Unfortunately there are a few known bugs in our software, we are hoping to have them ironed out by the time the next iteration of the program is released! We have included a list of them below for your convenience.

-Once you add players, the players list not clear for the text team
-Occasionally the tournaments can rename themselves but they keep their index in the list the same, just keep  that in mind if ithis happens.
-Sometimes you cannot edit matches from a divisions tournament, it will throw an exception saying that the match is unavaialable. This can usually be solved by starting the program over and getting mock data.
-Drop down boxes from the view bracket page can over populate themselves over and over again


Hope you enjoy using our program!!

The Team.





