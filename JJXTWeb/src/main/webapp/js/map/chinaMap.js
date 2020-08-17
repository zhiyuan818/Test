$(function() {
	$("#class").change(function(){
		loadData();
	});
	setInterval(function(){
		findDataChart();
	},1000*30);
	$("#data").change(function(){
		var data=$("#data").val();
		if(data!=null){
			if(data.length>1){
				$("#div")[0].style.display='block';
				$("#type option:first").prop("selected", 'selected'); 
			}else{
				$("#div")[0].style.display='none';
				$("#type option:first").prop("selected", 'selected'); 
			}
		}
		$("#type").selectpicker("refresh");
	});
	
	
	$("#btn_sub").click(function(){
		var cla=$("#class").val();
		var type=$("#type").val();
		var data=$("#data").val();
		if(cla !="平台"){
			if(data==null || data.length==0){
				if(cla =="acc"){
					$('.popup_de .show_msg').text('至少选择一个账号');
				}else{
					$('.popup_de .show_msg').text('至少选择一个通道');
				}
				$('.popup_de').addClass('bbox');
				$('.popup_de .btn_submit').one('click', function() {
					$('.popup_de').removeClass('bbox');
				})
				return ;
			}
		}
		$("#oldClass").val(cla);
		$("#oldData").val(data);
		$("#oldType").val(type);
		findDataChart();
	});
	
	
	$("#btn_clo").click(function(){
		$("#class option:first").prop("selected", 'selected');
		$("#type option:first").prop("selected", 'selected');
		$("#data").val('');
		$("select").selectpicker("refresh");
		load();
	});
	$("#btn_info").click(function(){
		$("#addBody").modal();
	});
})
var data = [
    {name:"北京",value:0},
    {name:"天津",value:0},
    {name:"河北",value:0},
    {name:"山西",value:0},
    {name:"内蒙古",value:0},
    {name:"辽宁",value:0},
    {name:"吉林",value:0},
    {name:"黑龙江",value:0},
    {name:"上海",value:0},
    {name:"江苏",value:0},
    {name:"浙江",value:0},
    {name:"安徽",value:0},
    {name:"福建",value:0},
    {name:"江西",value:0},
    {name:"山东",value:0},
    {name:"河南",value:0},
    {name:"湖北",value:0},
    {name:"湖南",value:0},
    {name:"重庆",value:0},
    {name:"四川",value:0},
    {name:"贵州",value:0},
    {name:"云南",value:0},
    {name:"西藏",value:0},
    {name:"陕西",value:0},
    {name:"甘肃",value:0},
    {name:"青海",value:0},
    {name:"宁夏",value:0},
    {name:"新疆",value:0},
    {name:"广东",value:0},
    {name:"广西",value:0},
    {name:"海南",value:0},
    {name:"unknow",value:0},
    ];
var toolTipData = [ 
{name:"北京",value:[]},
{name:"天津",value:[]},
{name:"河北",value:[]},
{name:"山西",value:[]},
{name:"内蒙古",value:[]},
{name:"辽宁",value:[]},
{name:"吉林",value:[]},
{name:"黑龙江",value:[]},
{name:"上海",value:[]},
{name:"江苏",value:[]},
{name:"浙江",value:[]},
{name:"安徽",value:[]},
{name:"福建",value:[]},
{name:"江西",value:[]},
{name:"山东",value:[]},
{name:"河南",value:[]},
{name:"湖北",value:[]},
{name:"湖南",value:[]},
{name:"重庆",value:[]},
{name:"四川",value:[]},
{name:"贵州",value:[]},
{name:"云南",value:[]},
{name:"西藏",value:[]},
{name:"陕西",value:[]},
{name:"甘肃",value:[]},
{name:"青海",value:[]},
{name:"宁夏",value:[]},
{name:"新疆",value:[]},
{name:"广东",value:[]},
{name:"广西",value:[]},
{name:"海南",value:[]},
{name:"unknow",value:[]},
];

