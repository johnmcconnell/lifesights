Okay if you are new to this repo, I will try to break it down quickly.

There are a useful set of http api for adding, updating, and eventually deleting objects in a database.

This library was designed for using ajax and json to send database objects to the client. 

A quick example:

The controller:
look at com.mcconnell.goaltracker.controller.JournalController.java

The model:
look at com.mcconnell.goaltracker.model.Journal.java

Tell spring mvc to host the controller
edit the mvc-dispatcher-servlet.xml example:
> <!-- Tell Spring to host controller -->
> <bean class="com.mcconnell.webapp.goaltracker.controllers.JournalController" />

With these changes:
there will be api to http handlers to get, add, update the objects.
You can see some of the api used in
war/js/goal-tracker-app.js
