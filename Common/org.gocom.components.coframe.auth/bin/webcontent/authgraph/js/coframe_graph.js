/**
 * Originally grabbed from the official RaphaelJS Documentation
 * http://raphaeljs.com/graffle.html
 * Adopted (arrows) and commented by Philipp Strathausen http://blog.ameisenbar.de
 * Licenced under the MIT licence.
 */

/**
 * Usage:
 * connect two shapes
 * parameters: 
 *      source shape [or connection for redrawing], 
 *      target shape,
 *      style with { fg : linecolor, bg : background color, directed: boolean }
 * returns:
 *      connection { draw = function() }
 */
Raphael.fn.connection = function (obj1, obj2, style) {
    var selfRef = this;
    /* create and return new connection */
    var edge = {/*
        from : obj1,
        to : obj2,
        style : style,*/
        draw : function() {
            /* get bounding boxes of target and source */
            var bb1 = obj1.getBBox();
            var bb2 = obj2.getBBox();
            var off1 = 0;
            var off2 = 0;
            /* coordinates for potential connection coordinates from/to the objects */
            var p = [
                {x: bb1.x + bb1.width / 2, y: bb1.y - off1},              /* NORTH 1 */
                {x: bb1.x + bb1.width / 2, y: bb1.y + bb1.height + off1}, /* SOUTH 1 */
                {x: bb1.x - off1, y: bb1.y + bb1.height / 2},             /* WEST  1 */
                {x: bb1.x + bb1.width + off1, y: bb1.y + bb1.height / 2}, /* EAST  1 */
                {x: bb2.x + bb2.width / 2, y: bb2.y - off2},              /* NORTH 2 */
                {x: bb2.x + bb2.width / 2, y: bb2.y + bb2.height + off2}, /* SOUTH 2 */
                {x: bb2.x - off2, y: bb2.y + bb2.height / 2},             /* WEST  2 */
                {x: bb2.x + bb2.width + off2, y: bb2.y + bb2.height / 2}  /* EAST  2 */
            ];
            
            /* distances between objects and according coordinates connection */
            var d = {}, dis = [];

            /*
             * find out the best connection coordinates by trying all possible ways
             */
            /* loop the first object's connection coordinates */
            for (var i = 0; i < 4; i++) {
                /* loop the seond object's connection coordinates */
                for (var j = 4; j < 8; j++) {
                    var dx = Math.abs(p[i].x - p[j].x),
                        dy = Math.abs(p[i].y - p[j].y);
                    if ((i == j - 4) || (((i != 3 && j != 6) || p[i].x < p[j].x) && ((i != 2 && j != 7) || p[i].x > p[j].x) && ((i != 0 && j != 5) || p[i].y > p[j].y) && ((i != 1 && j != 4) || p[i].y < p[j].y))) {
                        dis.push(dx + dy);
                        d[dis[dis.length - 1].toFixed(3)] = [i, j];
                    }
                }
            }
            var res = dis.length == 0 ? [0, 4] : d[Math.min.apply(Math, dis).toFixed(3)];
            /* bezier path */
            var x1 = p[res[0]].x,
                y1 = p[res[0]].y,
                x4 = p[res[1]].x,
                y4 = p[res[1]].y,
                dx = Math.max(Math.abs(x1 - x4) / 2, 10),
                dy = Math.max(Math.abs(y1 - y4) / 2, 10),
                x2 = [x1, x1, x1 - dx, x1 + dx][res[0]].toFixed(3),
                y2 = [y1 - dy, y1 + dy, y1, y1][res[0]].toFixed(3),
                x3 = [0, 0, 0, 0, x4, x4, x4 - dx, x4 + dx][res[1]].toFixed(3),
                y3 = [0, 0, 0, 0, y1 + dy, y1 - dy, y4, y4][res[1]].toFixed(3);
            /* assemble path and arrow */
            var path = ["M", x1.toFixed(3), y1.toFixed(3), "C", x2, y2, x3, y3, x4.toFixed(3), y4.toFixed(3)].join(",");
            /* arrow */
            if(style && style.directed) {
                /* magnitude, length of the last path vector */
                var mag = Math.sqrt((y4 - y3) * (y4 - y3) + (x4 - x3) * (x4 - x3));
                /* vector normalisation to specified length  */
                var norm = function(x,l){return (-x*(l||5)/mag);};
                /* calculate array coordinates (two lines orthogonal to the path vector) */
                var arr = [
                    {x:(norm(x4-x3)+norm(y4-y3)+x4).toFixed(3), y:(norm(y4-y3)+norm(x4-x3)+y4).toFixed(3)},
                    {x:(norm(x4-x3)-norm(y4-y3)+x4).toFixed(3), y:(norm(y4-y3)-norm(x4-x3)+y4).toFixed(3)}
                ];
                path = path + ",M"+arr[0].x+","+arr[0].y+",L"+x4+","+y4+",L"+arr[1].x+","+arr[1].y; 
            }
            /* function to be used for moving existent path(s), e.g. animate() or attr() */
            var move = "attr";
            /* applying path(s) */
            edge.fg && edge.fg[move]({path:path}) 
                || (edge.fg = selfRef.path(path).attr({stroke: style && style.stroke || "#000", fill: "none"}).toBack());
            edge.bg && edge.bg[move]({path:path})
                || style && style.fill && (edge.bg = style.fill.split && selfRef.path(path).attr({stroke: style.fill.split("|")[0], fill: "none", "stroke-width": style.fill.split("|")[1] || 3}).toBack());
            /* setting label */
            style && style.label 
                && (edge.label && edge.label.attr({x:(x1+x4)/2, y:(y1+y4)/2}) 
                    || (edge.label = selfRef.text((x1+x4)/2, (y1+y4)/2, style.label).attr({fill: "#000", "font-size": style["font-size"] || "12px"})));
            style && style.label && style["label-style"] && edge.label && edge.label.attr(style["label-style"]);
            style && style.callback && style.callback(edge);
        }
    }
    edge.draw();
    return edge;
};
//Raphael.prototype.set.prototype.dodo=function(){console.log("works");};
//获取文本宽度
function getTextWidth(text) {
   var ea = document.createElement("span");
   ea.innerHTML = text;
   document.body.appendChild(ea);
   var len = ea.offsetWidth;
   document.body.removeChild(ea);
   return len;
}

