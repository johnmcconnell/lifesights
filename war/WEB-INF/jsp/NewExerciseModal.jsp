<!-- Button trigger modal -->
<button class="btn btn-primary btn-lg" data-toggle="modal"
	data-target="#newExerciseModal">Add a new exercise &#43;</button>

<!-- Modal -->
<div class="modal fade" id="newExerciseModal" tabindex="-1"
	role="dialog" aria-labelledby="newExerciseModalLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="newExerciseModalLabel">Add a new
					exercise</h4>
			</div>
			<div class="modal-body">
				<form role="form" accept-charset="UTF-8" data-remote="true" method="post">
					<div id="exercise-name" class="input-group margin10">
						<span class="input-group-addon">name</span> <input
							class="form-control" name="name" type="text"
							placeholder="military press" />
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button id="newExerciseSubmit" type="button" class="btn btn-primary">Submit</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->