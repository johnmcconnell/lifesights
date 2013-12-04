/**
 * 	var input = form.find('input[name="index"]')
 *	var val = input.val()  * 1;
 *	input.val((val - 1).toString());
 */

function submitLevel() {
	var form = $('#newLevelForm');	
	form.submit();
}

window.onload=function() {
	$('.modal-footer > .btn-primary').click(submitLevel);
}