/**
 * 
 */
function submitMission() {
	$('#newMissionForm').submit();
}

window.onload=function() {
	$('.modal-footer > .btn-primary').click(submitMission);
}
