/* global jQuery */
/*eslint-disable*/

'use strict';

// API Url

var apiUrl = 'http://localhost:8089/PPSignSDK/';
var serverUrl = 'http://localhost:8089/PPSignSDK/';
//var apiUrl = 'https://localhost:8090/PPSignSDK/';
// initialize device web api

var initUrl = apiUrl + 'InitialDevice?id=2&width=580&height=380';
// uninitialize device web api
var uninitUrl = apiUrl + 'UninitialDevice?id=2';
// get ink web api
var getInkUrl = apiUrl + 'GetInks';
// clear ink api
var clrInkUrl = apiUrl + 'Clear';
// open & close LCD api
var oplcdUrl = apiUrl + 'OpenLCD';
var cllcdUrl = apiUrl + 'CloseLCD';
// get pen width api
var penwidthUrl = apiUrl + 'SetPenWidth?Width=';
// get pen style api
var penstyUrl = apiUrl + 'SetPenStyle?Style=';
// get save drawing api
var savedrawUrl = apiUrl + 'SaveDrawingImage?';
// get size api
var getsizeUrl = apiUrl + 'GetSize';
// get point api
var getpointUrl = apiUrl + 'GetPointer';
// get about api
var aboutUrl = apiUrl + 'About';
// get Version ID api ( for L398 & E560 )
var veridUrl = apiUrl + 'VersionID';
// get pen ID api ( for L398 & E560 )
var penidUrl = apiUrl + 'PenID';
// get pad ID api ( for L398 & E560 )
var padidUrl = apiUrl + 'PadID';
// Display device information in LCD api ( only for L398 )
var diilUrl = apiUrl + 'DisplayDeviceInfoInLCD?show=';
// Get device information api
var devinfoUrl = apiUrl + 'GetDeviceInfo?type=';
// get Encode api
var encodeUrl = apiUrl + 'Encode?type=';
// get Decode api
var decodeUrl = apiUrl + 'Decode?type=';
// get Set clip api
var setclipUrl = apiUrl + 'SetClip';
// get valid api
var validUrl = apiUrl + 'IsValid';
// save device data api
var savedataUrl = apiUrl + 'SaveDeviceData';
// read device data api
var readdataUrl = apiUrl + 'ReadDeviceData';
// save device data api
var cleardataUrl = apiUrl + 'ClearDeviceData';
// Get Decode File Path
var decodepathUrl = apiUrl + 'GetDecodeFilePath';
// GetDeviceConfirmOrCancelKeyStatus
var confirmStatusUrl = apiUrl + 'GetDeviceConfirmOrCancelKeyStatus';
// Save draw video
var saveDrawingVideoUrl = apiUrl + 'SaveDrawingVideo';
// Get video base64 code
var getDrawingVideoBase64DataUrl = apiUrl + 'GetDrawingVideoBase64Data';
// Enable Save Video Data
var enableSaveVideoDataUrl = apiUrl + 'EnableSaveVideoData?show=1';
var disableSaveVideoDataUrl = apiUrl + 'EnableSaveVideoData?show=0';

var TestApi = apiUrl + 'Test';

var canvas;
var context;

var isPolling = false;

var filenameCache = '';
var checkVideoIsGeneratge = false;
var isFirstPlay = true;

var LastVideoBase64Resp;

function TestHandShake() {
    //console.log('before TestHandShake');
    $.ajax({
        url: TestApi,
        type: 'GET',
        cache: false
    }).done(function () {
        //console.log('TestHandShake complete');
    }).fail(function () {});
}

