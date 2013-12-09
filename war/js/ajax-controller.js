if (typeof DATA_HANDLER !== 'object') {
	DATA_HANDLER = new Object();
	DATA_HANDLER.add = {
		exercise : submit_exercise
	};
	DATA_HANDLER.addAll = {};
	DATA_HANDLER.get = {}
	DATA_HANDLER.getALL = {
		daily_goal : get_goals
	}
}

function get_goals() {
	console.log("start");
	var json = 3;
	var jqxhr = $.ajax({
		type : 'GET',
		dataType : "json",
		url : '/goaltracker/goals/data/all',
	}).done(function() {
		json = jqxhr.responseJSON;
	}).fail(function() {
		console.log(null);
	});
	return json;
}

function submit_exercise(exercise_json) {
	$.ajax({
		type : 'POST',
		url : '/weights/exercises',
		dataType : 'json',
		data : exercise_json,
		contentType : 'application/json',
		mimeType : 'application/json'
	}).done(function(data) {
		console.log(data);
	});
}
