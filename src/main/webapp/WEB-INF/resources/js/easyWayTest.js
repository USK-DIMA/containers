var m = null;
var input1 = [
    [m, 5, 11, 9],
    [10, m, 8, 7],
    [7, 14, m, 8],
    [12, 6, 15, m]
];

var input2 = [
    [null, 19, 1, 14, 18, 9],
    [14, null, 2, 11, 11, 19],
    [7, 1, null, 8, 16, 14],
    [0, 7, 7, null, 1, 10],
    [8, 10, 11, 14, null, 8],
    [19, 14, 17, 20, 14, null]
];


 var input3 = [
     [null, 6, 5, 15, 15, 10],
     [3, null, 12, 8, 19, 13],
     [13, 11, null, 20, 8, 1],
     [9, 17, 19, null, 6, 10],
     [4, 11, 8, 13, null, 5],
     [8, 13, 13, 6, 14, null]
 ];

 var ans3 = [2, 5, 3, 4, 1, 0];

  var input4 = [
      [null, 6, 5, 15, 15, 10],
      [3, null, 12, 8, 19, 13],
      [13, 11, null, 20, 8, 1],
      [9, 17, 19, null, 6, 10],
      [4, 11, 8, 13, null, 5],
      [8, 13, 13, 6, 14, null]
  ];



var maxDistanse = 20;
function generateMatrixForPerfomanseTest(size) {
    var ans = [];
    for (var i = 0; i < size; i++) {
        ans[i] = [];
        for (var j = 0; j < size; j++) {
            if (i == j) {
                ans[i][j] = null;
            } else {
                ans[i][j] = parseInt(randomInteger(1, maxDistanse));
            }
        }
    }
    return ans;
}

function randomInteger(min, max) {
    var rand = min + Math.random() * (max + 1 - min);
    rand = Math.floor(rand);
    return rand;
}

test(input3, ans3);
test(input1);
test(input2);
test(input3);
test(generateMatrixForPerfomanseTest(66));
var cache;
function test(testInput, expected) {
    cache = testInput;
    var time = performance.now();
    var real = buildEasyWay(testInput)
    time = performance.now() - time;
    console.log('Кол-во точек: ' + testInput.length + '. Время выполнения = ', time);
    console.log('Полученый ответ: ' + real);
    console.log('СОВПАДЕНИЕ ПО КОЛ-ВУ: ' + (testInput.length == real.length));
    if(expected != undefined && arrayEqueals(real, expected)) {
        console.log('ПРОЙДЕН!');
    }
    console.log('=============================');
}

function arrayEqueals(a1, a2) {
    if(a1.length != a2.length) {
        return false;
    }
    for(var i =0; i<a1.length; i++) {
        if(a1[i] != a2[i]) {
            return false;
        }
    }
    return true;
}


function printData(arr) {
    var rowIndexis = Object.keys(arr); //массив индексов точек
    var columnIndexis = Object.keys(arr[rowIndexis[0]]);

    console.log("============================");
    var str = '    ';
    columnIndexis.forEach(function (cIndex) {
            str += cIndex + '   ';
    })
    console.log(str);
    console.log('----------------------------------');


    rowIndexis.forEach(function (index) {
        var str = index + ' ||';
        columnIndexis.forEach(function (cIndex) {
            str += arr[index][cIndex] + '   ';
        })
        console.log(str)
    })
    console.log("============================");
}