function findDataChart(){
	var cla=$("#oldClass").val();
	var type=$("#oldType").val();
	var da=$("#oldData").val();
	$.ajax({
		url:"findMapCount.action",
		type:"post",
		traditional: true,
		data:{
			clazz:cla,
			type:type,
			data:da
		},
		success:function(result){
			var mapData=result.mapData;
			var groupData=mapData.groupData;
			var compareData=mapData.compareData;
			var codeData=result.codeData;
			var sum=0;
			for(var i in data){
				data[i].value="0";
			}
			for(var i in toolTipData){
				toolTipData[i].value=[];
			}
			$("#provinceTab").empty();
			$("#provinceTab").append("<tr><td>省份</td><td>数量</td><td>百分比</td></tr>");
			
			for(var i in groupData){
				sum=parseInt(sum)+parseInt(groupData[i].value);
			}
			groupData.sort(function(a,b){
				return b.value-a.value;
			});
			for(var i in groupData){
				$("#provinceTab").append("<tr><td>"+groupData[i].name+"</td><td>"+groupData[i].value+"</td><td>"+toPercent(groupData[i].value,sum)+"</td></tr>");
			}
			for(var i in groupData){
				for(var j in data){
					if(groupData[i].name==data[j].name){
						data[j].value=groupData[i].value;
					}
				}
			}
			for(var i in compareData){
				for(var j in toolTipData){
					if(compareData[i].name==toolTipData[j].name){
						toolTipData[j].value=compareData[i].value;
					}
				}
			}
			myChart.hideLoading();
			myChart.setOption(option,true);
			
			var gwSubmitSum=codeData.gwSubmitSum;
			var gwArrivalSum=codeData.gwArrivalSum;
			var gwReportSuccSum = codeData.gwReportSuccSum;
			var successRate=codeData.successRate;
			var arrivalRate=codeData.arrivalRate;
			var speed=codeData.speed;
			option1.series[0].data[0].value=(gwSubmitSum/10000).toFixed(2);
			option1.series[1].data[0].value=(gwArrivalSum/10000).toFixed(2);
			option1.series[2].data[0].value=(gwReportSuccSum/10000).toFixed(2);
			option1.series[3].data[0].value=arrivalRate;
			option1.series[4].data[0].value=successRate;
			option1.series[5].data[0].value=speed;
			myChart1.setOption(option1,true);
		},
		dataType:"json"
	});
}


//地图的配置
var myChart = echarts.init(document.getElementById('china-map'));
var name_title = "全国平台提交量监控"
var subname = ''
var nameColor = " rgb(55, 75, 113)"
var name_fontFamily = '等线'
var subname_fontSize = 15
var name_fontSize = 18
var mapName = 'china'
var geoCoordMap = {};
var max = 480,
min = 9; // todo 
var maxSize4Pin = 100,
minSize4Pin = 20;

