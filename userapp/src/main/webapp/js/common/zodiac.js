var Zodiac = {
	names : [ "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪" ],

	getBallsByName : function(name) {
		var date = new Date(), year = date.getFullYear(), currentIndex = (year - 2008) % 12, index = -1, balls = [];
		for (var i = 0; i < this.names.length; i++) {
			if (this.names[i] == name) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			var start = 0;
			if (currentIndex >= index) {
				start = 1 + currentIndex - index;
			} else {
				start = 12 - (index - currentIndex - 1);
			}
			for (;;) {
				balls.push(start);
				start += 12;
				if (start > 49) {
					break;
				}
			}
		}
		return balls;
	},

	getNameByBall : function(ball) {
		var ballNum = parseInt(ball), date = new Date(), year = date.getFullYear(), currentIndex = (year - 2008) % 12;
		for (var start = 1, i = currentIndex; i >= 0; i--, start++) {
			var value = start, name = this.names[i];
			for (;;) {
				if(value==ballNum){
					return name;
				}
				value += 12;
				if (value > 49) {
					break;
				}
			}
		}
		if (currentIndex < 11) {
			for (var start = 12, i = currentIndex + 1; i < 11; i++, start--) {
				var value = start, name = this.names[i];
				for (;;) {
					if(value==ballNum){
						return name;
					}
					value += 12;
					if (value > 49) {
						break;
					}
				}
			}
		}
		return null;
	},

	getAnimalTypeByBall : function(ball) {
		if (parseInt(ball) == 49) {
			return null;
		}
		var name = this.getNameByBall(), beasts=["鼠","虎","龙","蛇","兔","猴"];
		if(name){
			for(var i=0;i<beasts.length;i++){
				if(beasts[i]==name){
					return "beast";
				}
			}
		}
		return "poultry";
	},

	getNameToBalls : function() {
		var date = new Date(), year = date.getFullYear(), currentIndex = (year - 2008) % 12, balls = {};
		for (var start = 1, i = currentIndex; i >= 0; i--, start++) {
			var value = start, name = this.names[i];
			for (;;) {
				if (typeof balls[name] === "undefined") {
					balls[name] = [];
				}
				balls[name].push(value);
				value += 12;
				if (value > 49) {
					break;
				}
			}
		}
		if (currentIndex < 11) {
			for (var start = 12, i = currentIndex + 1; i < 11; i++, start--) {
				var value = start, name = this.names[i];
				for (;;) {
					if (typeof balls[name] === "undefined") {
						balls[name] = [];
					}
					balls[name].push(value);
					value += 12;
					if (value > 49) {
						break;
					}
				}
			}
		}
		return balls;
	},

	getBallToNames : function() {
		var date = new Date(), year = date.getFullYear(), currentIndex = (year - 2008) % 12, balls = {};
		for (var start = 1, i = currentIndex; i >= 0; i--, start++) {
			var value = start, name = this.names[i];
			for (;;) {
				balls[value] = name;
				value += 12;
				if (value > 49) {
					break;
				}
			}
		}
		if (currentIndex < 11) {
			for (var start = 12, i = currentIndex + 1; i < 11; i++, start--) {
				var value = start, name = this.names[i];
				for (;;) {
					balls[value] = name;
					value += 12;
					if (value > 49) {
						break;
					}
				}
			}
		}
		return balls;
	}
}