// initialize device
function initDevice() {
    //event.preventDefault();

    $.ajax({
        url: initUrl,
        type: 'GET',
        cache: false
    }).done(function (response) {
        if (response === true) {
            isPolling = true;
            getInk();
            getStatus();
            $('.init').removeAttr('disabled');
            $('#initBtn').attr('disabled', 'disabled');
//            document.getElementById('VideoStatusBtn').innerHTML = 'Enable';
        } else {
            alert('初始化失败');
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert('Connection fail(' + jqXHR.status + ')!');
    });
}

// uninitialize device
function uninitDevice() {
    //event.preventDefault();
    isPolling = false;

    $.ajax({
        url: uninitUrl,
        type: 'GET',
        cache: false
    }).done(function (response) {
        if (response === true) {

            var encodeArea = $('#encode');
            var decodeArea = $('#decode');
            var canvas = document.getElementById('ppCanvas');
            context.clearRect(0, 0, canvas.width, canvas.height);
            encodeArea.html("");
            decodeArea.html("");

            $('.init').attr('disabled', 'disabled');
            $('.videoControl').attr('disabled', 'disabled');
            $('#initBtn').removeAttr('disabled');
        };
    });
}

// switch initialize
$('#initSwitch').click(function () {

    if ($(this).prop('checked')) {
        initDevice();
    } else {
        uninitDevice();
    }

    console.log($(this));
    console.log($(this).prop('checked'));
});

// initialize canvas and setup context
window.onload = function () {
    TestHandShake();
    canvas = document.getElementById('ppCanvas');

    if (canvas.getContext) {
        context = canvas.getContext('2d');
    }
};

window.onbeforeunload = function (e) {
    if (isPolling) {
        uninitDevice();
        var milliseconds = 1000;
        var start = new Date().getTime();

        while (true) {
            if (new Date().getTime() - start > milliseconds) {
                break;
            }
        }
    }
};

function getStatus() {
    // 用polling的方式向self-host發送request取得簽名板按鈕status
    (function poll() {
        var timeId = setTimeout(function () {

            clearTimeout(timeId);

            // 取得狀態
            if (isPolling) {
                $.ajax({
                    url: confirmStatusUrl,
                    type: 'GET',
                    cache: false
                }).done(function (result) {
                    //console.log('poll-confirmStatusUrl ' +result);
                    if (result == '1') {
                        alert('Notify From Device : Confirmed.');
                    } else if (result == '0') {
                        alert('Notify From Device : Cancel !');
                        clearInk();
                    }
                }).fail(function () {
                    console.log('Fail to get confirmed status!');
                }).always(function () {
                    if (isPolling) {
                        poll();
                    } else {}
                });
            }
        }, 500);
    })();
};

function enableSaveVideoData() {
    $.ajax({
        url: enableSaveVideoDataUrl,
        type: 'GET',
        cache: false
    }).done(function () {
        clearInk();
        document.getElementById('VideoStatusBtn').innerHTML = 'Disable';
        $('.videoControl').removeAttr('disabled');
    });
};

function disableSaveVideoData() {
    $.ajax({
        url: disableSaveVideoDataUrl,
        type: 'GET',
        cache: false
    }).done(function () {
        clearInk();
        document.getElementById('VideoStatusBtn').innerHTML = 'Enable';
        $('.videoControl').attr('disabled', 'disabled');
    });
};

function getInk() {
    // 用polling的方式向self-host發送request取得簽名板畫面(base64格式)
    (function poll() {
        var timeId = setTimeout(function () {

            clearTimeout(timeId);

            if (isPolling) {
                $.ajax({
                    url: getInkUrl,
                    type: 'GET',
                    cache: false
                }).done(function (data) {
                    //console.log('poll-getInkUrl');
                    var dataInfos = JSON.parse(data);

                    dataInfos.forEach(function (value) {
                        if (value.EventType === 0) {
                            drawImage(value.Image);
                        }
                    });
                }).always(function () {
                    if (isPolling) {
                        poll();
                    } else {}
                });
            }
        }, 50);
    })();
};

/*function getInk() {
    // 用polling的方式向self-host發送request取得簽名板畫面(base64格式)
    (function poll() {
        var timeId = setTimeout(function() {
              clearTimeout(timeId);
              $.ajax({
                url: getInkUrl,
                type: 'GET'
            }).done(function(data) {
                var dataInfos = JSON.parse(data);
                  dataInfos.forEach(function(value) {
                    if(value.EventType === 0) {
                        drawImage(value.Image);
                    }
                });
                  // 取得狀態
                $.ajax({
                    url: confirmStatusUrl,
                    type: 'GET'
                })
                .done(function(result) {
                    if (result == '1') {
                        alert('Notify From Device : Confirmed.');
                    } else if (result == '0') {
                        alert('Notify From Device : Cancel !');
                        clearInk();
                    }
                })
                .fail(function() {
                    console.log('Fail to get confirmed status!');
                });
            }).always(function() {
                if(isPolling) {
                    poll();
                } else {}
            });
        }, 20);
    }());
};*/

function drawImage(base64) {
    var dataUrl = 'data:image/png;base64,';

    dataUrl = dataUrl + base64;

    // 在image中載入圖檔，再畫到canvas呈現
    var img = new Image();

    img.addEventListener('load', function () {
        context.drawImage(this, 0, 0, canvas.width, canvas.height);
    }, false);

    img.src = dataUrl;
};

// clear Ink
function clearInk() {

    $.ajax({
        url: clrInkUrl,
        type: 'GET',
        cache: false,
        success: function success() {
            var canvas = document.getElementById('ppCanvas');

            context.clearRect(0, 0, canvas.width, canvas.height);
        }
    });
};

// open lcd
function openLcd() {

    $.ajax({
        url: oplcdUrl,
        type: 'GET',
        cache: false
    });
    $('.shutdown').fadeOut('fast');
};
// close lcd
function closeLcd() {

    $.ajax({
        url: cllcdUrl,
        type: 'GET',
        cache: false
    });
    $('.shutdown').fadeIn('fast');
};

// pen width change case
function pwChange() {
    var pwVal = $('#penWidth').val();

    $.ajax({
        url: penwidthUrl + pwVal,
        type: 'GET',
        cache: false
    }).done(function () {
        clearInk();
    });

    // alert( penwidthUrl + pwVal );
};

//  pen style change case
function psChange() {
    var psVal = $('#penStyle').val();

    $.ajax({
        url: penstyUrl + psVal,
        type: 'GET',
        cache: false
    });

    // alert(penstyUrl + psVal);
};

// save drawing images
function saveDrawing() {
    var getsdType = $('#sdType').val();
    var getsdDpi = $('#sdDpi').val();
    var localPath = 'DrawImage_' + generateString(5);
    var sdT, sdD;

    switch (getsdType) {
        case '1':
            sdT = '.BMP';
            break;
        case '2':
            sdT = '.JPG';
            break;
        case '3':
            sdT = '.PNG';
            break;
        case '4':
            sdT = '.GIF';
            break;
        case '5':
            sdT = '.TIFF';
            break;
        case '7':
            sdT = '.PDF';
            break;
        case '8':
            sdT = '.SVG';
            break;
    };

    if (getsdDpi == '0') {
        sdD = '150';
    } else {
        sdD = '300';
    };

    $.ajax({
        url: savedrawUrl + 'type=' + getsdType + '&dpi=' + getsdDpi + '&path=' + localPath + sdT,
        type: 'GET',
        cache: false
    }).done(function (resp) {
        if (Boolean(parseInt(resp))) {
            alert('Save file ' + localPath + ' failed' + ', result=' + resp);
        } else {
            if (navigator.userAgent.indexOf("WOW64") != -1 || navigator.userAgent.indexOf("Win64") != -1) {
                alert('File：' + resp + ' saved\nDpi：' + sdD);
            } else {
                alert('File：' + resp + ' saved\nDpi：' + sdD);
            }
        }
    });
};

// save drawing video
function saveVideo() {
    var getsvType = $('#svType').val();
    var getsvFps = $('#svFps').val();
    var localPath = 'DrawVideo_' + generateString(5);
    var svT;

    switch (getsvType) {
        case '1':
            svT = '.mp4';
            break;
        case '2':
            svT = '.wmv';
            break;
    };

    $.ajax({
        url: saveDrawingVideoUrl + '?type=' + getsvType + '&fpsindex=' + getsvFps + '&path=' + localPath + svT,
        type: 'GET',
        cache: false
    }).done(function (resp) {
        ///////////////////////////////////////////////////
        $.ajax({
            url: getDrawingVideoBase64DataUrl,
            type: 'GET',
            cache: false
        }).done(function (resp2) {
            console.log('getDrawingVideoBase64DataUrl');
            LastVideoBase64Resp = resp2;

            if (Boolean(parseInt(resp2))) {
                alert('Save video ' + localPath + ' failed' + ', result=' + resp2);
            } else {
                var RespStruct = JSON.parse(LastVideoBase64Resp)[0];

                if (navigator.userAgent.indexOf("WOW64") != -1 || navigator.userAgent.indexOf("Win64") != -1) {
                    alert('File：' + resp + ' saved\n' + 'Received base64 data size :' + RespStruct.Image.length + '\n' + 'Rechecked base64 data size :' + RespStruct.ImageLength);
                } else {
                    alert('File：' + resp + ' saved\n' + 'Received base64 data size :' + RespStruct.Image.length + '\n' + 'Rechecked base64 data size :' + RespStruct.ImageLength);
                }

                filenameCache = resp;

                if (!checkVideoIsGeneratge && !isFirstPlay) {
                    playback();
                }
                isFirstPlay = true;
                checkVideoIsGeneratge = true;
            }
        }).fail(function () {}).always(function () {});
        ///////////////////////////////////////////////////
    });
};

function videoStatusClick() {
    var type = document.getElementById('VideoStatusBtn').innerHTML;
    if (type === 'Enable') {
        enableSaveVideoData();
    } else {
        disableSaveVideoData();
    }
};
// playback drawing video
function playbackButtonClick() {
    isFirstPlay = false;
    checkVideoIsGeneratge = false;

    if (checkVideoIsGeneratge) {
        playback();
    } else {
        saveVideo();
    }
};

function playback() {
    var VideoFormat = JSON.parse(LastVideoBase64Resp)[0];
    var videoBase64 = 'data:video/mp4;base64,' + VideoFormat.Image;
    $('#playbackModal').modal('show');
    $('#playback-video').get(0).src = videoBase64;
    /*$.ajax({
        url: getDrawingVideoBase64DataUrl,
        type: 'GET'
    })
    .done(function(resp) {
        var VideoFormat = JSON.parse(resp)[0];
        console.log(VideoFormat);
        var videoBase64 = 'data:video/mp4;base64,' + VideoFormat.Image;
        $('#playbackModal').modal('show');
        $('#playback-video').get(0).src = videoBase64;
    })
    .fail(function() {
        console.log("error");
    })
    .always(function() {
        console.log("complete");
    });*/
}

// Set clip
function setClip() {

    var scWidth = $('#scWidth').val();
    var scHeight = $('#scHeight').val();

    if (!scWidth || scWidth < 0) {
        alert('Insert Width!');
        return;
    };
    if (!scHeight || scHeight < 0) {
        alert('Insert height!');
        return;
    };

    var canvas = document.getElementById('ppCanvas');
    context.clearRect(0, 0, canvas.width, canvas.height);

    $.ajax({
        url: setclipUrl + '?width=' + scWidth + '&height=' + scHeight,
        type: 'GET',
        cache: false
    });
};

// get size
function getSize() {

    $.ajax({
        url: getsizeUrl,
        type: 'GET',
        cache: false,
        success: function success(data) {
            if (data == '-8') {
                alert('Ink Empty.\nresult=' + data);
            } else {
                alert('result=' + data);
            }
        },
        error: function error() {
            alert('Get size fail!');
        }
    });
};

// get pointer
function getPointer() {
    var pointContant = $('#pointContant');

    pointContant.empty();
    $.ajax({
        url: getpointUrl,
        type: 'GET',
        cache: false,
        dataType: 'JSON',
        success: function success(resp) {
            var oJson = jQuery.parseJSON(resp);
            var dataLength = oJson.length;

            if (dataLength === 0) {
                alert('Point information is empty.');
            } else {
                $('#myModal').modal('show');
                for (var i = 0; i < dataLength; i++) {
                    pointContant.append('<tr><td>' + oJson[i].x + '</td><td>' + oJson[i].y + '</td><td align="right">' + oJson[i].pressure + '</td><td align="right">' + oJson[i].bStrokeEnd + '</td><td align="right">' + oJson[i].Time + '</td></tr>');
                }
            }
        },
        error: function error() {
            alert('Get point information fail!');
        }
    });
};

// get about
function getAbout() {

    $.ajax({
        url: aboutUrl,
        type: 'GET',
        cache: false
    });
};

// get version id
function getVerid() {

    $.ajax({
        url: veridUrl,
        type: 'GET',
        cache: false
    }).done(function (data) {
        alert(data);
    });
};

// get pad id
function getPadid() {

    $.ajax({
        url: padidUrl,
        type: 'GET',
        cache: false
    }).done(function (data) {
        alert(data);
    });
};

// get pen id
function getPenid() {

    $.ajax({
        url: penidUrl,
        type: 'GET',
        cache: false
    }).done(function (data) {
        alert(data);
    });
};

// show device info in lcd
function showDiilcd() {

    $.ajax({
        url: diilUrl + '1',
        type: 'GET',
        cache: false
    });
};

// hide device info in lcd
function hideDiilcd() {

    $.ajax({
        url: diilUrl + '0',
        type: 'GET',
        cache: false
    });
};

// get device information
function getDevinf() {

    var diVal = $('#devInfo').val();

    $.ajax({
        url: devinfoUrl + diVal,
        type: 'GET',
        cache: false
    }).done(function (data) {
        if (diVal == 1) {
            if (data === "true") {
                alert('Connected\nresult=' + data);
            } else {
                alert('Disconnected\nresult=' + data);
            }
        } else {
            alert(data);
        }
    });
};

// Encode
function encode() {

//    var encodeType = $('#encodeType').val();
    var encodeType = 2;
    var encodeArea = $('#encode');

    $.ajax({
        url: encodeUrl + encodeType,
        type: 'GET',
        cache: false
    }).done(function (data) {
    	//得到
//        encodeArea.html(data);
        saveSign(data);
    });
};

// Decode
function decode() {
    var encodeContent = $('#encode').val();
    var encodeType = $('#encodeType').val();
    var decodeArea = $('#decode');
    var decodeFormat;

    switch (encodeType) {
        case '1':
            decodeFormat = '.BMP';
            break;
        case '2':
            decodeFormat = '.JPG';
            break;
        case '3':
            decodeFormat = '.PNG';
            break;
        case '4':
            decodeFormat = '.GIF';
            break;
        case '5':
            decodeFormat = '.TIFF';
            break;
    }

    if (encodeType == 6) {
        $.ajax({
            url: decodeUrl + encodeType,
            type: 'POST',
            cache: false,
            data: encodeContent,
            success: function success(resp) {
                decodeArea.append('X\t\t\t\tY\t\t\t\tbStrokeEnd\n');
                var data = JSON.parse(resp);
                for (var i = 0; i < data.length; i++) {
                    var x = data[i].x,
                        y = data[i].y,
                        bStrokeEnd = data[i].bStrokeEnd;

                    decodeArea.append(x + '\t\t\t\t' + y + '\t\t\t\t' + bStrokeEnd + '\n');
                }
            }
        });
    } else {
        $.ajax({
            url: decodeUrl + encodeType + '&path=Decode_Image_' + generateString(5) + decodeFormat,
            type: 'POST',
            cache: false,
            data: encodeContent,
            success: function success() {
                $.ajax({
                    url: decodepathUrl,
                    type: 'GET',
                    cache: false,
                    success: function success(resp) {
                        alert('File：' + resp + '\nSaved.');
                    }
                });
            }
        });
    };
};

// Get Valid
function getValid() {

    $.ajax({
        url: validUrl,
        type: 'GET',
        cache: false,
        success: function success(data) {
            if (data) {
                alert('Protect is Valid');
            } else {
                alert('Protect is Not Valid');
            }
        }
    });
};

// Save Device Data
function saveData() {
    var svData = $('#svData').val();
    var svPath = '?path=sample_save.txt';

    $.ajax({
        url: savedataUrl + svPath,
        type: 'GET',
        cache: false,
        data: {
            index: svData
        }
    }).done(function (result) {
        alert('Upload ' + result + ' data into device.');
    });
};

// Read Device Data
function readData() {
    var reData = $('#reData').val();
    var rePath = '?path=sample_read.txt';

    $.ajax({
        url: readdataUrl + rePath,
        type: 'GET',
        cache: false,
        data: {
            index: reData
        }
    }).done(function (resp) {
        alert("Download device data to " + resp);
    });
};

// Clear Device Data
function clearData() {
    var clData = $('#clData').val();

    $.ajax({
        url: cleardataUrl,
        type: 'GET',
        cache: false,
        data: {
            index: clData
        }
    }).done(function (resp) {
        if (resp == 0) {
            alert('Clear Data success.');
        } else {
            alert('Clear Data failed.');
        }
    });
};

// Generate random number
function generateString(length) {
    var text = "";
    var possible = "0123456789";

    for (var i = 0; i < length; i++) {
        text += possible.charAt(Math.floor(Math.random() * possible.length));
    }return text;
}