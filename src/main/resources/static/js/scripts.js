/**
 * change 
 */

$(document).ready(function(){
	$(".cartItemQty").on('change', function(){
		var id = this.id;
		console.log("test");//to test if this js function is called
		$('#update-item-'+id).css('display','inline-block');
	});
});