
$(function() {
	// 获取对象res/Ivideo.cab classid="clsid:8CAA584A-AC84-445B-89D6-D4BD455EAF96">
	// var iVideo = document.getElementById("iVideo");//document.iVideo;
	var iVideo = document.iVideo;
	
	// 打开设备
	$('#opendevices').click(
			function() {
				var result = iVideo.OpenDevices();
				if (result == 1) {
					log('打开设备成功!');
					var result = iVideo.GetListResolution();
					if (result != "") {
						var items = result.split(',');
						for (var i = 0; i < items.length; i++) {
							$('#VideoResolution').append(
									"<option value='" + i + "'>" + items[i] + "</option>");
						}
						log("读取视频能力成功")
					}
				} else {
					log('打开设备失败!')
				}
			})
	// 关闭设备
	$('#closedevices').click(function() {
		var result = iVideo.CloseDevices();
		if (result == 1) {
			log('关闭设备成功!');
		} else {
			log('关闭设备失败!')
		}
	})
	// 视频旋转
	$('.rotate').click(function() {
		var angle = $(this).attr('data-angle');
		var result = iVideo.SetRotate(angle);
		if (result == 1) {
			log('视频旋转成功!');
		} else {
			log('视频旋转失败!')
		}
	})

	// 拍摄类型->彩色/灰度/被白
	$('input[name=color]').change(function() {
		var mode = $(this).val();
		var result = iVideo.SetColorMode(mode);
		if (result == 1) {
			log('设置成功!');
		} else {
			log('设置失败!')
		}
	});

	// 矫正类型->不矫正/智能矫正/手动裁切
	$('input[name=crop]').change(function() {
		var mode = $(this).val();
		var result = iVideo.SetCropMode(mode);
		if (result == 1) {
			log('设置成功!');
		} else {
			log('设置失败!')
		}
	});
	// 开启/关闭条码识别
	$('#barcode').click(function() {
		var result = iVideo.SetAutoExposure($(this).prop('checked'));
		if (result == 1) {
			log("操作成功");
		} else {
			log("操作失败");
		}
	});
	// 开启/关闭自动曝光
	$('#autoExposure').click(function() {
		var result = iVideo.SetAutoExposure($(this).prop('checked'));
		if (result == 1) {
			log("操作成功");
		} else {
			log("操作失败");
		}
	});
	// 曝光度调节
	$('.setExposure').click(function() {
		var _value = $('.Exposure').val();
		var type = $(this).val();
		if (type == '<') {
			_value--;
		} else {
			_value++;
		}
		$('.Exposure').val(_value);
		var result = iVideo.SetExposure(_value);
		if (result == 1) {
			log("操作成功");
		} else {
			log("操作失败");
		}
	})
	// 拍摄
	$('#snap').click(function() {
		var result = iVideo.GetSnap();
		if (result == 1) {
			log("拍摄成功");
			if ($('#barcode').prop('checked')) {
				if (iVideo.Barcode != "") {
					alert("扫到条码/二维码:" + iVideo.Barcode);
				} else {
					alert('没有识别到任何条码/二维码');
				}
				;
			}
			;
			// 保存快照
			var imgpath = "C:\\temp\\"+ new Date().getTime()+".jpg";
			var i = iVideo.SaveSnap(imgpath, quality); // 保存路径和
			uploadFile(imgpath);													// 压缩比例,图片的格式和后缀.
																// 自行调节
		} else {
			log("拍摄失败");
		}
	});
	// 打开视频属性
	$('#DevicesProperty').click(function() {
		var result = iVideo.OpenPropertyPage();
		if (result == 1) {
			log("操作成功");
		} else {
			log("操作失败");
		}
	});
	// 切换摄像头
	$('#btnToggleDevice').click(function() {
		var result = iVideo.ToggleDevice();
//		var result = iVideo.OpenDevices();
		alert(result);
		if (result == 1)
			log("切换成功")
		else
			log("切换失败")
	})
	// 切换视频能力
	$('#VideoResolution').change(function() {
		var result = iVideo.SetVideoResolution($(this).val())
		if (result == 1)
			log("切换成功")
		else
			log("切换失败")
	})
	// 压缩比例 ,默认80
	var quality = 80
	// 压缩比例调节
	$('.setQuality').click(function() {
		var _value = $('.Quality').val();
		// 压缩比例应该在1-100之间
		var type = $(this).val();
		if (type == '<') {
			if (_value > 1)
				_value--;
		}
		if (type == '>') {
			if (_value < 100)
				_value++;
		}
		$('.Quality').val(_value);
		quality = _value;
		log("操作成功");
	})
	// 设置图片保存格式
	$('input[name=imageFormat]').change(function() {
		var mode = $(this).val();
		var result = iVideo.SetImageFormat(mode);
		if (result == 1) {
			log('设置成功!');
		} else {
			log('设置失败!')
		}
	});
	// 拍照存入PDF图片列表
	$('#PDFImage').click(function() {
		var result = iVideo.GetSnap();
		if (result == 1) {
			log("拍摄成功");
			if ($('#barcode').prop('checked')) {
				if (iVideo.Barcode != "") {
					alert("扫到条码/二维码:" + iVideo.Barcode);
				} else {
					alert('没有识别到任何条码/二维码');
				}
			}
			// 保存快照
			var imgpath = "C:\\temp\\Crop\\"+ new Date().getTime()+".jpg";
			var i = iVideo.SaveSnap(imgpath, quality); // 保存路径和
		} else {
			log("拍摄失败");
		}
		iVideo.AddPdfImageFilePath(imgpath);
	});
	// 生成PDF
	$('#CreatePdf').click(function() {
		var pdfpath = "C:\\temp\\Crop\\"+ new Date().getTime()+".pdf";
		var result = iVideo.CreatePDF(pdfpath, false);
		if (result) {
			log("生成PDF成功");
			uploadFile(pdfpath);	
		} else {
			log("拍摄失败");
		}
	});
	function log(msg) {
		$('#log').val(msg + "(" + new Date().toLocaleTimeString() + ")" + "\n" + $('#log').val());
	}
	
})
