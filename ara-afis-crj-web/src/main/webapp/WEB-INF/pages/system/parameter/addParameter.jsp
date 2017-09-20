<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head></head>

<body>
<div class="hideDiv">
    <input type="hidden" id="action" value="addOption" />
    <input type="hidden" id="prifex" value="parameter" />
    <input type="hidden" id="page" value="Parameter"/>
    <input type="hidden" id="operate" value="modify"/>
</div>

<section class="wrapper_top">
    <div class="row">
        <div class="col-lg-12">
            <!--breadcrumbs start -->
            <ul class="breadcrumb">
                <li><a href="javascript:void(0)" onclick="loadPage('main-content','common/loadContent','')"><i
                        class="icon-home"></i>首页</a></li>
                <li><a href="javascript:void(0)"><i class="icon-gear"></i>系统设置</a></li>
                <li class="active"><i class="icon-reorder"></i>参数设置</li>
            </ul>
            <!--breadcrumbs end -->
        </div>
    </div>
</section>

<section class="wrapper">
    <div class="row">
        <div class="col-lg-12">
            <div class="alert alert-success fade in">
                <button data-dismiss="alert" class="close close-sm" type="button">
                    <i class="icon-remove"></i>
                </button>
                <strong>温馨提示 : </strong> 请慎重修改系统参数.
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div id="result" class="alert alert-block fade in hideDiv">
                <button data-dismiss="alert" class="close close-sm" type="button">
                    <i class="icon-remove"></i>
                </button>
                <h4><i id="result_sign"></i><span id="result_title"></span></h4>
                <p id="result_msg"></p>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading panel-bg-orgin">
                    <i class="icon-reorder"></i>修改系统参数
                </header>
                <div class="panel-body">
                    <div class="form text-nowrap">
                        <form class="cmxform form-horizontal tasi-form" id="dataForm" name="dataForm"
                              novalidate="novalidate">
                            <input type="hidden" id="anyOption" name="anyOption"/>
                            <input type="hidden" id="anyId" name="anyId" value="${parameter.id}"/>
                            <div class="form-group ">
                                <label class="control-label col-sm-2 col-lg-1">应用标示 ：</label>
                                <div class="col-lg-4">
                                    <p class="form-control-static">${parameter.appId}</p>
                                </div>
                            </div>
                            <div class="form-group ">
                                <label class="control-label col-sm-2 col-lg-1">系统模块 ：</label>
                                <div class="col-lg-4">
                                    <p class="form-control-static">
                                        <s:choose>
                                            <s:when test="${parameter.mainKey == 'Master'}">比对引擎</s:when>
                                            <s:otherwise>支撑系统</s:otherwise>
                                        </s:choose>
                                    </p>
                                </div>
                            </div>
                            <div class="form-group ">
                                <label class="control-label col-sm-2 col-lg-1">参数项&nbsp; ：</label>
                                <div class="col-lg-4">
                                    <p class="form-control-static">${parameter.subKey}</p>
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="value" class="control-label col-lg-1"><span class="form-req">*</span>参数值 ：</label>
                                <div class="col-lg-4">
                                    <s:choose>
                                        <s:when test="${uiInputStyle == 'select'}">
                                            <select id="value" name="value" class="form-control">
                                                <option value="T"
                                                        <s:if test='${"T" == parameter.value}'>selected="selected"</s:if>>T
                                                </option>
                                                <option value="F"
                                                        <s:if test='${"T" != parameter.value}'>selected="selected"</s:if>>F
                                                </option>
                                            </select>
                                        </s:when>
                                        <s:otherwise>
                                            <input id="value" name="value"  value="${parameter.value}" class="form-control"  maxlength="30" type="text" required>
                                        </s:otherwise>
                                    </s:choose>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-lg-offset-1 col-lg-12">
                                    <button class="btn btn-danger btn-form" type="button" id="button_add">
                                        <i class="icon-ok-sign"></i>修改
                                    </button>
                                    <button class="btn btn-default btn-form" type="button" id="button_reset">
                                        <i class="icon-refresh"></i> 重置
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </section>
        </div>
    </div>
</section>
<script type="text/javascript" src="js/common/pageEvent.js"></script>
</body>
</html>
