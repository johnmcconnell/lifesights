<div class="col-md-4">
{{#if isEditing}}
	{{partial 'journal-entry/edit'}}
	<button {{action 'delete'}}>Delete</button>
	<button {{action 'cancel'}}>Cancel</button>
	<button {{action 'save'}}>Save</button>
{{else}}
	<button {{action 'edit'}}>Edit</button>
{{/if}}
</div>

<div class="col-md-4">
<h2>{{title}}</h2>
<h4>By:{{author}}</h4>
<small class="muted">updated {{pretty-date updatedOn}}</small>
<table><tr>{{#each tags}}<td> {{.}} </td> {{/each}} </tr></table>
<div>{{format-markdown something}}</div>
</div>