var convertData = function(data) {
var res = [];
for (var i = 0; i < data.length; i++) {
    var geoCoord = geoCoordMap[data[i].name];
    if (geoCoord) {
        res.push({
            name: data[i].name,
            value: geoCoord.concat(data[i].value),
        });
    }
}
return res;
};
option = {
title: {
    text: name_title,
    subtext: subname,
    x: 'center',
   textStyle: {
        color: nameColor,
        fontFamily: name_fontFamily,
        fontSize: name_fontSize
    },
    subtextStyle:{
        fontSize:subname_fontSize,
        fontFamily:name_fontFamily
    }
},
tooltip: {
    trigger: 'item',
    formatter:function(params) {
        if (typeof(params.value)[2] == "undefined") {
            var toolTiphtml = ''
            for(var i = 0;i<data.length;i++){
                if(params.name==data[i].name){
                    toolTiphtml += data[i].name+':'
                    if(toolTipData[i].value.length != 0){
                    	for(var j = 0;j<toolTipData[i].value.length;j++){
                    		toolTiphtml+="<br>"+toolTipData[i].value[j].name+':'+toolTipData[i].value[j].value;
                    	} 
                    }else{
                    	toolTiphtml += data[i].value;
                    }
                }
            }
            return toolTiphtml;
        } else {
            var toolTiphtml = ''
            for(var i = 0;i<data.length;i++){
                if(params.name==data[i].name){
                    toolTiphtml += data[i].name+':'
                    if(toolTipData[i].value.length != 0){
                    	for(var j = 0;j<toolTipData[i].value.length;j++){
                    		toolTiphtml+=toolTipData[i].value[j].name+':'+toolTipData[i].value[j].value+"<br>"
                    	} 
                    }else{
                    	toolTiphtml += data[i].value;
                    }
                }
            }
            return toolTiphtml;
        }
    }
},
visualMap: {
    show: true,
    min: 0,
    max: 1000000,
    left: 'left',
    top: 'center',
    text: ['高', '低'], // 文本，默认为数值文本
    calculable: true,
    seriesIndex: [1],
    inRange: {
    	color: ['#A5CC82','#00467F']
    } 
},

geo: {
    show: true,
    map: mapName,
    label: {
        normal: {
            show: false
        },
        emphasis: {
            show: false,
        }
    },
    roam: false,
    itemStyle: {
        normal: {
            areaColor: '#031525',
            borderColor: '#3B5077',
        },
        emphasis: {
            areaColor: '#2B91B7',
        }
    }
},
series: [ {
        name: '散点',
        type: 'scatter',
        data : convertData(data),
        coordinateSystem: 'geo',
        symbolSize: function(val) {
            return val[2] / 10;
        },
        label: {
            normal: {
                formatter: '{b}',
                position: 'right',
                show: true
            },
            emphasis: {
                show: true
            }
        },
        itemStyle: {
            normal: {
                color: '#05C3F9'
            }
        }
    }, 
    {
        type: 'map',
        map: mapName,
        geoIndex: 0,
        aspectScale: 0.75, //长宽比
        showLegendSymbol: false, // 存在legend时显示
        label: {
            normal: {
                show: true
            },
            emphasis: {
                show: false,
                textStyle: {
                    color: '#fff'
                }
            }
        },
        itemStyle: {
            normal: {
                areaColor: '#031525',
                borderColor: '#3B5077',
            },
            emphasis: {
                areaColor: '#2B91B7'
            }
        },
        animation: false,
        data : data
    }

]
};
myChart.setOption(option);
/*获取地图数据*/
myChart.showLoading();
var mapFeatures = echarts.getMap(mapName).geoJson.features;
myChart.hideLoading();
mapFeatures.forEach(function(v) {
    // 地区名称
    var name = v.properties.name;
    // 地区经纬度
    geoCoordMap[name] = v.properties.cp;

});
//
var myChart1 = echarts.init(document.getElementById('div1'));
option1 = {
	    tooltip : {
	        formatter: "{b} : {c}"
	    },
	    series : [
	        {
	            name:'提交总数',
	            type:'gauge',
	            radius: '70%',
	            splitNumber: 10,       // 分割段数，默认为5
	            center: ['8%', '50%'],
	            axisLine: {            // 坐标轴线
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: [[0.2, '#228b22'],[0.8, '#48b'],[1, '#ff4500']], 
	                    width: 10
	                }
	            },
	          	max:'20000',
	            axisTick: {            // 坐标轴小标记
	                splitNumber: 10,   // 每份split细分多少段
	                length :12,        // 属性length控制线长
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: 'auto'
	                }
	            },
	            axisLabel: { // 坐标轴文本标签，详见axis.axisLabel
	            	fontSize: 1,
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto'
	                }
	            },
	            splitLine: {           // 分隔线
	                show: true,        // 默认显示，属性show控制显示与否
	                length :15,         // 属性length控制线长
	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            pointer : {
	                width : 5
	            },
	            title : {
	                show : true,
	                offsetCenter: [0, '-40%'],       // x, y，单位px
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    fontWeight: 'bolder'
	                }
	            },
	            detail : {
	                formatter:'{value}',
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto',
	                    fontWeight: 'bolder'
	                }
	            },
	            data:[{value: 50, name: '提交总数(万)'}],
	            markPoint:{
	                symbol:'circle',                             
	                symbolSize:10,                            
	                data:[ //跟你的仪表盘的中心位置对应上，颜色可以和画板底色一样
	                	{x:'center',y:'center',itemStyle:{color:'#FFF'}}
	                ]
	           },
	        },
	        {
	            name:'到达总数',
	            type:'gauge',
	            radius: '70%',
	            splitNumber: 10,       // 分割段数，默认为5
	            center: ['25%', '50%'],
	            axisLine: {            // 坐标轴线
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: [[0.2, '#228b22'],[0.8, '#48b'],[1, '#ff4500']], 
	                    width: 10
	                }
	            },
	          	max:'20000',
	            axisTick: {            // 坐标轴小标记
	                splitNumber: 10,   // 每份split细分多少段
	                length :12,        // 属性length控制线长
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: 'auto'
	                }
	            },
	            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
	            	fontSize: 1,
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto'
	                }
	            },
	            splitLine: {           // 分隔线
	                show: true,        // 默认显示，属性show控制显示与否
	                length :15,         // 属性length控制线长
	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            pointer : {
	                width : 5
	            },
	            title : {
	                show : true,
	                offsetCenter: [0, '-40%'],       // x, y，单位px
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    fontWeight: 'bolder'
	                }
	            },
	            detail : {
	                formatter:'{value}',
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto',
	                    fontWeight: 'bolder'
	                }
	            },
	            data:[{value: 50, name: '到达总数(万)'}],
	            markPoint:{
	                symbol:'circle',                             
	                symbolSize:10,                            
	                data:[ //跟你的仪表盘的中心位置对应上，颜色可以和画板底色一样
	                	{x:'center',y:'center',itemStyle:{color:'#FFF'}}
	                ]
	           },
	        },
	        {
	            name:'成功总数',
	            type:'gauge',
	            radius: '70%',
	            splitNumber: 10,       // 分割段数，默认为5
	            center: ['42%', '50%'],
	            axisLine: {            // 坐标轴线
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: [[0.2, '#228b22'],[0.8, '#48b'],[1, '#ff4500']], 
	                    width: 10
	                }
	            },
	          	max:'20000',
	            axisTick: {            // 坐标轴小标记
	                splitNumber: 10,   // 每份split细分多少段
	                length :12,        // 属性length控制线长
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: 'auto'
	                }
	            },
	            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
	            	fontSize: 1,
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto'
	                }
	            },
	            splitLine: {           // 分隔线
	                show: true,        // 默认显示，属性show控制显示与否
	                length :15,         // 属性length控制线长
	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            pointer : {
	                width : 5
	            },
	            title : {
	                show : true,
	                offsetCenter: [0, '-40%'],       // x, y，单位px
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    fontWeight: 'bolder'
	                }
	            },
	            detail : {
	                formatter:'{value}',
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto',
	                    fontWeight: 'bolder'
	                }
	            },
	            data:[{value: 50, name: '成功总数(万)'}],
	            markPoint:{
	                symbol:'circle',                             
	                symbolSize:10,                            
	                data:[ //跟你的仪表盘的中心位置对应上，颜色可以和画板底色一样
	                	{x:'center',y:'center',itemStyle:{color:'#FFF'}}
	                ]
	           },
	        },
	        {
	            name:'到达率',
	            type:'gauge',
	            radius: '70%',
	            splitNumber: 10,       // 分割段数，默认为5
	            center: ['59%', '50%'],
	            axisLine: {            // 坐标轴线
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: [[0.8, '#ff4500'],[1, '#228b22']], 
	                    width: 10
	                }
	            },
	          	max:'100',
	            axisTick: {            // 坐标轴小标记
	                splitNumber: 10,   // 每份split细分多少段
	                length :12,        // 属性length控制线长
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: 'auto'
	                }
	            },
	            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
	            	fontSize: 1,
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto'
	                }
	            },
	            splitLine: {           // 分隔线
	                show: true,        // 默认显示，属性show控制显示与否
	                length :15,         // 属性length控制线长
	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            pointer : {
	                width : 5
	            },
	            title : {
	                show : true,
	                offsetCenter: [0, '-40%'],       // x, y，单位px
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    fontWeight: 'bolder'
	                }
	            },
	            detail : {
	                formatter:'{value}',
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto',
	                    fontWeight: 'bolder'
	                }
	            },
	            data:[{value: 50, name: '到达率(%)'}],
	            markPoint:{
	                symbol:'circle',                             
	                symbolSize:10,                            
	                data:[ //跟你的仪表盘的中心位置对应上，颜色可以和画板底色一样
	                	{x:'center',y:'center',itemStyle:{color:'#FFF'}}
	                ]
	           },
	        },
	        {
	            name:'成功率',
	            type:'gauge',
	            radius: '70%',
	            splitNumber: 10,       // 分割段数，默认为5
	            center: ['76%', '50%'],
	            axisLine: {            // 坐标轴线
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: [[0.8, '#ff4500'],[1, '#228b22']], 
	                    width: 10
	                }
	            },
	          	max:'100',
	            axisTick: {            // 坐标轴小标记
	                splitNumber: 10,   // 每份split细分多少段
	                length :12,        // 属性length控制线长
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: 'auto'
	                }
	            },
	            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
	            	fontSize: 1,
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto'
	                }
	            },
	            splitLine: {           // 分隔线
	                show: true,        // 默认显示，属性show控制显示与否
	                length :15,         // 属性length控制线长
	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            pointer : {
	                width : 5
	            },
	            title : {
	                show : true,
	                offsetCenter: [0, '-40%'],       // x, y，单位px
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    fontWeight: 'bolder'
	                }
	            },
	            detail : {
	                formatter:'{value}',
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto',
	                    fontWeight: 'bolder'
	                }
	            },
	            data:[{value: 50, name: '成功率(%)'}],
	            markPoint:{
	                symbol:'circle',                             
	                symbolSize:10,                            
	                data:[ //跟你的仪表盘的中心位置对应上，颜色可以和画板底色一样
	                	{x:'center',y:'center',itemStyle:{color:'#FFF'}}
	                ]
	           },
	        },
	        {
	            name:'速度',
	            type:'gauge',
	            radius: '70%',
	            splitNumber: 10,       // 分割段数，默认为5
	            center: ['93%', '50%'],
	            axisLine: {            // 坐标轴线
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: [[0.2, '#228b22'],[0.8, '#48b'],[1, '#ff4500']], 
	                    width: 10
	                }
	            },
	          	max:'20000',
	            axisTick: {            // 坐标轴小标记
	                splitNumber: 10,   // 每份split细分多少段
	                length :12,        // 属性length控制线长
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: 'auto'
	                }
	            },
	            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
	            	fontSize: 1,
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto'
	                }
	            },
	            splitLine: {           // 分隔线
	                show: true,        // 默认显示，属性show控制显示与否
	                length :15,         // 属性length控制线长
	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            pointer : {
	                width : 5
	            },
	            title : {
	                show : true,
	                offsetCenter: [0, '-40%'],       // x, y，单位px
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    fontWeight: 'bolder'
	                }
	            },
	            detail : {
	                formatter:'{value}',
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto',
	                    fontWeight: 'bolder'
	                }
	            },
	            data:[{value: 50, name: '速度(条/秒)'}],
	            markPoint:{
	                symbol:'circle',                             
	                symbolSize:10,                            
	                data:[ //跟你的仪表盘的中心位置对应上，颜色可以和画板底色一样
	                	{x:'center',y:'center',itemStyle:{color:'#FFF'}}
	                ]
	           },
	        }
	    ]
	};
	myChart1.setOption(option1);

