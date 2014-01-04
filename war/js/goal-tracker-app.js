/*
 * Initialize App in Ember.js
 * Author: John McConnell
 * http://eleviary.appspot.com
 */
GoalApp = Ember.Application.create();

/*
 * Create the router mapping Goals : #/goals/goal/:id Journals :
 * #/journal-entries/journal-entry/:id Progress : #/progress About : #/about
 */
GoalApp.Router.map(function() {
	this.resource('goals', function() {
		this.resource('goal', {
			path : ':id'
		});
	});
	this.resource('journal-entries', function() {
		this.resource('journal-entry', {
			path : ':id'
		});
	});
	this.resource('progress');
	this.resource('about');
	this.resource('sign-in');
	this.resource('sign-up');
});

/*
 * ======================= Journal Route and Controller =================================
 */
GoalApp.JournalEntriesRoute = Ember.Route.extend({
	setupController : function(controller, entries) {
		controller.set('model', entries);
	},
	model : function() {
		return $.getJSON('goaltracker/journal/api/all');
	}
});

GoalApp.JournalEntriesController = Ember.ArrayController.extend({
	needs : [ 'journal-entry' ],
	actions : {
		addnew : function() {
			entry = new JournalEntry();
			this.pushObject(entry);
			this.transitionToRoute('journal-entry', entry);
		}
	}
});

GoalApp.JournalEntryRoute = Ember.Route.extend({
	setupController : function(controller, entry) {
		controller.set('model', entry);
		//this.controllerFor('journal-entries');
	},
	model : function(params) {
		return GoalApp.JournalEntriesController.findProperty('id', params.id);
	}
});

GoalApp.JournalEntryController = Ember.ObjectController.extend({
	isEditing : false,
	actions : {
		edit : function() {
			this.set('isEditing', true);
		},
		save : function() {
			if (this.get('model').id == 'new') {
				addJournalEntry(this.get('model'));
			} else {
				updateJournalEntry(this.get('model'));
			}
			this.set('isEditing', false);
		},
		cancel : function() {
			this.set('isEditing', false);
		}
	}
});
/*
 * ======================= Finish Journal Route and Controller ==========================
 */
/*
 *  ======================= Goal Route and Controller ====================================
 */
GoalApp.GoalsRoute = Ember.Route.extend({
	setupController : function(controller, goals) {
		controller.set('model', goals);
	},
	model : function() {
		return $.getJSON('goaltracker/goals/api/all');
	}
});

GoalApp.GoalsController = Ember.ArrayController.extend({
	needs : [ "goal" ],
	actions : {
		addNew : function() {
			goal = new Goal();
			this.pushObject(goal);
			this.transitionToRoute('goal', goal);
		}
	}
});

GoalApp.GoalRoute = Ember.Route.extend({
	setupController : function(controller, goal) {
		controller.set('model', goal);
	},
	model : function(params) {
		return GoalApp.GoalsController.findProperty('id', params.id);
	}
});

GoalApp.GoalController = Ember.ObjectController.extend({
	isEditing : false,
	actions : {
		startEdit : function() {
			this.set('isEditing', true);
		},
		save : function() {
			if (this.get('model').id == 'new') {
				addGoal(this.get('model'));
			} else {
				updateGoal(this.get('model'));
			}
			this.set('isEditing', false);
		},
		cancel : function() {
			this.transitionToRoute('goals');
			this.set('isEditing', false);
		}
	}
});
/*
 * ======================= Finish Goal Route and Controller ==========================
 */

/*
 * Create a new Goal that can be added to the database at a later time
 */
function Goal() {
	this.id = "new";
	this.name = "title";
	this.description = "description";
	var now = new Date();
	this.updatedOn = now.getTime();
}
/*
 * Add a goal to the database
 */
function addGoal(goal) {
	json = JSON.stringify(goal);
	$.ajax({
		type : 'POST',
		url : '/goaltracker/goals/api',
		dataType : 'json',
		data : json,
		contentType : 'application/json',
		mimeType : 'application/json'
	}).done(function(data) {
		console.log(data);
	});
}
/*
 * Update a goal in the database
 */
function updateGoal(goal) {
	json = JSON.stringify(goal);
	$.ajax({
		type : 'PUT',
		url : '/goaltracker/goals/api/' + goal.id,
		dataType : 'json',
		data : json,
		contentType : 'application/json',
		mimeType : 'application/json'
	}).done(function(data) {
		console.log(data);
	});
}
/*
 * Add USer
 */
function User() {
	this.id = "new";
	this.username = "username";
	this.password = "password";
	var now = new Date();
	this.updatedOn = now.getTime();
}

function addUser(user) {
	json = JSON.stringify(user);
	$.ajax({
		type : 'POST',
		url : '/goaltracker/users/api',
		dataType : 'json',
		data : json,
		contentType : 'application/json',
		mimeType : 'application/json'
	}).done(function(data) {
		console.log(data);
	});
}

/*
 * Journal Entries
 */
function JournalEntry() {
	this.id = "new";
	this.author = "Author";
	this.title = "Title";
	this.markdown = "markdown";
	this.published = false;
	var now = new Date();
	this.updatedOn = now.getTime();
}

function addJournalEntry(entry) {
	json = JSON.stringify(entry);
	$.ajax({
		type : 'POST',
		url : '/goaltracker/journal/api',
		dataType : 'json',
		data : json,
		contentType : 'application/json',
		mimeType : 'application/json'
	}).done(function(data) {
		console.log(data);
	});
}

function updateJournalEntry(entry) {
	json = JSON.stringify(entry);
	$.ajax({
		type : 'PUT',
		url : '/goaltracker/journal/api/' + entry.id,
		dataType : 'json',
		data : json,
		contentType : 'application/json',
		mimeType : 'application/json'
	}).done(function(data) {
		console.log(data);
	});
}

/*
 * Helper to print a timestamp as a nice date
 */
Ember.Handlebars.helper('pretty-date', function(timestamp) {
	var x = new Date(timestamp);
	return x.toDateString();
});
Ember.Handlebars.helper('format-markdown', function(data) {
	var converter = new Showdown.converter();
	return new Handlebars.SafeString(converter.makeHtml(data));
});