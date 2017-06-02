/**
 * Функция для нахождения кратчайшего пути между точками, заданными точками
 * @param matrix матрица с исходными данными
 * @returns {*}
 */
function buildEasyWay(matrix) {

    //ОПРЕДЕЛЕНИЕ ПЕРЕМЕННЫХ И ФУНКЦИЙ

    var arrStoEnd = {};
    var arrEndToS = {};
    var ends = [];
    var starts = [];

    function savePointsForCircle(startPint, endPoint) {

        if(ends.indexOf(startPint) != -1 && starts.indexOf(endPoint) != -1) {
            var realS = arrEndToS[startPint]
            var realE = arrStoEnd[endPoint];
            delete arrEndToS[startPint];
            delete arrStoEnd[endPoint];
            ends.splice(ends.indexOf(startPint), 1);
            starts.splice(starts.indexOf(endPoint), 1);
            arrStoEnd[realS] = realE;
            arrEndToS[realE] = realS;
        } else  if(ends.indexOf(startPint) != -1) {
            var s = arrEndToS[startPint];
            delete arrEndToS[startPint];
            arrEndToS[endPoint] = s;
            arrStoEnd[s] = endPoint;
            ends.splice(ends.indexOf(startPint), 1);
            ends.push(endPoint);
        } else if (starts.indexOf(endPoint) != -1) {
            var rEnd = arrStoEnd[endPoint];
            starts.splice(starts.indexOf(endPoint), 1);
            starts.push(startPint);
            arrEndToS[rEnd] = startPint;
            delete arrStoEnd[endPoint];
            arrStoEnd[startPint] = rEnd;
        } else {
            arrStoEnd[startPint] = endPoint;
            arrEndToS[endPoint] = startPint;
            ends.push(endPoint);
            starts.push(startPint);
        }
    }


    function getCirclesIndexes() {
        return arrStoEnd;
        //return [starts, ends];
    }


    //ключ -- индекс точки из которой начинается движение, значение -- индекс города, куда идём
    var way = [];

    /**
     * Вспомогательная фнкция преобразования двумерного массива к объекту
     *
     * @param inputArr двумерный массив, который необходимо преобразовать к аналогичномуобъекту
     */
    function arrayToObject(inputArr) {
        var ans = {};
        for (var i = 0; i < inputArr.length; i++) {
            ans[i] = {};
            for (var j = 0; j < inputArr[i].length; j++) {
                ans[i][j] = inputArr[i][j];
            }
        }
        return ans;
    }

    /**
     * Реукрсивная функция заполнения для заполения массива "way". инкапсулирует всю логику алгоритма.
     *
     * @param arr матриуа с исходными данными на итерации. Должна предствлять из себя JavaScript объект
     */
    function calc(arr) {
        console.log('новая итерация расщётов')
        //printData(arr);
        var rowIndexis = Object.keys(arr); //массив индексов точек
        var columnIndexis = Object.keys(arr[rowIndexis[0]]);
        if(rowIndexis.length == 1) {
            way[rowIndexis] = columnIndexis[0];
            return;
        }
        var pintCount = rowIndexis.length;

        //редукция строк
        rowIndexis.forEach(function (rowIndex) {
            var minInRow = findMinInRow(arr[rowIndex], rowIndex); //нахождение минимального в каждой строке
            //вычитаем минимальное значние в каждой строке
            columnIndexis.forEach(function (columnIndex) {
                if(arr[rowIndex][columnIndex] != null) {
                    arr[rowIndex][columnIndex] = arr[rowIndex][columnIndex] - minInRow;
                }
            })
        })

        //редукция столбцов
        columnIndexis.forEach(function (columnIndex) {
            var minInColumn = findMinInColumn(arr, columnIndex, columnIndex);
            //вычитаем минимальное значние в каждой колонке
            rowIndexis.forEach(function (rowIndex, p2, p3) {
                if(arr[rowIndex][columnIndex] != null) {
                    arr[rowIndex][columnIndex] = arr[rowIndex][columnIndex] - minInColumn;
                }
            })
        })

        //редукция матрицы
        var targetCellRowIndex, targetCellColumnIndex;
        var targetCellRang;

        rowIndexis.forEach(function (i) {
            columnIndexis.forEach(function (j) {
                if (arr[i][j] == 0) {
                    var cellRang = calculateCellRang(arr, i, j);
                    if (cellRang >= targetCellRang || targetCellRang == undefined) {
                        targetCellRang = cellRang;
                        targetCellRowIndex = i;
                        targetCellColumnIndex = j;
                    }
                }
            })
        })

        way[targetCellRowIndex] = targetCellColumnIndex;
        savePointsForCircle(targetCellRowIndex, targetCellColumnIndex);
        if (rowIndexis.length <= 1) { //условние завершения работы алгоритма
            return;
        } else {
            var newArr = removePoints(arr, targetCellRowIndex, targetCellColumnIndex);
            calc(newArr);
        }
    }

    /**
     * Функция, удаляющая из матрицы колонку и строку с индексами columnIndexForDelete и rowIndexForDelete соответсвенно
     * @param arr исходный массив
     * @param rowIndexForDelete индекс строки для удаления
     * @param columnIndexForDelete индекс колонки для удаления
     * @returns {*} матрица с удалёнными строкой и столбцом
     */
    function removePoints(arr, rowIndexForDelete, columnIndexForDelete) {
        if (arr[columnIndexForDelete] != undefined && arr[columnIndexForDelete][rowIndexForDelete] != undefined) {
            arr[columnIndexForDelete][rowIndexForDelete] = null;
        }

        delete arr[rowIndexForDelete];
        var indexis = Object.keys(arr);
        indexis.forEach(function (di) {
            delete arr[di][columnIndexForDelete];
        })

        var circlesIndexis = getCirclesIndexes();
        var starts = Object.keys(circlesIndexis);
        starts.forEach(function(s, i, startArr) {
            if(arr[circlesIndexis[s]] != undefined && arr[circlesIndexis[s]][s] != undefined) {
                 arr[circlesIndexis[s]][s] = null;
             }
        })

        /*var circlesIndexis = getCirclesIndexes();
        circlesIndexis[1].forEach(function(sti){
            circlesIndexis[0].forEach(function(stj){
                 if(arr[sti] != undefined && arr[sti][stj] != undefined) {
                     arr[sti][stj] = null;
                 }
            });
        });*/
        return arr;
    }

    /**
     * Функция расчёта оценки для ячеки матрицы с данными
     * @param arrForRang исходная матрица
     * @param rangI индекс строки ячейки, для которой надо произвести расчёт
     * @param rangJ индекс колонки ячейки, для которой надо произвести расчёт
     * @returns {*} оцентка ячейки
     */
    function calculateCellRang(arrForRang, rangI, rangJ) {
        return findMinInRow(arrForRang[rangI], rangJ) + findMinInColumn(arrForRang, rangJ, rangI);
    }

    /**
     * Функция для нахождения минимального значения в массиве
     *
     * @param arr исходный одномерный массив
     * @param disabledIndex индекс значниея, которое не надо учитывать при расчётах
     * @returns {*} минимальное значние
     */
    function findMinInRow(arr, disabledIndex) {
        if (disabledIndex == undefined) {
            disabledIndex = -1;
        }
        var indexis = Object.keys(arr);
        var min = undefined;
        // = disabledIndex != indexis[0] ? arr[indexis[0]] : arr[indexis[1]];

        indexis.forEach(function (ind) {
            if (ind != disabledIndex && arr[ind] != null && ( min == undefined || arr[ind] < min )) {
                min = arr[ind];
            }
        })
        if(min == undefined) {
            min = 0;
        }
        return min;
    }

    /**
     * Функция для нахождения минимального значения в колонке двумерного массива
     *
     * @param arr исходный двумерный массив
     * @param columnIndex индекс колонки в массиве, для которой надо найти минимальное значение
     * @param disabledIndex индекс значниея строки, которое не надо учитывать при расчётах
     * @returns {*} минимальное значние в колонке
     */
    function findMinInColumn(arr, columnIndex, disabledIndex) {
        if (disabledIndex == undefined) {
            disabledIndex = -1;
        }
        var indexis = Object.keys(arr);
        var minInColumn = disabledIndex != 0 ? arr[indexis[0]][columnIndex] : arr[indexis[1 ]][columnIndex];
        indexis.forEach(function (i) {
            if (i != disabledIndex && arr[i][columnIndex]!= null &&( minInColumn == undefined || arr[i][columnIndex] < minInColumn )) {
                minInColumn = arr[i][columnIndex];
            }
        })
        if(minInColumn == undefined) {
            minInColumn = 0;
        }
        return minInColumn;
    }


    /**
     * Функция преобразовывающая данные из масива way в удобный вид
     *
     * @returns {Array} массив, содержащий в себе последовтальность индексов, соответсвующие кратчашему пути
     */
    function buildWay(wayArray) {
    console.log(wayArray);
        /*var startPointPretendent = generateSequense(wayArray.length);
        var startPoint;
        for (var i = 0; i < wayArray.length; i++) {
            if (wayArray[i] != undefined) {
                startPointPretendent.splice(startPointPretendent.indexOf(parseInt(wayArray[i])), 1);
            }
        }
        startPoint = startPointPretendent[0];
        */
        startPoint = 0//wayArray[0];
        var ans = [];
        var currentPoint = startPoint;
        do {
            ans.push(currentPoint);
            currentPoint = wayArray[currentPoint];
        }
        while (currentPoint != startPoint)
        return ans;
    }

    /**
     * Генерирует массив указанной длины, где значение каждого элемента равно индексу, под которым стоит этот элемент
     *
     * @param count размер массива
     * @returns {Array} сгенерированный масив
     */
    function generateSequense(count) {
        var ans = [];
        for (var seqNumber = 0; seqNumber < count; seqNumber++) {
            ans[seqNumber] = seqNumber;
        }
        return ans;
    }


    //ВЫПОЛНЕНИЕ РАСЧЁТОВ


    //преобразовываем входные данные в удобный для обработки вид
    if (Array.isArray(matrix)) {
        matrix = arrayToObject(matrix);
    }

    //производим расчёты
    calc(matrix);
    //преобразовываем выходные данные в удобный для дальнейшей работы вид
    return buildWay(way);
}
