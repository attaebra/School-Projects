#CS/COE 1520 Assignment 3

Posted:  July 14, 2016

***Due:  July 25, 2016 @ 11:59PM***

##Goal:
To gain experience using AJAJ and JSON by building a website to host and manage multiple chat rooms.

##High-level description:
You will be writing a website to host and manage chat rooms.
Users can be in only one chat room at a time.

##Specifications:
1.  When visiting the page for the first time, users should be given the chance to create an account or login
1.  Once successfully logged in, the user should be given a list of possible chat rooms to join, or a message stating that none currently exist.
	The User should also have the option to create a new chat room.
1.  Once in a chat room, the user should be shown the history of messages for that chat room, as well as be kept up to date as messages are send to the chat room.
	The user should also have the option to post a new message to the chat room.
	The user should further be given a link to leave the chat room.
	* You must use AJAJ and JSON to update the list of messages posted to the chat room, and to post new messages to the chat room..
	* All AJAJ chat updates should send only *new* messages to the user.  The user should not receive the full list of chat messages with every AJAJ update as this could grow quite large over time.
		* We discussed several approaches to achieving this in class, you are free to use any of those methods.
	* You must be sure that your application does not display "phantom" messages to the user.
		* I.e., All users in the same chat room should see the same messages in the same order and new messages should always appear at the end of the chat log, never in the middle of the chat log.
	* You should take a polling approach to ensure that new messages are always available to the user.
		Your application should have a 1 second time between polls.
1.  Once a user leaves the chat room, they should again be shown a list of potential chat rooms to join (or a message if none exist).
1.  The user should always be presented with link to log out while they are logged in.
1.  Data management
	*  All data for your application should be stored in an SQLite database named "chat.db" using SQLAlchemy's ORM and the Flask-SQLAlchemy extension.
1.  You must build your website using JavaScript, JSON, AJAJ, Python 3.3 or greater, Flask 0.11, SQLAlchemy, and the Flask-SQLAlchemy extension.

##Submission Guidelines:
*  **DO NOT SUBMIT** any IDE package files.
*  Do not include chat.db in your submitted repository.
*  You must name the main flask file for your site "chat.py", and place it in the root directory of your repository.
*  You must be able to run your application by setting the FLASK_APP environment variable to your chat.py and running "flask run"
*  You must be able to initialize your database by setting the FLASK_APP environment variable to your chat.py and running "flask initdb"
*  You must fill out info_sheet.txt.
*  Be sure to remember to push the latest copy of your code back to your GitHub repository before the the assignment is due.  At 12:00 AM July 26, the repositories will automatically be copied for grading.  Whatever is present in your GitHub repository at that time will be considered your submission for this assignment.

##Additional Notes/Hints:
*  We will adopt Google Chrome version 51 as the standard testbed for this class, if you use a different browser in testing, please note that in info_sheet.txt.
*  While you are not going to be heavily graded on the style and design of your web site, it should be presented in a clear and readable manner.

##Grading Rubric:
*  User management (account creation/login/logout) works as specified: 10%
*  List of available chat rooms shown:  10%
*  Leaving a chat room works as specified:  10%
*  Posting new messages to a chat room works as specified:  15%
*  Polled updates performed as specified: 15%
*  AJAJ working as specified:  20%
*  SQLAlchemy data model quality:  10%
*  Clear and readable presentation:  5%
*  Submission/info sheet:  5%
