//��ӡ�ļ���ǩ
function PrintLabel5025(
sender, 
PrinterName, 
Barcode,
Filecreatedate, 
Comedept, 
Createdate,  
Filename, 
Fileno, 
Emerg, 
Secret, 
Sendlevel, 
Createdept, 
Title, 
MainSendDept, 
Other, 
L_up, L_down, R_Up, R_down, offsetX, offsetY, PrintMachineryType){//��������ĳ���
	//if(PrinterName == "") return;
	PrinterName = "TSC";
	var res = "";
	var Dot = '^';
	var End = "^|";
			res = "GB0626-2005" + Dot;
			res += Barcode + Dot;
			res += Comedept + Dot;
			res += Filename + Dot;
			res += Fileno + Dot;
			res += MainSendDept + Dot;
			res += Title + Dot;
			res += Secret + Dot;
			res += Emerg + Dot;
			res += Filecreatedate + Dot;
			res += Sendlevel + Dot;
			res += Createdept + Dot;
			res += Createdate + Dot;
			res += Other + End;
	var LabelInfo=res;
	var b = myLenA(LabelInfo);
	document.all(sender).setLisenceCode('DFKYBAROCX0602');
	
	document.all(sender).myOpenPrinter(PrinterName, 0, 0, PrintMachineryType);
	if (PrintMachineryType == "2") document.all(sender).mySetLabel(true, true, 4, 3);
	document.all(sender).myInitLabel(50, 25);
	document.all(sender).myPrintString(20 + offsetX, 150 + offsetY, "����", 32, L_up, 0, 0);
	if  (b > 264) {     //265 �ֽ�����
		document.all(sender).myPrintPDF417(offsetX, 30 + offsetY, 390, 190, LabelInfo, 0, 3, 2, 3, 40, 40);
	}
	else {
		if (b > 150){     //151�� 264 �ֽ�����
			document.all(sender).myPrintPDF417(offsetX, 30 + offsetY, 390, 190, LabelInfo, 0, 3, 2, 4, 30, 30);
		}
		else {
			if ( b > 130) {  //131��150 �ֽ�����
				document.all(sender).myPrintPDF417(offsetX, 30 + offsetY, 390, 190, LabelInfo, 0, 3, 2, 5, 30, 30);
			}
			else {
				if (b > 100) {   //101��130 �ֽ�����
					document.all(sender).myPrintPDF417(offsetX, 30 + offsetY, 390, 190, LabelInfo, 0, 4, 2, 5, 30, 30);
				}
				else {
					if( b > 60) {  //'61��100  �ֽ�����
						document.all(sender).myPrintPDF417(offsetX, 30 + offsetY, 390, 190, LabelInfo, 0, 4, 2, 6, 30, 30);
					}
					else {
						document.all(sender).myPrintPDF417(offsetX, 30 + offsetY, 390, 190, LabelInfo, 0, 5, 2, 6, 30, 30);
					}
				}
			}
		}
	}
	document.all(sender).myPrintLabel();
	document.all(sender).myClosePrinter();
}

//�ж��ַ����ĳ���
function myLenA(myString){
    var intLen = 0;
    for (var i=0; i<myString.length; i++){
        if ((myString.charCodeAt(i) > 0) && (myString.charCodeAt(i) < 128))
            intLen += 1;
        else
            intLen += 2;
    }
    return intLen;
}
function myLenB(myString){
    var intLen = 0;
    for (var i=0; i<myString.length; i++){
        if ((myString.charCodeAt(i) > 0) && (myString.charCodeAt(i) < 128))
            intLen += 1;
        else
            intLen += 2;
    }
    return intLen;
}
function _PagePrint(dir){
	var dlg = dir+"activex/PrintPage.aspx";
	var res = window.showModalDialog(dlg,"","status:no;resizable:no;help:no;scroll:no;dialogHeight:240px;dialogWidth:350px;center:yes");
	return res;
}