<div class="container">
	<div class="row">
		<div class="col-md-3">
			<div class="page-header">
				<h3>
					Journal
				</h3>
			</div>
			<table class="table">
				<tr>
					<th>title</th>
					<th>update on</th>
				</tr>
				{{#each}}
				<tr>

					<td>{{#link-to 'entry' this}}{{title}}{{/link-to}} by {{author}} </td>

					<td>{{pretty-date updatedOn}}</td>
				</tr>
				{{/each}}

			</table>
		</div>
		{{outlet}}
	</div>
</div>
