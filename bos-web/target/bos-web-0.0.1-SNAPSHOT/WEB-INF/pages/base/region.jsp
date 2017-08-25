<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域设置</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.ocupload-1.1.2.js">
</script>
<script type="text/javascript">
	function doAdd() {
		//打开修改取派员窗口
		$('#addRegionWindow').window("open");

	}

	function doView() {
	//alert("双击表格修改")
	//$("#editRegionWindow").window("open");
		//使用form表单对象的load方法回显数据
	//$("#editRegionForm").form("load", name:"province");
	 
	}
	function doSearch(){
		$('#searchWindow').window("open");
	}
	//导出按钮对应的处理函数
	function doExport(){
		//发送请求，请求Action，进行文件下载
		window.location.href = "regionAction_exportXls.action";
	}

	function doDelete() {
		var rows = $("#grid").datagrid("getSelections");
		if (rows.length == 0) {
			/* 没有选中弹出提示框 */
			$.messager.alert("提示信息", "请选择要删除的区域", "warning");
		} else {
			//选中了取派员，弹出提示框
			$.messager.confirm("删除确认", "您确认要删除选中的区域吗？", function(r) {
				if (r) {
					var array = new Array();
					//确认删除，发送请求
					//获得所有选中的取派员的id，循环遍历
					for ( var i = 0; i < rows.length; i++) {
						var region = rows[i]; //json对象
						var id = region.id;
						array.push(id);
					}
					var ids = array; //在这里可以不用追加.join(",")！！
					location.href = "regionAction_deleteBatch.action?ids="
							+ ids;
				}
			});
		}
	}

	//工具栏
	var toolbar = [{
		id : 'button-search',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, {
		id : 'button-edit',
		text : '双击表格修改',
		iconCls : 'icon-edit',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}, {
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo'
	},{
		id : 'button-export',
		text : '导出',
		iconCls : 'icon-undo',
		handler : doExport
	} ];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'province',
		title : '省',
		width : 120,
		align : 'center'
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center'
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center'
	}, {
		field : 'postcode',
		title : '邮编',
		width : 120,
		align : 'center'
	}, {
		field : 'shortcode',
		title : '简码',
		width : 120,
		align : 'center'
	}, {
		field : 'citycode',
		title : '城市编码',
		width : 200,
		align : 'center'
	} ] ];

	$(function() {
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({
			visibility : "visible"
		});

		// 收派标准数据表格
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList : [ 20, 40, 60 ],
			pagination : true,
			toolbar : toolbar,
			url : "regionAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});

		//页面加载完成后，调用OCUpload方法
		$("#button-import").upload({
			action : 'regionAction_importXls.action',
			name : 'regionFile'
		});

		// 添加、修改区域窗口
		$('#addRegionWindow').window({
			title : '添加修改区域',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		
		// 查询分区
		$('#searchWindow').window({
	        title: '查询分区',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });

		// 添加、修改区域窗口
		$('#editRegionWindow').window({
			title : '添加修改区域',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		
		//定义一个工具方法，用于将指定的form表单中所有的输入项转为json数据{key:value,key:value}
		$.fn.serializeJson=function(){  
            var serializeObj={};  
            var array=this.serializeArray();
            $(array).each(function(){  
                if(serializeObj[this.name]){  
                    if($.isArray(serializeObj[this.name])){  
                        serializeObj[this.name].push(this.value);  
                    }else{  
                        serializeObj[this.name]=[serializeObj[this.name],this.value];  
                    }  
                }else{  
                    serializeObj[this.name]=this.value;   
                }  
            });  
            return serializeObj;  
        }; 
		
		$("#btn").click(function(){
			//将指定的form表单中所有的输入项转为json数据{key:value,key:value}
			var p = $("#searchForm").serializeJson();
			console.info(p);
			//调用数据表格的load方法，重新发送一次ajax请求，并且提交参数
			$("#grid").datagrid("load",p);
			//关闭查询窗口
			$("#searchWindow").window("close");
		});

	});
	//数据表格绑定的双击行事件对应的函数
	function doDblClickRow(rowIndex, rowData) {
		//打开修改取派员窗口
		$("#editRegionWindow").window("open");
		//使用form表单对象的load方法回显数据
		$("#editRegionForm").form("load", rowData);
	}
</script>

</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
		<table id="grid"></table>
	</div>
	<div class="easyui-window" title="区域添加修改" id="addRegionWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true">保存</a>
			</div>
		</div>

		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addRegionForm" action="regionAction_add.action"
				method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>区域编号</td>
						<td><input type="text" name="id" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>省</td>
						<td><script type="text/javascript">
							$(function() {
								//为保存按钮绑定事件
								$("#save").click(
										function() {
											//表单校验，如果通过，提交表单
											var v = $("#addRegionForm").form(
													"validate");
											if (v) {
												//$("#addStaffForm").form("submit");
												$("#addRegionForm").submit();
											}
										});
							});
						</script> <input type="text" name="province" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" name="district"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" name="shortcode"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" name="citycode"
							class="easyui-validatebox" required="true" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 对区域进行修改 -->
	<div class="easyui-window" title="区域添加修改" id="editRegionWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true">保存</a>
			</div>
		</div>

		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editRegionForm" name="editRegionForm"
				action="regionAction_edit.action" method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>

					<tr>
						<td>省</td>
						<td><script type="text/javascript">
							$(function() {
								//为保存按钮绑定事件
								$("#edit").click(
										function() {
											//表单校验，如果通过，提交表单
											var v = $("#editRegionForm").form(
													"validate");
											if (v) {
												//$("#addStaffForm").form("submit");
												$("#editRegionForm").submit();
											}
										});
							});
						</script> <input type="text" name="province" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" name="district"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" name="shortcode"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" name="citycode"
							class="easyui-validatebox" required="true" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 查询分区 -->
	<div class="easyui-window" title="查询分区窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="searchForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" name="region.province"/></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="region.city"/></td>
					</tr>
					<tr>
						<td>区（县）</td>
						<td><input type="text" name="region.district"/></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" name="addresskey"/></td>
					</tr>
					<tr>
						<td>编码</td>
						<td><input type="text" name="addresskey"/></td>
					</tr>
					
					<tr>
						<td colspan="2"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>