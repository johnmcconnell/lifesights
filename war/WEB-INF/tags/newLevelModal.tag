<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="mission" required="true"
	type="com.mcconnell.modernwar.model.Mission"%>
<!-- Modal -->
<div class="modal fade" id="newLevelModal" tabindex="-1" role="dialog"
	aria-labelledby="missionModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="missionModalLabel">New Level for
					${fn:escapeXml(mission.name)}</h4>
			</div>
			<div class="modal-body">
				<form id="newLevelForm" role="form" method="post"
					accept-charset="utf-8">
					<div class="form-group">
						<label for="missionName">Level Name</label> <input type="text"
							class="form-control" name="name" placeholder="level name" />
					</div>
					<div class="form-group">
						<label class="pull-left" for="size">Level Number</label>
						<div class="col-lg-2">
							<input type="text" class="form-control" name="index"
								placeholder="1" />
						</div>
						<label class="pull-left" for="size">Total Number of
							Completions</label>
						<div class="col-lg-2">
							<input type="text" class="form-control" name="totalRuns"
								placeholder="1" />
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