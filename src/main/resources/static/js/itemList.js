/**
 * 
 */

$(function() {

	$("#parentCategory").on('change',function() {
		$('[id^="childParentCategoryId"]').remove();
		$('[id^="grandParentCategoryId"]').remove();
		if($(this).val() != '- parentCategory -'){
		var parent = $(this).val();
		$.ajax({
			url:'http://localhost:8080/itemList/findByChildCategoryName',
			type:'POST',
			data:{
				parent:parent
			},
		dataType:"json",
		})
		.done(function(data){
			var listNumber = 0;
			data.forEach(function(element,index,category){
				var childParentCategoryId = 'childParentCategoryId' + data[listNumber].id;
				var childParentCategoryName = data[listNumber].name;
				var childParentId = data[listNumber].id;
				$(function(){ 
					$('#childParentCategory').append("<option id='"+ childParentCategoryId +"' label='"+childParentCategoryName+"' value='"+childParentId +"'>"+childParentCategoryName+"</option>");
				})
				listNumber++;
			})
		})
		}
	});
	
	$("#childParentCategory").on('change',function(){
		$('[id^="grandParentCategoryId"]').remove();
		var parent = $(this).val();
		var childCategoryName = $('#childParentCategory option:selected').text();
		if(childCategoryName != '- childCategory -'){
			$.ajax({
				url:'http://localhost:8080/itemList/findByGrandCategoryName',
				type:'POST',
				data:{
					parent:parent,
					childCategoryName:childCategoryName
				},
				dataType:"json",
			})
			.done(function(data){
				var listNumber = 0;
				data.forEach(function(element,index,category){
					var grandParentCategoryId = 'grandParentCategoryId' + data[listNumber].id;
					var grandParentCategoryName = data[listNumber].name;
					var grandParentId = data[listNumber].id;
					$(function(){
						$('#grandParentCategory').append("<option id='"+ grandParentCategoryId + "' label='" + grandParentCategoryName+"' value='"+grandParentId +"'>'"+grandParentCategoryName+"'</option>")
					})
					listNumber++;
				})
			})
		}
	})
	
	
	$.ajax({
		url:'http://localhost:8080/itemList/findBySession',
	})
	.done(function(data){
		console.log(data)
		
		
	})
	
	
});