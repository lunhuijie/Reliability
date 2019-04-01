var cars;
var replay_speed=5;
var carsPos;
var SCPos;
var deals=[];
/*[{
	CPHM:"苏A0001",
    YYSJ:28,
    YYLC:9,
    YYJE:20,
    startLngLat:[116.379028, 39.865042],
    endLngLat:[116.427281, 39.903719]
},{
	CPHM:"苏A0002",//绕路
    YYSJ:30,
    YYLC:12,
    YYJE:25,
    startLngLat:[116.379029, 39.865041],
    endLngLat:[116.427281, 39.903719]
},{
	CPHM:"苏A0003",
    YYSJ:35,
    YYLC:8,
    YYJE:20,
    startLngLat:[116.379027, 39.865046],
    endLngLat:[116.427281, 39.903719]
},{
	CPHM:"苏A0004",//绕路
    YYSJ:28,
    YYLC:11,
    YYJE:25,
    startLngLat:[116.379028, 39.865043],
    endLngLat:[116.427281, 39.903719]
},{
	CPHM:"苏A0005",//绕路
    YYSJ:29,
    YYLC:15,
    YYJE:26,
    startLngLat:[116.379029, 39.865042],
    endLngLat:[116.427281, 39.903719]
}];*/
var ifHeat=0;
var heatmapData=[];
var illegalCars=[];//存储违规车辆的数组
var production;//生产信息（drivesNum,carsNum）
var revenue;//营收信息
function disAll(){//根据查询时间 获取车辆分布位置+热点区域显示
    var sj_in =  $('#display_time').val();
    searchCars(sj_in);
    var cluster,markers=[];
	var map = new AMap.Map("container", {
        resizeEnable: true,
        center: [118.778611, 32.043889],
        zoom: 13
    });    
    map.clearMap();  // 清除地图覆盖物
    var iconUrl = './Marker_32px.png';
//     heatmapData=[];//清空内容
    for (var i = 0; i < carsPos.length; i += 1) {
        markers.push(new AMap.Marker({
            position: [carsPos[i].center[0],carsPos[i].center[1]],
            title:carsPos[i].CPH,
            content: '<div style="background-color: hsla(180, 100%, 50%, 0.7); height: 24px; width: 24px; border: 1px solid hsl(180, 100%, 40%); border-radius: 12px; box-shadow: hsl(180, 100%, 50%) 0px 0px 1px;"></div>',
            offset: new AMap.Pixel(-15, -15)
        }));
//        heatmapData.push({"lng":carsPos[i].center[0],"lat":carsPos[i].center[1]});//两个属性一定是lng和lat
    }
    var count = markers.length;
    if (cluster) {
         cluster.setMap(null);
    }
    cluster = new AMap.MarkerClusterer(map, markers, {gridSize: 80});
 /*设置热点的数据*/
 /*   ifHeat=1;
    map.plugin(["AMap.Heatmap"], function() {
        //初始化heatmap对象
        heatmap = new AMap.Heatmap(map, {
            radius: 15, //给定半径
            opacity: [0, 0.8]
        });
        heatmap.setDataSet({
            data: heatmapData,
            max: 0
        });
    });*/
}

function disAllNow()//实时显示所有车辆位置+热点区域显示
{
    var sj_in = getFormatTime();
    searchNowCars(sj_in);
    var cluster,markers=[];
	var map = new AMap.Map("container", {
        resizeEnable: true,
        center: [118.778611, 32.043889],
        zoom: 13
    });    
    map.clearMap();  // 清除地图覆盖物
    var iconUrl = './Marker_32px.png';
    for (var i = 0; i < carsPos.length; i += 1) {
        markers.push(new AMap.Marker({
            position: [carsPos[i].center[0],carsPos[i].center[1]],
            title:carsPos[i].CPH,
            content: '<div style="background-color: hsla(180, 100%, 50%, 0.7); height: 24px; width: 24px; border: 1px solid hsl(180, 100%, 40%); border-radius: 12px; box-shadow: hsl(180, 100%, 50%) 0px 0px 1px;"></div>',
            offset: new AMap.Pixel(-15, -15)
        }));
    }
    var count = markers.length;
    if (cluster) {
         cluster.setMap(null);
    }
    cluster = new AMap.MarkerClusterer(map, markers, {gridSize: 80});
}

