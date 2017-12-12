/**
 * change 
 */

function checkBillingAddress(){
	if($("#theSameAsShippingAddress").is(":checked")){
		$(".billingAddress").prop("disabled", true);
	}else{
		$(".billingAddress").prop("disabled", false);
	}
}

$(document).ready(function(){
	$(".cartItemQty").on('change', function(){
		var id = this.id;
		console.log("test");//to test if this js function is called
		$('#update-item-'+id).css('display','inline-block');
	});
	$("#theSameAsShippingAddress").on('click', checkBillingAddress);
});