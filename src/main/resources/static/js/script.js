
function paymentStart() {
	var path = window.location
	let amount = $("#payment_field").val();
	console.log(amount);
	if (amount == "" || amount == null || amount <= 0) {
		/*alert("No item in the cart...");*/
		swal({
			title: "Cart Empty",
			text: "Please buy someThing...",
			icon: "warning",
			buttons: true,
			dangerMode: true,
		})
			
		return;
	}

console.log("Post to be called")
	$.ajax({
		type: 'POST',
		url: "/createOrder",
		data: JSON.stringify({ amount: amount, info: 'order_request' }),
		contentType: "application/json",
		dataType: 'json',
		success: function(response) {
			console.log(response)
			if (response.status == "created") {
				let options = {
					key: 'rzp_test_xD1ZcdIlypoUZd',
					amount: response.amount,
					currency: 'INR',
					name: 'Medical Payment',
					description: 'Donation',
					order_id: response.id,
					handler: function(response) {
						console.log(response.razorpay_payment_id)
						console.log(response.razorpay_order_id)
						console.log(response.razorpay_signature)
						console.log("payment successfull")
						// alert("congrates !! Payment successfull")

						updatePaymentOnServer(response.razorpay_payment_id, response.razorpay_order_id, "paid")

						// swal("good jon", "congrates !! payment success", "success");
					},
					prefill: {
						name: "Ashish kumar",
						contact: "7607814860",
						email: "ashishrajput142@gmail.com"
					},
					notes: {
						address: "Checkout you med..."
					}
				};
				let rzp = new Razorpay(options);
				rzp.on('payment.failed', function(response) {
					console.log(response.error.code);
					console.log(response.error.description);
					console.log(response.error.source);
					console.log(response.error.step);
					console.log(response.error.reason);
					console.log(response.error.metadata.order_id);
					console.log(response.error.metadata.payment_id);
					alert("Oops payment failed...")
				});

				rzp.open();
			}
		},
		error: function(error) {
			console.log(error)
			alert("something went wrong")
		}
	})
};

function updatePaymentOnServer(payment_id, order_id, status) {
	$.ajax({
		// url: '/user/updateOrder',
		type: 'POST',
		url: "/medical/updateOrder",
		//  data:{amount:amount},
		data: JSON.stringify({
			payment_id: payment_id,
			order_id: order_id,
			status: status
		}),
		contentType: 'application/json',
		dataType: 'json',
		success: function(response) {
			swal("good job", "congrates !! payment success,", "success");
			window.location = '/user/userOrders'
		},

		error: function(error) {
			swal("Paid", "congrates !! payment success,", "success");
			// swal("Failed", "congrates !! payment success, might be Stuck some where", "success");
			setInterval(() => {
				swal("Hold Tight You are Landing to the Orders!!!");
			}, 3000)
			setInterval(() => {
				window.location = '/user/userOrders'
			}, 6000)
		}
	});

};


/*window.onload = () => {
	var payBtn = document.getElementById("payment_field");
	var total = payBtn.val();
	console.log(total);
	if (total <= 0) {
		document.getElementById("pay_btn").disabled = true
	}
};*/
function checkAmount() {
	var cartCheckoutBtn = document.getElementById("cartCheckout");
	var cartCheckoutAmount = document.getElementById("cartCheckoutAmount").value;
	console.log(total);
	if (cartCheckoutAmount <= 0) {
		cartCheckoutBtn.disabled = true
	}
};
