GoalApp = Ember.Application.create();

GoalApp.Router.map(function() {
	this.resource('goals', function() {
		this.resource('goal', {
			path : ':id'
		});
	});
	this.resource('progress');
	this.resource('about');
});

GoalApp.GoalsRoute = Ember.Route.extend({
	setupController : function(controller, goals) {
		controller.set('model', goals);
	},
	model : function() {
		return $.getJSON('goaltracker/api/all')
	}
});

GoalApp.GoalsController = Ember.ArrayController.extend({
	needs : [ "goal" ]
});

GoalApp.GoalRoute = Ember.Route.extend({
	setupController : function(controller, goal) {
		controller.set('model', goal);
	},
	model : function(param) {
		if (param.id === undefined) {
			return new Goal();
		}
		return $.getJSON('goaltracker/api/' + param.id);
	}
});

GoalApp.GoalController = Ember.ObjectController.extend({
	isEditing : false,
	isNew : false,
	actions : {
		startEdit : function() {
			this.set('isEditing', true);
		},
		startNew : function() {
			this.set('isEditing', true);
			this.set('isNew', true);
			this.set('model', new Goal());
			this.transitionToRoute('goal');
		},
		save : function() {
			if (this.get('isNew')) {
				addGoal(this.get('model'));
			} else {
				updateGoal(this.get('model'));
			}
			this.set('isEditing', false);
			this.set('isNew', false)
		},
		cancel : function() {
			this.transitionToRoute('goals');
			this.set('isEditing', false);
			this.set('isNew', false);
		}
	}
});

function Goal() {
	this.id = "";
	this.name = "";
	this.description = "";
	var now = new Date();
	this.updatedOn = now.getTime();
}

function addGoal(goal) {
	json = JSON.stringify(goal);
	$.ajax({
		type : 'POST',
		url : '/goaltracker/api',
		dataType : 'json',
		data : json,
		contentType : 'application/json',
		mimeType : 'application/json'
	}).done(function(data) {
		console.log(data);
	});
}

function updateGoal(goal) {
	json = JSON.stringify(goal);
	$.ajax({
		type : 'PUT',
		url : '/goaltracker/api/' + goal.id,
		dataType : 'json',
		data : json,
		contentType : 'application/json',
		mimeType : 'application/json'
	}).done(function(data) {
		console.log(data);
	});
}
Ember.Handlebars.helper('pretty-date', function(timestamp) {
	var x = new Date(timestamp);
    return x.toDateString();
});