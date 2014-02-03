if (LifeSights !== undefined) {
	throw new Error('LifeSights object is already defined');
} else {
	var LifeSights = new Object();
}

LifeSights.Journal = new Object();

LifeSights.Journal.create = function (data,user) {
	json = JSON.stringify({data:data,user:user});
	$.ajax({
		type : 'POST',
		url : 'api/model/journal/create',
		dataType : 'json',
		data : json,
		contentType : 'application/json',
		mimeType : 'application/json'
	}).done(function(data) {
		console.log(data);
	});
}

LifeSights.Journal.createAll = function (models) {
	json = JSON.stringify(models);
	$.ajax({
		type : 'POST',
		url : 'api/model/journal/createAll',
		dataType : 'json',
		data : json,
		contentType : 'application/json',
		mimeType : 'application/json'
	}).done(function(data) {
		console.log(data);
	});
}

LifeSights.Journal.update = function (model) {
	json = JSON.stringify(model);
	$.ajax({
		type : 'POST',
		url : 'api/model/journal/update',
		dataType : 'json',
		data : json,
		contentType : 'application/json',
		mimeType : 'application/json'
	}).done(function(data) {
		console.log(data);
	});
}

LifeSights.Journal.read = function (id) {
	return $.getJSON('api/model/journal/read/' + id);
};

LifeSights.Journal.readAll = function() {
	return $.getJSON('api/model/journal/readAll');
};

LifeSights.Journal.new = function(){
	return new Object({id:"new", updatedOn:Date(), author:"author",title:"title",something:"type here",published:false,tags:[]});
};

LifeSights.User = new Object();

LifeSights.User.create = function (data,user) {
	json = JSON.stringify({data:data,user:user});
	$.ajax({
		type : 'POST',
		url : 'api/model/user/create',
		dataType : 'json',
		data : json,
		contentType : 'application/json',
		mimeType : 'application/json'
	}).done(function(data) {
		console.log(data);
	});
}

LifeSights.User.createAll = function (data,user) {
	json = JSON.stringify({data:data,user:user});
	$.ajax({
		type : 'POST',
		url : 'api/model/user/createAll',
		dataType : 'json',
		data : json,
		contentType : 'application/json',
		mimeType : 'application/json'
	}).done(function(data) {
		console.log(data);
	});
}

LifeSights.User.update = function (data,user) {
	json = JSON.stringify({data:data,user:user});
	$.ajax({
		type : 'POST',
		url : 'api/model/user/update',
		dataType : 'json',
		data : json,
		contentType : 'application/json',
		mimeType : 'application/json'
	}).done(function(data) {
		console.log(data);
	});
}

LifeSights.User.login = function (user) {
	json = JSON.stringify(user);
	$.ajax({
		type : 'POST',
		url : 'api/model/user/login',
		dataType : 'json',
		data : json,
		contentType : 'application/json',
		mimeType : 'application/json'
	}).done(function(data) {
		console.log(data);
	});
}

LifeSights.User.read = function(user){
	return $.getJSON('api/model/user/read');
}

LifeSights.User.readAll = function(user){
	return $.getJSON('api/model/user/readAll');
}

LifeSights.User.model = function(){
	return new Object({id:'new', updatedOn:Date(), username:"",password:"",nonce:0,roles:["User"]});
}