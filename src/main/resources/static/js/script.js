
function paymentStart() {
	let amount = $("#payment_field").val();
	console.log(amount);
	if (amount == "" || amount == null) {
		alert("amount is required !!");
		return;
	}


	$.ajax({
		url: '/createOrder',
		// url: '/user/createOrder',
		//  data:{amount:amount},
		data: JSON.stringify({ amount: amount, info: 'order_request' }),
		contentType: 'application/json',
		type: 'POST',
		dataType: 'json',
		success: function (response) {
			console.log(response)
			if (response.status == "created") {
				let options = {
					key: 'rzp_test_xD1ZcdIlypoUZd',
					amount: response.amount,
					currency: 'INR',
					name: 'Medical Payment',
					description: 'Donation',
					order_id: response.id,
					handler: function (response) {
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
				rzp.on('payment.failed', function (response) {
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
		error: function (error) {
			console.log(error)
			alert("something went wrong")
		}
	})
};

function updatePaymentOnServer(payment_id, order_id, status) {
	$.ajax({
		// url: '/user/updateOrder',
		url: '/updateOrder',
		//  data:{amount:amount},
		data: JSON.stringify({
			payment_id: payment_id,
			order_id: order_id,
			status: status
		}),
		contentType: 'application/json',
		type: 'POST',
		dataType: 'json',
		success: function (response) {
			swal("good job", "congrates !! payment success,", "success");
		},

		error: function (error) {
			swal("Paid", "congrates !! payment success,", "success");
			// swal("Failed", "congrates !! payment success, might be Stuck some where", "success");

		}
	});
};