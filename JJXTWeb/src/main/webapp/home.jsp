<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

<head>    
<meta content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Document</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css"></link>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/bootstrap/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/china.js"></script>
    <style>#china-map {width:900px; height: 600px;float: left}
    	#provinceTab tr td {padding :0px 8px 0px 5px }	
    </style>
</head>
<body>
	
	<div class="col-lg-2" id="div1" style="width: 1130px;height:250px;"></div>
    <div id="china-map"></div>
    <div style="float: left">
    	<table id="provinceTab" style="" class="table table-hover table-bordered">
    	</table>
    </div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/home.js"></script>
</body>

</html>