//节点的图标
var node_images = {};
//节点的颜色
var node_border_colors = {};
//关联的最大层级数
var MAX_LEVEL = 10;

var NODE_HEIGHT = 20;

var NODE_SPACE = 10;
/**
 * 画图的类，有节点和节点连线，采用分列布局的方式
 * 
 */
function CoframeGraph(elem,width,height){
	this.elem = elem;
	//引及开源组件的画布
	this.r = null;
	//节点映射,根据节点ID获取节点
	this.nodeMap = {};
	//节点的图形映射,根据节点ID获取节点的图形
	this.nodeShapeMap = {};
	//画布高
	this.height = height;
	//画布宽
	this.width = width;
	//最大高度
	this.maxHeight = 0;
	//列宽度,图形分为N列,每列宽度为总宽度除以列数
	this.columnWidth = 0;
	//每个图形的高度,包括间隔,计算位置时使用,如果画布较大则均匀分布
	//this.rowsHieght = NODE_HEIGHT + NODE_SPACE;
	//连线数组
	this.edges = [];
	//连线map，根据节点ID获取节点连出去的所有线
	this.connectionMap = {};
}

CoframeGraph.prototype = {
    //增加节点,增加时只存起来,经过计算后画节点
    addNode: function(node) {
    	if(!this.nodeMap[node.level]){
    		this.nodeMap[node.level] = [];
    	}
    	this.nodeMap[node.level].push(node);
       
    },
    //根据ID获取图形节点的方法
    getShape:function(nodeId){
    	return this.nodeShapeMap[nodeId];
    },
	//增加连线,增加时只存起来,画完节点再画连线
    addEdge: function(source, target, style) {
       this.edges.push({source:source,target:target,style:style});
    },
    //画节点的方法,根据节点设置的颜色/图标和文本画节点
    drawNode:function(node){
    	 /* the default node drawing */
		var color = node_border_colors[node.type];
		if(!color){
			color = Raphael.getColor();
		}
		var image_url = node_images[node.type];
		//算出文本的宽度
		var len = getTextWidth(node.name);
		var textLeft = node.x;
		if(image_url){
			textLeft = node.x + len/2 + 20;
			len = len + 30;
		}else{
			len = len + 10;
			textLeft = node.x + len/2;
		}
		//画带圆角的矩形
		var rect = this.r.rect(node.x, node.y, len, 24,5).attr({fill:color,"fill-opacity": .1,stroke: color, "stroke-width": 2});
		/* set DOM node ID */
		var shapeId = node.id;
		var graph = this;
		var shape = this.r.set().push(rect);
		var textShape = this.r.text(textLeft, node.y + 12, node.name);
		textShape.attr({"font-size":"13px","font-family":' "Microsoft YaHei", "宋体", "Segoe UI", sans-serif'});
		if(image_url){
			shape.push(this.r.image(image_url,node.x + 2,node.y + 4,16,16)).push(textShape);
		}else{
			shape.push(textShape);
		}
		//鼠标移上去的时候节点颜色变深,并把节点连出去的线变粗,变绿
		//鼠标移出时变回原来样子
		shape.hover(function(){ 
			rect.animate({"fill-opacity":1}, 100);
			if(graph){			
				var conns = graph.connectionMap[shapeId];
				if(conns){
					for(var i=0;i<conns.length;i++){
						var conn = conns[i];
						if(conn && conn.fg){
							conn.fg.attr({"stroke-width":3,stroke: "green"});
						}
					}
				}
			}
		}, function(){ 
			rect.animate({"fill-opacity":.1},100); 
			if(graph){
				var conns = graph.connectionMap[shapeId];
				if(conns){
					for(var i=0;i<conns.length;i++){
						var conn = conns[i];
						if(conn && conn.fg){
							conn.fg.attr({"stroke-width":1,stroke: "#000"});
						}
					}
				}	
			}
		});
		return shape;
    },
    //画连线的方法
    drawEdge:function(){
    	for(var i=0;i<this.edges.length;i++){
    		var edge = this.edges[i];
    		if(this.nodeShapeMap[edge.source] && this.nodeShapeMap[edge.target]){
	    		var connection = this.r.connection(this.nodeShapeMap[edge.source], this.nodeShapeMap[edge.target], edge.style);
	    		var connections = this.connectionMap[edge.source];
	    		if(!connections){
	    			connections = [];
	    			this.connectionMap[edge.source] = connections;
	    		}
	    		connections.push(connection);
    		}
    	}
    },
    //计算列宽度和每一列中节点的高度
    computeSize:function(){
    	var maxCount = 0;
    	var columnsCount = 0;
    	for(var i=1;i<MAX_LEVEL;i++){
    		if(this.nodeMap[i]){
    			columnsCount++;
    			maxCount = Math.max(maxCount,this.nodeMap[i].length);
    		}else{
    			//break;
    		}
    	}
    	if(columnsCount!=0 && maxCount>0){
	    	this.maxHeight = maxCount * (NODE_HEIGHT + NODE_SPACE);
	    	if(this.height>this.maxHeight){
	    		//this.rowsHieght = this.height/maxCount;
	    		this.maxHeight = this.height;
	    	}
			this.columnWidth = (this.width - 20)/columnsCount;
    	}
    	this.r = Raphael(this.elem,this.width,this.maxHeight);
    },
    //画列,节点是分列的,按照node的level区分
    drawColumn:function(columns,index){
    	var count = columns.length;
    	var columnsHeight = NODE_HEIGHT + NODE_SPACE;
    	if(count>0){
    		//计算出每列的控件高度
    		columnsHeight = Math.max(this.maxHeight/(count+1),columnsHeight);
    		//	var columnsTop = (this.maxHeight - (count * this.rowsHieght))/2;
    	}
    	if(count == 1 && index > 1){
    		columnsHeight = columnsHeight + (100 * (0.5 - Math.random()));
    	}
    	for(var i=0;i<columns.length;i++){
    		var node = columns[i];
    		//node.y = columnsTop + i * this.rowsHieght + 5;
    		node.y = columnsHeight * (i+1);
    		node.x = this.columnWidth * (index -1) + 20;
    		this.nodeShapeMap[node.id] = this.drawNode(node);
    	}
    },
    //画图方法，先计算，后画节点和连线
    draw:function(){
    	this.computeSize();
    	var columnIndex = 1;
    	for(var i=1;i<MAX_LEVEL;i++){
    		if(this.nodeMap[i]){
    			this.drawColumn(this.nodeMap[i],columnIndex);
    			columnIndex++;
    		}else{
    			//break;
    		}
    	}
    	this.drawEdge();
    	this.r.safari();
    }
 }