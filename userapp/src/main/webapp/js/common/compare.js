var Compare = {
	getSingleOrDouble : function(ball) {
		var ballNum = parseInt(ball);
		if (ballNum % 2 == 0) {
			return "double";
		} else if (ballNum == 49) {
			return null;
		} else {
			return "single";
		}
	},

	getBigOrSmall : function(ball) {
		var ballNum = parseInt(ball);
		if (ballNum <= 24) {
			return "small";
		} else if (ballNum == 49) {
			return null;
		} else {
			return "big";
		}
	},

	getTailBigOrSmall : function(ball) {
		var ballNum = parseInt(ball);
		if (ballNum == 49) {
			return null;
		}
		var tail = ballNum % 10;
		if (tail >= 5) {
			return "big";
		} else {
			return "small";
		}
	},

	getSumSingleOrDouble : function(ball) {
		var ballNum = parseInt(ball);
		if (ballNum == 49) {
			return null;
		}
		var head = Math.floor(ballNum / 10), tail = ballNum % 10, sum = head
				+ tail;
		if (sum % 2 == 0) {
			return "double";
		} else {
			return "single";
		}
	},

	getSumBigOrSmall : function(ball) {
		var ballNum = parseInt(ball);
		if (ballNum == 49) {
			return null;
		}
		var head = Math.floor(ballNum / 10), tail = ballNum % 10, sum = head
				+ tail;
		if (sum <= 6) {
			return "small";
		} else {
			return "big";
		}
	},

	getTailSumBigOrSmall : function(ball) {
		var ballNum = parseInt(ball);
		if (ballNum == 25) {
			return null;
		}
		var head = Math.floor(ballNum / 10), tail = ballNum % 10, sum = (head + tail) % 10;
		if (sum <= 4) {
			return "small";
		} else {
			return "big";
		}
	}
}