function haha() {//获得当天车辆的轨迹

	var name= $('#cph').val();
	if(ifHeat){
		CloseHeatmap();
		ifHeat=0;
	}
//  window.location.reload()   
    searchPath(name);
	//创建地图
   var map = new AMap.Map('container',{
                zoom: 20,
            center: [118.778611, 32.043889]
             });
  //  var trace = cars;
    AMapUI.load(['ui/misc/PathSimplifier', 'lib/$'], function(PathSimplifier, $) {

        if (!PathSimplifier.supportCanvas) {
            alert('当前环境不支持 Canvas！');
            return;
        }

        var pathSimplifierIns = new PathSimplifier({
            zIndex: 100,
            //autoSetFitView:false,
            map: map, //所属的地图实例

            getPath: function(pathData, pathIndex) {

                return pathData.path;
            },
            getHoverTitle: function(pathData, pathIndex, pointIndex) {

                if (pointIndex >= 0) {
                    //point 
                    return pathData.name + '，点：' + pointIndex + '/' + pathData.path.length;
                }

                return pathData.name + '，点数量' + pathData.path.length;
            },
            renderOptions: {

//                renderAllPointsIfNumberBelow: 100 //绘制路线节点，如不需要可设置为-1
                pathLineStyle: {
                    dirArrowStyle: true
                },
                getPathStyle: function(pathItem, zoom) {

                    var color = 'blue',
                        lineWidth = 6;

                    return {
                        pathLineStyle: {
                            strokeStyle: color,
                            lineWidth: lineWidth
                        },
                        pathLineSelectedStyle: {
                            lineWidth: lineWidth + 2
                        },
                        pathNavigatorStyle: {
                            fillStyle: color
                        }
                    };
                }
            }
        });

        window.pathSimplifierIns = pathSimplifierIns;

           
        //设置数据
        pathSimplifierIns.setData([{
            name:cars.name,
            path:cars.path
        }]);
 
        function onload() {
            pathSimplifierIns.renderLater();
        }

        function onerror(e) {
            alert('图片加载失败！');
        }

        //对第一条线路（即索引 0）创建一个巡航器
        var navg1 = pathSimplifierIns.createPathNavigator(0, {
            loop: true, //循环播放
            speed: 1000*replay_speed, //巡航速度，单位千米/小时
            pathNavigatorStyle: {
                    width: 15,
                    height: 30,
                    //使用图片
                    content: PathSimplifier.Render.Canvas.getImageContent('https://webapi.amap.com/ui/1.0/ui/misc/PathSimplifier/examples/imgs/car.png', onload, onerror),
                    strokeStyle: null,
                    fillStyle: null,
                    //经过路径的样式
                    pathLinePassedStyle: {
                        lineWidth: 6,
                        strokeStyle: 'black',
                        dirArrowStyle: {
                            stepSpace: 15,
                            strokeStyle: 'red'
                        }
                    }
                }
        });

        navg1.start();
    });
}

function getYYYYMMDD()//获取当前时间
{
    var d = new Date();
    var curr_date = d.getDate();
    var curr_month = d.getMonth() + 1; 
    var curr_year = d.getFullYear();
    String(curr_month).length < 2 ? (curr_month = "0" + curr_month): curr_month;
    String(curr_date).length < 2 ? (curr_date = "0" + curr_date): curr_date;
    var yyyyMMdd = curr_year + "" + curr_month +""+ curr_date;
    return yyyyMMdd;
}

function getFormatTime() //yyyyMMdd HH:MM:SS
{
    var date = new Date();
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var minute = date.getMinutes();
    var second = date.getSeconds();
    if(minute>1) minute-=1;
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (minute >= 0 && minute <= 9) {
    	minute = "0" + minute;
    }
    if (second >= 0 && second <= 9) {
    	second = "0" + second;
    }
    var currentdate = date.getFullYear() + "" + month + "" + strDate
            + " " + date.getHours() + seperator2 + minute
            + seperator2 + second;
    return currentdate;
}

