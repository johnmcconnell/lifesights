<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="mission" required="true"
	type="com.mcconnell.modernwar.model.Mission"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Modal -->
<div class="modal fade" id="missionModal" tabindex="-1" role="dialog"
	aria-labelledby="missionModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="missionModalLabel">Enter a New Mission</h4>
			</div>
			<div class="modal-body">
				<form id="newMissionForm" role="form" method="post"
					accept-charset="utf-8">
					<div class="form-group">
						<label for="missionName">Mission Name</label> <input type="text"
							class="form-control" name="name" placeholder="mission name" />
					</div>
					<div class="form-group">
						<label class="pull-left" for="size">Total Levels</label>
						<div class="col-lg-2">
							<input type="text" class="form-control" name="totalLevels"
								placeholder="0" />
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Save</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->