function load(){
	$("#oldClass").val("平台");
	$("#oldType").val("group");
	loadData();
	findDataChart();
}
function loadData(){
	var cla=$("#class").val();
	if(cla == null || cla == '' || cla == undefined){
		alert("error");
	}else{
		$.ajax({
			url:"findLoadData.action",
			data:{
				cla:cla
			},
			type:"post",
			success:function(result){
				$("#data").empty();
				for(var i in result){
					$("#data").append("<option value='"+result[i].id+"'>"+result[i].id+":"+result[i].name+"</option>");
				}
				$("#data").selectpicker("refresh");
			},
			dataType:"json"
		})
	}
}
function toPercent(num, total) {
	if(total==0){
		return "00.00%";
	}

    return (Math.round(num / total * 10000) / 100.00 + "%");// 小数点后两位百分比
   
}

$(document).ajaxComplete(function(event, xhr, settings) {
	// 从http头信息取出 在filter定义的sessionstatus，判断是否是 timeout
	if (xhr.getResponseHeader("sessionstatus") == "timeout") {
		// 从http头信息取出登录的url ＝ loginPath
		if (xhr.getResponseHeader("loginPath")) {
			alert("会话过期，请重新登陆!");
			window.location.replace(xhr.getResponseHeader("loginPath"));
		} else {
			alert("请求超时请重新登陆 !");
		}
	}
});