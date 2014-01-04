/*
 * Initialize App in Ember.js
 * Author: John McConnell
 * http://eleviary.appspot.com
 */
BlogApp = Ember.Application.create();
BlogApp.Router.map(function() {
	this.resource('entries', function() {
		this.resource('entry', {
			path : ':id'
		});
	});
	this.resource('about');
});

BlogApp.EntriesRoute = Ember.Route.extend({
	setupController : function(controller, entries) {
		controller.set('model', entries);
	},
	model : function() {
		return $.getJSON('goaltracker/journal/api/all').then(function(data) {
			var published = data.filter(function(entry) {
				return entry.published == true;
			});
			return published;
		});
	}
});

BlogApp.EntriesController = Ember.ArrayController.extend({});

BlogApp.EntryRoute = Ember.Route.extend({
	setupController : function(controller, entry) {
		controller.set('model', entry);
		// this.controllerFor('entries').set('model',$.getJSON('goaltracker/journal/api/all'));
	},
	model : function(params) {
		return $.getJSON('goaltracker/journal/api/' + params.id);
	}
});

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