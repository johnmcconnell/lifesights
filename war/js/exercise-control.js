/*! Author: John McConnell */

function exercise_step() {
	if (typeof exercise_step.latest_index == 'undefined') {
		/*
		 * add first exercise step dom after the name input on the form all
		 * others will be added after the latest step
		 */
		exercise_step.latest_index = 1; // add 1 because we just created a node
		var es_dom = exercise_step_dom(0);
		$('#exercise-name').after(es_dom);
		
	}
}

function exercise_step_dom(index) {
	var dom_span = $('<span>', {
		class : 'input-group-addon',
		text : 'step ' + (index + 1)
	});
	var dom_input = $('<input>', {
		type : 'text',
		class : 'form-control',
		name : 's' + index,
		placeholder : 'enter your description here'
	});
	var dom_div = $('<div>', {
		class : 'input-group margin10',
		id : 's' + index
	}).append(dom_span).append(dom_input);

	add_step_handler(dom_div);

	return dom_div;
}

function new_step_handler(dom) {
	var handler = function() {
		if (dom.children('input').val() != "") {
			add_exercise_step();
		} else {
			remove_exercise_step();
		}
	}
	return handler;
}

function add_step_handler(dom) {
	dom.on('focusout', new_step_handler(dom));
}

function remove_exercise_step() {
	var index = exercise_step.latest_index - 1;
	console.log(index);
	if (index > 0) {
		$("#s" + index).remove();
		var prev_dom = $("#s" + (index - 1));
		add_step_handler(prev_dom);
		exercise_step.latest_index--;
	}
}

function add_exercise_step() {
	var index = exercise_step.latest_index++;
	console.log(index);
	var next_es_dom = exercise_step_dom(index);
	var prev_es_dom = $("#s" + (index - 1));
	prev_es_dom.off('focusout');
	prev_es_dom.after(next_es_dom);
}

$(document).ready(init);

function init() {
	exercise_step();
	$('#newExerciseSubmit').click(function() {
		exercise_json = '{"name":"test1","steps":["step1","step2"],"updatedOn":1386371932948}';
		DATA_HANDLER.put['exercise']();
	});
}