function getTimeDuring(timing)//获得十分钟的起始、结束时间
{
	var data=new Object();
	var date=new Date();//你已知的时间
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var minute = date.getMinutes();
    var second = date.getSeconds();
    if(minute>1) minute-=1;
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (minute >= 0 && minute <= 9) {
    	minute = "0" + minute;
    }
    if (second >= 0 && second <= 9) {
    	second = "0" + second;
    }
    data.sj_to = date.getFullYear() + "" + month + "" + strDate
            + " " + date.getHours() + seperator2 + minute
            + seperator2 + second;
	
    date.setTime(date.setMinutes(date.getMinutes()-timing));//设置新时间比旧时间少timing分钟
	month = date.getMonth() + 1;
	strDate = date.getDate();
	minute = date.getMinutes();
	second = date.getSeconds();
	if(minute>1) minute-=1;
	if (month >= 1 && month <= 9) {
	    month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
	    strDate = "0" + strDate;
	}
	if (minute >= 0 && minute <= 9) {
	    minute = "0" + minute;
	}
	if (second >= 0 && second <= 9) {
	    second = "0" + second;
	}
    data.sj_from = date.getFullYear() + "" + month + "" + strDate
	            + " " + date.getHours() + seperator2 + minute
	            + seperator2 + second;
    
    return data;
}

function OpenHeatmap(){
    if(ifHeat==1){
        if (!isSupportCanvas()) {
        alert('热力图仅对支持canvas的浏览器适用,您所使用的浏览器不能使用热力图功能,请换个浏览器试试~')
        }
        else heatmap.show();
    }
}

function CloseHeatmap(){
    if(ifHeat==1){
        heatmap.hide();
    }
}

function isSupportCanvas() {
    var elem = document.createElement('canvas');
     return !!(elem.getContext && elem.getContext('2d'));
}

function ifDetour()//获取十分钟内可能存在绕路行为的车辆车牌号
{
	searchDeal();//返回内容存储在deals中
	illegalCars=[];//存储存在绕路行为的车辆车牌号
//	console.log(deals);
	for(var i=0;i<deals.length;i+=1)
	{
		//对每个合法车辆进行判断
        computePath(deals[i]);
	}
	console.log("违规车辆");
	console.log(illegalCars);
    
}

function computePath(deal)
{
	   var TIME,DIS;
	   var flag=1;
	   var driving = new AMap.Driving({
	        // 驾车路线规划策略，AMap.DrivingPolicy.LEAST_TIME是最快捷模式  LEAST_DISTANCE最短距离模式
	        policy: AMap.DrivingPolicy.LEAST_TIME
	   })
	   AMap.plugin('AMap.Driving', function() {
		        driving.search(deal.startLngLat, deal.endLngLat, function (status, result) {
		        // 未出错时，result即是对应的路线规划方案
		            if(status=="complete"){
		                  TIME=result.routes[0].time;//秒
		                  DIS=result.routes[0].distance;//米
		                  //console.log(DIS);console.log(TIME);
		                  //console.log(deal.YYLC);console.log(deal.YYSJ);
		                  if((deal.YYLC*1000>DIS*1.2)&&(deal.YYSJ*60>TIME)){ 
		                	  flag=0;
		                  }
		        		 if(flag==0) 
		        		 {
		        			 illegalCars.push(deal.CPHM);
                             console.log("违规：");
		        			 console.log(illegalCars);
                             showRl(illegalCars);
		        		 }
		            }
		        })
		 })
}

function demandForecast()//即时性的需求预测
{
	heatmapData=[];//清空内容
	searchSCPos();//返回20分钟内的所有上车地点
	var map = new AMap.Map("container", {
        resizeEnable: true,
        center: [118.778611, 32.043889],
        zoom: 13
    });    
    map.clearMap();  // 清除地图覆盖物
	console.log(SCPos);
/*	for(var i=0;i<SCPos.length;i+=1)
	{
		heatmapData.push({"lng":SCPos[i].LngLat[0],"lat":SCPos[i].LngLat[1]});//两个属性一定是lng和lat
	}*/
	ifHeat=1;
	map.plugin(["AMap.Heatmap"], function() {
	    //初始化heatmap对象
	    heatmap = new AMap.Heatmap(map, {
	        radius: 15, //给定半径
	        opacity: [0, 0.8]
	    });
	    heatmap.setDataSet({
	        data: SCPos,
	        max: 0
	    });
	});
}

















