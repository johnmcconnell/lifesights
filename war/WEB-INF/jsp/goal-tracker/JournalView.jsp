<div class="container">
	<div class="row">
		<div class="col-md-3">
			<div class="page-header">
				<h3>
					Journal
					<button type="button" class="btn btn-default btn-xs"
						{{action 'addnew' }}>&#43;</button>
				</h3>
			</div>
			<table class="table">
				<tr>
					<th>title</th>
					<th>update on</th>
				</tr>
				{{#each}}
				<tr>

					<td>{{#link-to 'journal-entry' this}}{{title}}{{/link-to}} by {{author}} </td>

					<td>{{pretty-date updatedOn}}</td>
				</tr>
				{{/each}}

			</table>
		</div>
		{{outlet}}
	</div>
</div>
