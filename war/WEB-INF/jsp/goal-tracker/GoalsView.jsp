<div class="container">
	<div class="row">
		<div class="col-md-4">
			<div class="page-header">
				<h3>
					Goals
					<button type="button" class="btn btn-default btn-xs"
						{{action 'addNew'}}>&#43;</button>
				</h3>
			</div>
			<table class="table">
				<tr>
					<th>goal</th>
					<th>update on</th>
				</tr>
				{{#each}}
				<tr>

					<td>{{#link-to 'goal' this}}{{name}}{{/link-to}}</td>

					<td>{{pretty-date updatedOn}}</td>
				</tr>
				{{/each}}

			</table>
		</div>
		{{outlet}}
	</div>
</div>
