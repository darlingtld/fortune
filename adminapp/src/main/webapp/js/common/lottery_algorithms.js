var colorMap = {
    "1": "red",
    "2": "red",
    "3": "blue",
    "4": "blue",
    "5": "green",
    "6": "green",
    "7": "red",
    "8": "red",
    "9": "blue",
    "10": "blue",
    "11": "green",
    "12": "red",
    "13": "red",
    "14": "blue",
    "15": "blue",
    "16": "green",
    "17": "green",
    "18": "red",
    "19": "red",
    "20": "blue",
    "21": "green",
    "22": "green",
    "23": "red",
    "24": "red",
    "25": "blue",
    "26": "blue",
    "27": "green",
    "28": "green",
    "29": "red",
    "30": "red",
    "31": "blue",
    "32": "green",
    "33": "green",
    "34": "red",
    "35": "red",
    "36": "blue",
    "37": "blue",
    "38": "green",
    "39": "green",
    "40": "red",
    "41": "blue",
    "42": "blue",
    "43": "green",
    "44": "green",
    "45": "red",
    "46": "red",
    "47": "blue",
    "48": "blue",
    "49": "blue"
};

// 连码类型表
var jointTypeMap={
	"JOINT_3_ALL": "三全中",
	"JOINT_3_2": "三中二",
	"JOINT_2_ALL": "二全中",
	"JOINT_2_SPECIAL": "二中特",
	"JOINT_SPECIAL": "特串"	
};

// 过关类型表
var passTypeMap={
	"PASS_DAN": "过关单", 
    "PASS_SHUANG": "过关双",
    "PASS_DA": "过关大",
    "PASS_XIAO": "过关小",
    "PASS_RED": "过关红",
    "PASS_GREEN": "过关绿",
    "PASS_BLUE": "过关蓝"
};

// 正码类型表
var zhengTypeMap={
	"DAN": "单",
	"SHUANG": "双",
	"DA": "大",
	"XIAO": "小",
	"HEDAN": "合单",
	"HESHUANG": "合双",
	"RED": "红波",
	"GREEN": "绿波",
	"BLUE": "蓝波"
};

// 生肖类型表
var zodiacTypeMap={
	"ZODIAC_SHU": "鼠",
	"ZODIAC_NIU": "牛",
	"ZODIAC_HU": "虎",
	"ZODIAC_TU": "兔",
	"ZODIAC_LONG": "龙",
	"ZODIAC_SHE": "蛇",
	"ZODIAC_MA": "马",
	"ZODIAC_YANG": "羊",
	"ZODIAC_HOU": "猴",
	"ZODIAC_JI": "鸡",
	"ZODIAC_GOU": "狗",
	"ZODIAC_ZHU": "猪"
};

//两面类型表
var twoFaceTypeMap={
	DAN: "特单",
    SHUANG: "特双",
    DA: "特大",
    XIAO: "特小",
    HEDAN: "合单",
    HESHUANG: "合双",
    HEDA: "合大",
    HEXIAO: "合小",
    WEIDA: "尾大",
    WEIXIAO: "尾小",
    HEWEIDA: "合尾大",
    HEWEIXIAO: "合尾小",
    JIAQIN: "家禽",
    YESHOU: "野兽"
};

var Compare = {
    getSingleOrDouble: function (ball) {
        var ballNum = parseInt(ball);
        if (ballNum % 2 == 0) {
            return "double";
        } else if (ballNum == 49) {
            return null;
        } else {
            return "single";
        }
    },

    getBigOrSmall: function (ball) {
        var ballNum = parseInt(ball);
        if (ballNum <= 24) {
            return "small";
        } else if (ballNum == 49) {
            return null;
        } else {
            return "big";
        }
    },

    getTailBigOrSmall: function (ball) {
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

    getSumSingleOrDouble: function (ball) {
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

    getSumBigOrSmall: function (ball) {
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

    getTailSumBigOrSmall: function (ball) {
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
};

var Zodiac = {
    zodiacMap: [{
        id: "ZODIAC_SHU",
        name: "鼠"
    }, {
        id: "ZODIAC_NIU",
        name: "牛"
    }, {
        id: "ZODIAC_HU",
        name: "虎"
    }, {
        id: "ZODIAC_TU",
        name: "兔"
    }, {
        id: "ZODIAC_LONG",
        name: "龙"
    }, {
        id: "ZODIAC_SHE",
        name: "蛇"
    }, {
        id: "ZODIAC_MA",
        name: "马"
    }, {
        id: "ZODIAC_YANG",
        name: "羊"
    }, {
        id: "ZODIAC_HOU",
        name: "猴"
    }, {
        id: "ZODIAC_JI",
        name: "鸡"
    }, {
        id: "ZODIAC_GOU",
        name: "狗"
    }, {
        id: "ZODIAC_ZHU",
        name: "猪"
    }],

    getBallsByName: function (name) {
        var date = new Date(), year = date.getFullYear(), currentIndex = (year - 2008) % 12, index = -1, balls = [];
        for (var i = 0; i < this.zodiacMap.length; i++) {
            if (this.zodiacMap[i].name == name) {
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
            for (; ;) {
                balls.push(start);
                start += 12;
                if (start > 49) {
                    break;
                }
            }
        }
        return balls;
    },

    getNameByBall: function (ball) {
        var ballNum = parseInt(ball), date = new Date(), year = date
            .getFullYear(), currentIndex = (year - 2008) % 12;
        for (var start = 1, i = currentIndex; i >= 0; i--, start++) {
            var value = start, name = this.zodiacMap[i].name;
            for (; ;) {
                if (value == ballNum) {
                    return name;
                }
                value += 12;
                if (value > 49) {
                    break;
                }
            }
        }
        if (currentIndex < 11) {
            for (var start = 12, i = currentIndex + 1; i < 12; i++, start--) {
                var value = start, name = this.zodiacMap[i].name;
                for (; ;) {
                    if (value == ballNum) {
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

    getAnimalTypeByBall: function (ball) {
        if (parseInt(ball) == 49) {
            return null;
        }
        var name = this.getNameByBall(ball), beasts = ["鼠", "虎", "龙", "蛇",
            "兔", "猴"];
        if (name) {
            for (var i = 0; i < beasts.length; i++) {
                if (beasts[i] == name) {
                    return "beast";
                }
            }
        }
        return "poultry";
    },

    getNameToBalls: function () {
        var date = new Date(), year = date.getFullYear(), currentIndex = (year - 2008) % 12, balls = {};
        for (var start = 1, i = currentIndex; i >= 0; i--, start++) {
            var value = start, name = this.zodiacMap[i].name;
            for (; ;) {
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
                var value = start, name = this.zodiacMap[i].name;
                for (; ;) {
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

    getBallToNames: function () {
        var date = new Date(), year = date.getFullYear(), currentIndex = (year - 2008) % 12, balls = {};
        for (var start = 1, i = currentIndex; i >= 0; i--, start++) {
            var value = start, name = this.zodiacMap[i].name;
            for (; ;) {
                balls[value] = name;
                value += 12;
                if (value > 49) {
                    break;
                }
            }
        }
        if (currentIndex < 11) {
            for (var start = 12, i = currentIndex + 1; i < 11; i++, start--) {
                var value = start, name = this.zodiacMap[i].name;
                for (; ;) {
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
};
