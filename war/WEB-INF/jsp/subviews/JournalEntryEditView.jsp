<p>Title:{{input type="text" value=title}}</p>
<p>Author:{{input type="text" value=author}}</p>
<table><tr>{{#each tags}}<td> {{input type="text" value=this}} </td>{{/each}}</tr></table>
<button {{action 'addTag'}}>tag &#43;</button>
<p>Published:{{input type="checkbox" name="published" checked=published}}</p>
<p>{{textarea value=something}}</p>
