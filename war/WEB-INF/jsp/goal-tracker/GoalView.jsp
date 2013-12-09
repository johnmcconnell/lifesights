{{#if isEditing}}
	{{partial 'goal/edit'}}
	<button {{action 'cancel'}}>Cancel</button>
	<button {{action 'save'}}>Save</button>
{{else}}
	<button {{action 'startEdit'}}>Edit</button>
{{/if}}

<h1>{{name}}</h1>
<small class="muted">updated {{pretty-date updatedOn}}</small>
<p>{{description}}</p>