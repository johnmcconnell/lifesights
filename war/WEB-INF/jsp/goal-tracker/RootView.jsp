
<nav class="navbar navbar-default" role="navigation">
	<div class="navbar-header">
		<a class="navbar-brand" href="#">Goal Tracker</a>
	</div>
	<div class="collapse navbar-collapse">

		<ul class="nav navbar-nav">
			<li class="active"><a href="#/goals">Goals</a></li>
			<li><a href="#/progress">Progress</a></li>

		</ul>
		<form class="navbar-form navbar-left" role="search">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Search">
			</div>
			<button type="submit" class="btn btn-default">submit</button>
		</form>
		<ul class="nav navbar-nav navbar-right">
			<li><a href="#/about">About</a></li>
			<li><a href="#">sign in</a></li>
			<li><a href="#">sign up</a></li>
		</ul>
	</div>
</nav>
{{outlet}}

