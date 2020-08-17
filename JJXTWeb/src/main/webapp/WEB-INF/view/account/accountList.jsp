<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <style type="text/css">
        .td {
            width: 100px;
        }
    </style>
</head>
<jsp:include page="/head.html"></jsp:include>
<body>
<div class="tableBody">
    <div class="panel panel-default" style="margin-bottom: 0px;">
        <div class="panel-body form-group" style="margin-bottom: 0px;">
            <div class="col-sm-2">
                <select name="companyId" id="search_companyId" class="selectpicker show-tick form-control"
                        data-width="98%" data-live-search="true">
                    <option value="">全部公司</option>
                    <c:forEach var="s" items="${complains }">
                        <option value="${s.id}">${s.id}:${s.companyKey }</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-2">
                <select name="appId" id="search_appId" class="selectpicker show-tick form-control" data-width="98%"
                        data-live-search="true">
                    <option value="">全部账户</option>
                    <c:forEach var="s" items="${apps }">
                        <option value="${s.id}">${s.id}:${s.appName }</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-2">
                <select class="selectpicker show-tick form-control" id="search_appStatus" name="appStatus">
                    <option value="">状态</option>
                    <option value="normal">正常</option>
                    <option value="pause">暂停</option>
                    <option value="deleted">删除</option>
                </select>
            </div>
            <div class="col-sm-2">
                <select name="product" id="searchProductId"
                        class="selectpicker show-tick form-control" placeholder="全部产品" data-width="98%"
                        data-live-search="true">
                    <option value="">全部产品</option>
                    <c:forEach var="s" items="${products }">
                        <option value="${s.id}">${s.id}:${s.productName }</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-2">
                <select name="channelId" id="searchChannelId"
                        class="selectpicker show-tick form-control" placeholder="全部通道" data-width="98%"
                        data-live-search="true">
                    <option value="">全部通道</option>
                    <c:forEach var="channel" items="${channels}">
                        <option value="${channel.id}">${channel.id}:${channel.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-2">
              			<input type="text" class="form-control" name="appExtSrc"
					id="searchAppExtSrc" placeholder="账户扩展(精准)" />
			</div>
			<div class="col-sm-2">
				<select name="sales" id="searchSales"
					class="selectpicker show-tick form-control" placeholder="全部销售"
					data-width="98%" data-first-option="false" required
					data-live-search="true"> 
					<option value="">全部销售</option>
					<c:forEach var="s" items="${sales }">
						<option value="${s.sales}">${s.sales}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-sm-2">
				<select name="saleAfter" id="searchSaleAfter"
					class="selectpicker show-tick form-control" placeholder="全部客服"
					data-width="98%" data-first-option="false" required
					data-live-search="true"> 
					<option value="">全部客服</option>
					<c:forEach var="s" items="${saleAfter }">
						<option value="${s.saleAfter}">${s.saleAfter}</option>
					</c:forEach>
				</select>
			</div>
            <div class="col-sm-2 pull-right">
                <button class="btn btn-primary" id="search_btn">搜索</button>
                <button class="btn btn-default" id="search_back">重置</button>
            </div>
        </div>

    </div>
    <table id="mytab" class="table table-hover"></table>
    <div id="toolbar" class="btn-group pull-right" style="margin-right: 20px">
        <button id="btn_add" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增帐户
        </button>
    </div>
</div>
<div class="addBody" style="width:100%; display: none;position: absolute;top:10px">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>新增帐户</h5>
            </div>
            <div class="ibox-content">
                <form id="addForm" method="post" class="form-horizontal">
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">帐户名称</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="appName" id="appName" maxlength="6"/>
                            </div>
                        </div>
                        <label class="col-sm-2 control-label">帐户状态</label>
                        <div class="col-sm-2">
                            <select id="appStatus" name="appStatus" class="form-control">
                                <option value="normal">正常</option>
                                <option value="pause">暂停</option>
                                <option value="deleted">删除</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">所属公司</label>
                            <div class="col-sm-4">
                                <select name="fakeCompanyId" id="companyId" class="selectpicker show-tick form-control"
                                        data-width="98%" data-live-search="true">
                                    <option value="">...请选择公司...</option>
                                    <c:forEach var="s" items="${complains }">
                                        <option value="${s.id}">${s.id}:${s.companyName }</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" name="companyId" id="oldCompanyId">
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">父帐户</label>
                            <div class="col-sm-2">
                                <select id="appParentId" name="appParentId" class="form-control">
                                    <option value="0">父帐户</option>
                                    <c:forEach var="s" items="${parents }">
                                        <option value="${s.id}">${s.appName }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">开始时间</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="serviceTimeBegin" id="serviceTimeBegin"
                                       value="0000"/>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">结束时间</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="serviceTimeEnd" id="serviceTimeEnd"
                                       value="2400"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">状态报告类型</label>
                            <div class="col-sm-4">
                                <select id="rptSyncModel" name="rptSyncModel" class="form-control">
                                    <option value="push">推送</option>
                                    <option value="pull">拉取</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">状态报告地址</label>
                            <div class="col-sm-4">
                                <!-- <input type="text" class="form-control" name="rptSyncAddress" id="rptSyncAddress" maxlength="40"/> -->
                                <input type="text" class="form-control" name="rptSyncAddress" id="rptSyncAddress">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">上行类型</label>
                            <div class="col-sm-4">
                                <select id="moSyncModel" name="moSyncModel" class="form-control">
                                    <option value="push">推送</option>
                                    <option value="pull">拉取</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">上行地址</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="moSyncAddress" id="moSyncAddress"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">分钟限</label>
                            <div class="col-sm-4">
                                <select id="isMinLimit" name="isMinLimit" class="form-control">
                                    <option value="no">不需要分钟限</option>
                                    <option value="yes">需要分钟限</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">分钟限条数</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="minLimitCount" id="minLimitCount"
                                       value="30" maxlength="4"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">日限</label>
                            <div class="col-sm-4">
                                <select id="isDayLimit" name="isDayLimit" class="form-control">
                                    <option value="no">不需要日限</option>
                                    <option value="yes">需要日限</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">日限条数</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="dayLimitCount" id="dayLimitCount"
                                       value="10" maxlength="4"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">月限</label>
                            <div class="col-sm-4">
                                <select id="isMonthLimit" name="isMonthLimit" class="form-control">
                                    <option value="no">不需要月限</option>
                                    <option value="yes">需要月限</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">月限条数</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="monthLimitCount" id="monthLimitCount"
                                       value="10" maxlength="4"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">内容限</label>
                            <div class="col-sm-4">
                                <select id="isDayContentLimit" name="isDayContentLimit" class="form-control">
                                    <option value="no">不需要内容限</option>
                                    <option value="yes">需要内容限</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">内容限条数</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="dayLimitContentCount"
                                       id="dayLimitContentCount" value="5" maxlength="4"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">策略模板</label>
                            <div class="col-sm-4">
                                <select id="isTemplate" name="isTemplate" class="form-control">
                                    <option value="no">不过策略模板</option>
                                    <option value="yes">过策略模板</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">敏感词</label>
                            <div class="col-sm-4">
                                <select id="skipMustWords" name="skipMustWords" class="form-control">
                                    <option value="no">过敏感词</option>
                                    <option value="yes">不过敏感词</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">审核词</label>
                        <div class="col-sm-4">
                            <select id="checkWordsType" name="checkWordsType" class="form-control">
                                <option value="no">不过审核词</option>
                                <option value="yes">过审核词</option>
                            </select>
                        </div>
                        <label class="col-sm-2 control-label">是否使用模板跳转通道</label>
                        <div class="col-sm-4">
                            <select id="isModel" name="isModel" class="form-control">
                                <option value="no">不使用模板</option>
                                <option value="yes">使用模板</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">是否内容审核</label>
                            <div class="col-sm-4">
                                <select id="isContAudit" name="isContAudit" class="form-control">
                                    <option value="no">不使用内容审核</option>
                                    <option value="yes">使用内容审核</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">超过条数进审核</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="contAuditCount" id="contAuditCount"
                                       value="100" maxlength="5"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">日限进审核</label>
                            <div class="col-sm-4">
                                <select id="isDayLimitCheck" name="isDayLimitCheck" class="form-control">
                                    <option value="no">不使用日限进审核</option>
                                    <option value="yes">使用日限进审核</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">非默认签名提交拦截</label>
                            <div class="col-sm-4">
                                <select id="isDefaultSignSubmit" name="isDefaultSignSubmit" class="form-control">
                                    <option value="no">不使用非默认签名提交拦截</option>
                                    <option value="yes">使用非默认签名提交拦截</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">计费方式</label>
                        <div class="col-sm-4">
                            <select id="chargeBy" name="chargeBy" class="form-control">
                                <option value="delivrd">成功计费</option>
                                <option value="delivrdunknown">成功+未知</option>
                                <option value="submit">提交计费</option>
                            </select>
                        </div>
                        <label class="col-sm-2 control-label">结算类型</label>
                        <div class="col-sm-4">
                            <select id="payment" name="payment" class="form-control">
                                <option value="advance">预付费</option>
                                <option value="arrears">后付费</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">初始余额</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="limitCount" id="limitCount" value="100"
                                       maxlength="11"/>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">产品</label>
                            <div class="col-sm-4">
                                <select id="productId" name="productId" class="selectpicker show-tick form-control"
                                        data-width="98%" data-live-search="true">
                                    <option value="">...请选择产品...</option>
                                    <c:forEach var="s" items="${products }">
                                        <option value="${s.id}">${s.id}:${s.productName }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">帐号扩展</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="appExtSrc" id="appExtSrc" maxlength="10"/>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">默认签名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="defaultSign" id="defaultSign"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">是否固定提交码号</label>
                            <div class="col-sm-4">
                                <select id="isExt" name="isExt" class="form-control">
                                	<option value="1">自定义提交码号</option>
                                    <option value="0">固定提交码号</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">提交码号</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="sourceSegment" id="sourceSegment"
                                       maxlength="20"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">是否固定上行码号</label>
                            <div class="col-sm-4">
                                <select id="moFlag" name="moFlag" class="form-control">
                                    <option value="1">固定上行码号</option>
                                    <option value="0" selected="selected">不固定上行码号</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">上行推送码号</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="moSourceSegment" id="moSourceSegment"
                                       maxlength="20"/>
                            </div>
                        </div>
                        <!-- <div class="rowGroup">
                            <label class="col-sm-2 control-label">实号库</label>
                            <div class="col-sm-4">
                                <select id="isShuntPhone" name="isShuntPhone" class="form-control" >
                                    <option value="yes">校验实号库</option>
                                    <option value="no" selected="selected">不校验实号库</option>
                                </select>
                            </div>
                        </div> -->
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">测试模式</label>
                            <div class="col-sm-4">
                                <select id="testModel" name="testModel" class="form-control">
                                    <option value="no">否</option>
                                    <option value="yes">是</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">cmpp最大连接数</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="maxConnection" id="maxConnection"
                                       value="5" maxlength="4"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">优先级</label>
                            <div class="col-sm-4">
                                <select id="priority" name="priority" class="form-control">
                                    <option value="normal">正常</option>
                                    <option value="hight">高</option>
                                    <option value="low">低</option>
                                    <option value="urgent">紧急</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">实号库</label>
                            <div class="col-sm-4">
                                <select id="isShuntPhone" name="isShuntPhone" class="form-control">
                                    <option value="no" selected="selected">不校验实号库</option>
                                    <option value="yes">校验实号库</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">验证IP</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="authIp" id="authIp"/>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">队列分配</label>
                            <div class="col-sm-4">
                               <select id="redisFlag" name="redisFlag" class="form-control">
                                    <option value="1" selected="selected">1</option>
                                    <option value="2">2</option>
                                </select>
                                <font size="2" color="#ccc">修改队列分配会影响到状态报告推送</font>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">价格(分)</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="price" id="price" value="0.0"
                                       maxlength="4"/>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">移动价格(分)</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="priceCmcc" id="priceCmcc" value="0.0"
                                       maxlength="4"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">联通价格(分)</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="priceUnicom" id="priceUnicom" value="0.0"
                                       maxlength="4"/>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">电信价格(分)</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="priceTelecom" id="priceTelecom"
                                       value="0.0" maxlength="4"/>
                            </div>
                        </div>
                    </div>

                    <!-- <div class="form-group">
                            <div class="rowGroup">
                                    <label class="col-sm-2 control-label">黑名单级别</label>
                                    <div class="col-sm-8">
                                        <label style="width: 50px;margin: auto"><input type="checkbox" class="form-control" name="blackLevel" value="1" style="zoom:60%;"></label><font style="zoom:130%;">投诉</font>
                                        <label style="width: 50px;margin: auto"><input type="checkbox" class="form-control" name="blackLevel" value="2" style="zoom:60%;"></label><font style="zoom:130%;">回复</font>
                                        <label style="width: 50px;margin: auto"><input type="checkbox" class="form-control" id="edit_putong_btn" name="blackLevel" value="3" style="zoom:60%;"></label><font style="zoom:130%;">普通</font>
                                        <label style="width: 50px;margin: auto"><input type="checkbox" class="form-control" id="edit_youhua_btn" name="blackLevel" value="4" style="zoom:60%;"></label><font style="zoom:130%;">优化</font>
                                        <label style="width: 50px;margin: auto"><input type="checkbox" class="form-control" id="edit_jinrong_btn" name="blackLevel" value="5" style="zoom:60%;"></label><font style="zoom:130%;">金融</font>
                                    </div>
                                </div>
                            </div> -->
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">黑名单级别</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="blackLevel" value="1,2"/>
                                <span style="font-size: 13px; color: #ff5907;">
								    		1:投诉黑名单; 2:回复黑名单; 3:普通黑名单; 4:优化黑名单; 5:金融黑名单; 其他黑名单<br>
								    		  默认投诉黑,回复黑;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;普通黑与优化黑互斥</span>
                            </div>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div class="col-sm-2 col-sm-offset-2">
                            <button type="button" class="btn btn-primary btn-block" id="add_saveBtn">保存</button>
                        </div>
                        <div class="col-sm-2 col-sm-offset-1">
                            <button type="button" class="btn btn-default btn-block" id="add_backBtn">返回</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="productDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width:500px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    产品详情
                </h4>
            </div>
            <div class="modal-body" id="menuBody">
                <table class="table table-bordered table-hover">
                    <tr>
                        <td class="td">产品名称</td>
                        <td><span id="detailname"></span></td>
                    </tr>
                    <tr>
                        <td class="td">产品属性</td>
                        <td><span id="detailclass"></span></td>
                    </tr>
                    <tr>
                        <td class="td">产品状态</td>
                        <td><span id="detailstatus"></span></td>
                    </tr>
                    <tr>
                        <td class="td">签名要求</td>
                        <td><span id="detailsign"></span></td>
                    </tr>
                    <tr>
                        <td class="td">移动通道</td>
                        <td><span id="detailcmcc"></span></td>
                    </tr>
                    <tr>
                        <td class="td">联通通道</td>
                        <td><span id="detailunicom"></span></td>
                    </tr>
                    <tr>
                        <td class="td">电信通道</td>
                        <td><span id="detailtelecom"></span></td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="Details" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:950px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    详情
                </h4>
            </div>
            <div class="modal-body" id="menuBody">
                <table class="table table-bordered table-hover">
                    <tr>
                        <td class="td">账户</td>
                        <td><span id="detailApp"></span></td>
                        <td class="td">账户密码</td>
                        <td><span id="detailAppPassword"></span></td>
                        <td class="td">状态</td>
                        <td><span id="detailAppStatus"></span></td>
                    </tr>
                    <tr>
                        <td class="td">公司</td>
                        <td><span id="detailCompanyId"></span></td>
                        <td class="td">产品</td>
                        <td><span id="detailProductId"></span></td>
                        <td class="td">默认签名</td>
                        <td><span id="detailDefaultSign"></span></td>
                    </tr>
                    <tr>
                        <td class="td">状态报告类型</td>
                        <td><span id="detailRptSyncModel"></span></td>
                        <td class="td">状态报告地址</td>
                        <td><span id="detailRptSyncAddress"></span></td>
                        <td class="td">优先级</td>
                        <td><span id="detailPriority"></span></td>
                    </tr>
                    <tr>
                        <td class="td">上行类型</td>
                        <td><span id="detailMoSyncModel"></span></td>
                        <td class="td">上行地址</td>
                        <td><span id="detailMoSyncAddress"></span></td>
                        <td class="td">跳过敏感词</td>
                        <td><span id="detailSkipMustWords"></span></td>
                    </tr>
                    <tr>
                        <td class="td">是否日限</td>
                        <td><span id="detailIsDayLimit"></span></td>
                        <td class="td">日限条数</td>
                        <td><span id="detailDayLimitCount"></span></td>
                        <td class="td">是否过模版</td>
                        <td><span id="detailIsModel"></span></td>
                    </tr>
                    <tr>
                        <td class="td">分钟限</td>
                        <td><span id="detailIsMinLimit"></span></td>
                        <td class="td">分钟限(秒数)</td>
                        <td><span id="detailMinLimitCount"></span></td>
                        <td class="td">是否必审词</td>
                        <td><span id="detailCheckWordsType"></span></td>
                    </tr>
                    <tr>
                        <td class="td">内容限</td>
                        <td><span id="detailIsDayContentLimit"></span></td>
                        <td class="td">内容限数</td>
                        <td><span id="detailDayLimitContentCount"></span></td>
                        <td class="td">是否实号库</td>
                        <td><span id="detailIsShuntPhone"></span></td>
                    </tr>
                    <tr>
                        <td class="td">相同内容审核</td>
                        <td><span id="detailIsContAudit"></span></td>
                        <td class="td">内容审核条数</td>
                        <td><span id="detailContAuditCount"></span></td>
                        <td class="td">策略模板</td>
                        <td><span id="detailIsTemplate"></span></td>
                    </tr>
                    <tr>
                        <td class="td">主账户</td>
                        <td><span id="detailAppParentId"></span></td>
                        <td class="td">子账户</td>
                        <td><span id="detailSubAppId"></span></td>
                        <td class="td">价格</td>
                        <td><span id="detailPrice"></span></td>
                    </tr>
                    <tr>
                        <td class="td">移动价格</td>
                        <td><span id="detailPriceCmcc"></span></td>
                        <td class="td">联通价格</td>
                        <td><span id="detailPriceUnicom"></span></td>
                        <td class="td">电信价格</td>
                        <td><span id="detailPriceTelecom"></span></td>
                    </tr>
                    <tr>
                        <td class="td">是否月限</td>
                        <td><span id="detailIsMonthLimit"></span></td>
                        <td class="td">月限条数</td>
                        <td><span id="detailMonthLimitCount"></span></td>
                        <td class="td">日限进审核</td>
                        <td><span id="detailIsDayLimitCheck"></span></td>
                    </tr>
                    <tr>
                        <td class="td">是否固定提交码号</td>
                        <td><span id="detailIsExt"></span></td>
                        <td class="td">提交码号</td>
                        <td><span id="detailSourceSegment"></span></td>
                        <td class="td">非默认签名拦截</td>
                        <td><span id="detailIsDefaultSignSubmit"></span></td>
                    </tr>
                    <tr>
                        <td class="td">是否固定上行码号</td>
                        <td><span id="detailMoFlag"></span></td>
                        <td class="td">上行推送码号</td>
                        <td><span id="detailMoSourceSegment"></span></td>
                        <td class="td">是否支持客户端登录</td>
                        <td><span id="detailAppRoles"></span></td>
                    </tr>
                    <tr>
                        <td class="td">是否推送状态报告</td>
                        <td><span id="detailIsSgip"></span></td>
                        <td class="td">队列分配</td>
                        <td><span id="detailRedisFlag"></span></td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="chargeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:500px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"> &times;</button>
                <h4 class="modal-title" id="myModalLabel">充值</h4>
            </div>
            <div class="modal-body" id="menuBody">
                <form id="chargeForm" method="post" class="form-horizontal">
                    <input id="cAppId" name="appId" type="hidden"/>
                    <input id="cCompanyId" name="companyId" type="hidden"/>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">帐户</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" readonly="readonly" name="appName" id="cAppName"/>
                            <font size="2" color="#ccc">不可修改</font>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">单价</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" readonly="readonly" name="price" id="cPrice"/>
                            <font size="2" color="#ccc">不可修改</font>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">余额</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" readonly="readonly" name="limitCount"
                                   id="climitCount"/>
                            <font size="2" color="#ccc">不可修改</font>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">交易方式</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="bankName" id="bankName">
                                <option value="">--请选择---</option>
                                <option value="招商银行">招商银行</option>
                                <option value="工商银行">工商银行</option>
                                <option value="建设银行">建设银行</option>
                                <option value="农业银行">农业银行</option>
                                <option value="浦发银行">浦发银行</option>
                                <option value="民生银行">民生银行</option>
                                <option value="公户">公户</option>
                                <option value="支票">支票</option>
                                <option value="现金">现金</option>
                                <option value="支付宝">支付宝</option>
                                <option value="微信支付">微信支付</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">充值类型</label>
                        <div class="col-sm-5">
                            <select name="changeType" id="changeType">
                                <option value="充值">+ 充值</option>
                                <option value="失败回补">+ 失败回补</option>
                                <option value="赠送">+ 赠送</option>
                                <option value="核减">- 核减</option>
                                <option value="扣罚">- 扣罚</option>
                                <option value="退款">- 退款</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">充值条数</label>
                        <div class="col-sm-5">
                            <input id="cChangeNum" name="changeNum" type="text" maxlength="9"/>
                            <font size="2" color="#ccc">充值的条数，不用管正负，系统根据转账类型自动判断</font>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">备注</label>
                        <div class="col-sm-5">
                            <input id="notice" name="notice" type="text"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="chargeBtn">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>
<div class="changeBody" style="width:100%; display: none;position: absolute;top:10px">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>修改帐户</h5>
            </div>
            <div class="ibox-content">
                <form id="editForm" method="post" class="form-horizontal">
                    <input type="hidden" name="id" id="editId">
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">帐户名称</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" readonly="readonly"
                                       name="appName" id="editAccName"/>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">帐户状态</label>
                            <div class="col-sm-4">
                                <select id="editAppStatus" name="appStatus" class="form-control">
                                    <option value="normal">正常</option>
                                    <option value="pause">暂停</option>
                                    <option value="deleted">删除</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">公司</label>
                            <div class="col-sm-4">
                                <select id="editCompanyId" name="companyId"
                                        class="selectpicker show-tick form-control" data-width='98%'
                                        data-live-search="true">
                                    <option value="">....请选择公司....</option>
                                    <c:forEach var="s" items="${complains }">
                                        <option value="${s.id}">${s.id}:${s.companyName }</option>
                                    </c:forEach>
                                </select><span id="editSpan"></span>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">产品</label>
                            <div class="col-sm-4">
                                <select id="editProductId" name="productId"
                                        class="selectpicker show-tick form-control" data-width='98%'
                                        data-live-search="true">
                                    <option value="">...请选择产品...</option>
                                    <c:forEach var="s" items="${products }">
                                        <option value="${s.id}">${s.id}:${s.productName }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">开始时间</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="serviceTimeBegin"
                                       id="editServiceTimeBegin" value="0000"/>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">结束时间</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="serviceTimeEnd" id="editServiceTimeEnd"
                                       value="2400"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">状态报告类型</label>
                            <div class="col-sm-4">
                                <select id="editRptSyncModel" name="rptSyncModel"
                                        class="form-control">
                                    <option value="push">推送</option>
                                    <option value="pull">拉取</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">状态报告地址</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="rptSyncAddress"
                                       id="editRptSyncAddress"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">上行类型</label>
                            <div class="col-sm-4">
                                <select id="editMoSyncModel" name="moSyncModel"
                                        class="form-control">
                                    <option value="push">推送</option>
                                    <option value="pull">拉取</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">上行地址</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="moSyncAddress"
                                       id="editMoSyncAddress"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">分钟限</label>
                            <div class="col-sm-4">
                                <select id="editIsMinLimit" name="isMinLimit"
                                        class="form-control">
                                    <option value="no">不需要分钟限</option>
                                    <option value="yes">需要分钟限</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">分钟限时间</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="minLimitCount"
                                       id="editMinLimitCount" maxlength="4"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">日限</label>
                            <div class="col-sm-4">
                                <select id="editIsDayLimit" name="isDayLimit"
                                        class="form-control">
                                    <option value="no">不需要日限</option>
                                    <option value="yes">需要日限</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">日限条数</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="dayLimitCount"
                                       id="editDayLimitCount" maxlength="4"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">月限</label>
                            <div class="col-sm-4">
                                <select id="editIsMonthLimit" name="isMonthLimit"
                                        class="form-control">
                                    <option value="no">不需要月限</option>
                                    <option value="yes">需要月限</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">月限条数</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="monthLimitCount"
                                       id="editMonthLimitCount" maxlength="4"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">内容限</label>
                            <div class="col-sm-4">
                                <select id="editIsDayContentLimit" name="isDayContentLimit"
                                        class="form-control">
                                    <option value="no">不需要内容限</option>
                                    <option value="yes">需要内容限</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">内容限条数</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control"
                                       name="dayLimitContentCount" id="editDayLimitContentCount"
                                       maxlength="4"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">策略模板</label>
                            <div class="col-sm-4">
                                <select id="editIsTemplate" name="isTemplate" class="form-control">
                                    <option value="no">不过策略模板</option>
                                    <option value="yes">过策略模板</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">敏感词</label>
                            <div class="col-sm-4">
                                <select id="editSkipMustWords" name="skipMustWords"
                                        class="form-control">
                                    <option value="no">过敏感词</option>
                                    <option value="yes">不过敏感词</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">审核词</label>
                            <div class="col-sm-4">
                                <select id="editCheckWordsType" name="checkWordsType"
                                        class="form-control">
                                    <option value="no">不过审核词</option>
                                    <option value="yes">过审核词</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">是否使用模板跳转通道</label>
                            <div class="col-sm-4">
                                <select id="editIsModel" name="isModel" class="form-control">
                                    <option value="no">不使用模板</option>
                                    <option value="yes">使用模板</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">是否内容审核</label>
                            <div class="col-sm-4">
                                <select id="editIsContAudit" name="isContAudit"
                                        class="form-control">
                                    <option value="no">不使用内容审核</option>
                                    <option value="yes">使用内容审核</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">超过条数进审核</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="contAuditCount"
                                       id="editContAuditCount" maxlength="4"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">日限进审核</label>
                            <div class="col-sm-4">
                                <select id="editIsDayLimitCheck" name="isDayLimitCheck"
                                        class="form-control">
                                    <option value="no">不使用日限进审核</option>
                                    <option value="yes">使用日限进审核</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">非默认签名拦截</label>
                            <div class="col-sm-4">
                                <select id="editIsDefaultSignSubmit" name="isDefaultSignSubmit"
                                        class="form-control">
                                    <option value="no">不使用非默认签名拦截</option>
                                    <option value="yes">使用非默认签名拦截</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">计费方式</label>
                        <div class="col-sm-4">
                            <select id="editChargeBy" name="chargeBy" class="form-control">
                                <option value="delivrd">成功计费</option>
                                <option value="delivrdunknown">成功+未知</option>
                                <option value="submit">提交计费</option>
                            </select>
                        </div>
                        <label class="col-sm-2 control-label">结算类型</label>
                        <div class="col-sm-4">
                            <select id="editPayment" name="payment" class="form-control">
                                <option value="advance">预付费</option>
                                <option value="arrears">后付费</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">帐号扩展</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="appExtSrc"
                                       id="editAppExtSrc"/>
                                <input type="hidden" class="form-control" name="oldAppExtSrc"
                                       id="oldAppExtSrc"/>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">默认签名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="defaultSign"
                                       id="editDefaultSign"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">是否固定提交码号</label>
                            <div class="col-sm-4">
                                <select id="editIsExt" name="isExt" class="form-control">
                                    <option value="0">固定提交码号</option>
                                    <option value="1">自定义提交码号</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">提交码号</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="sourceSegment"
                                       id="editSourceSegment" maxlength="20"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">是否固定上行码号</label>
                            <div class="col-sm-4">
                                <select id="editMoFlag" name="moFlag" class="form-control">
                                    <option value="1">固定上行码号</option>
                                    <option value="0" selected="selected">不固定上行码号</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">上行推送码号</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="moSourceSegment" id="editMoSourceSegment"
                                       maxlength="20"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">测试模式</label>
                            <div class="col-sm-4">
                                <select id="editTestModel" name="testModel"
                                        class="form-control">
                                    <option value="no">否</option>
                                    <option value="yes">是</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">cmpp最大连接数</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="maxConnection"
                                       id="editMaxConnection" maxlength="4"/>
                            </div>
                        </div>
                    </div>


                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">优先级</label>
                            <div class="col-sm-4">
                                <select id="editPriority" name="priority" class="form-control">
                                    <option value="hight">高</option>
                                    <option value="normal">正常</option>
                                    <option value="low">低</option>
                                    <option value="urgent">紧急</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">实号库</label>
                            <div class="col-sm-4">
                                <select id="editIsShuntPhone" name="isShuntPhone"
                                        class="form-control">
                                    <option value="no">不校验实号库</option>
                                    <option value="yes">校验实号库</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">是否推送sgip状态报告</label>
                            <div class="col-sm-4">
                                <select id=editIsSgip name="isSgip" class="form-control">
                                    <option value="yes">是</option>
                                    <option value="no">否</option>
                                </select>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">账户扩展参数</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="appExtras"
                                       id="editAppExtras"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">验证IP</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="authIp" id="editAuthIp"/>
                            </div>
                        </div>
                        <label class="col-sm-2 control-label">队列分配</label>
                        <div class="col-sm-4">
                           <select id="editRedisFlag" name="redisFlag" class="form-control">
                                <option value="1" selected="selected">1</option>
                                <option value="2">2</option>
                            </select>
                            <font size="2" color="#ccc">修改队列分配会影响到状态报告推送</font>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">价格(分)</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="price"
                                       id="editPrice" maxlength="4"/>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">移动价格(分)</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="priceCmcc"
                                       id="editPriceCmcc" maxlength="4"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">联通价格(分)</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="priceUnicom"
                                       id="editPriceUnicom" maxlength="4"/>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">电信价格(分)</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="priceTelecom"
                                       id="editPriceTelecom" maxlength="4"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">黑名单级别</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" name="blackLevel" id="editBlackLevel"/>
                                <span style="font-size: 13px; color: #ff5907;">
								    		1:投诉黑名单; 2:回复黑名单; 3:普通黑名单; 4:优化黑名单; 5:关键黑名单; 其他黑名单<br>
								    		  默认投诉黑,回复黑;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;普通黑与优化黑互斥</span>
                            </div>
                        </div>
                        <div class="rowGroup">
                            <label class="col-sm-2 control-label">应用角色</label>
                            <div class="col-sm-2">
                                <select name="appRoles" id="editAppRoles" class="form-control">
                                    <option value="0">不支持客户端登录</option>
                                    <option value="1">支持客户端登录(显示全部手机号、内容)</option>
                                    <option value="2">支持客户端登录(隐藏部分手机号、内容)</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div class="col-sm-2 col-sm-offset-2">
                            <button type="button" class="btn btn-primary btn-block"
                                    id="edit_saveBtn">保存
                            </button>
                        </div>
                        <div class="col-sm-2 col-sm-offset-1">
                            <button type="button" class="btn btn-default btn-block"
                                    id="edit_backBtn">返回
                            </button>
                        </div>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="linkAccount" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:700px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    子帐户
                </h4>
            </div>
            <div class="modal-body" id="menuBody" style="height:400px;overflow:auto;">
                <table id="tLinkAccount" class="table table-bordered table-hover">
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>
<div class="popup_de xycenter" style="z-index:9999">
    <div class="popup_box" style="width: 320px;height: 180px">
        <span class="popup_close">×</span>
        <span class="show_msg"></span>
        <div class="btn_box">
            <div class="popup_btn btn_submit">确定</div>
            <div class="popup_btn btn_cancel">取消</div>
        </div>
    </div>
</div>
<input type="hidden" name="buttons" value="${buttons }" id="buttons"/>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/account/account.js"></script>
</html>